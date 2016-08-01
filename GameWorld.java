package com.jtbgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.utils.Pool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by ggarc_000 on 23/06/2016.
 */
public class GameWorld {

    Hero hero;
    Baddie baddie;


    Scrollable bg;
    Scrollable bg2;
    Scrollable bg3;
    Scrollable bg4;

    ArrayList<Danger> dangers = new ArrayList<>();



    ArrayList<Star> stars = new ArrayList<>();

    Pool<Star> starPool = new Pool<Star>() {
        @Override

        protected Star newObject() {
            return new Star(0,5000,40,40,20);
        }
    };

    ArrayList<Platform> plats = new ArrayList<>();
    ArrayList<Platform> hiddentplats = new ArrayList<>();
    Platform lastReset;
    Platform plat;
    Platform plat7;
    Platform plat3;
    Platform plat4;

    BitmapFont font;

    float GAMESPEED = 600;
    int JUMPS = 100;

    GameRenderer render;
    boolean dead;
    Random r = new Random();

    public GameWorld(){
        hero = new Hero(50,500,30,63,this);
        baddie = new Baddie(0,32,32,this);
        bg = new Scrollable(0,-100,1167,597,20);
        bg2 = new Scrollable(0,-100,1167,597,20);
        bg3 = new Scrollable(-1300,-100,1167,597,20);
        bg4 = new Scrollable(-300,-200,1167,597,10);

        font = new BitmapFont(Gdx.files.internal("flipp2.fnt"));


        plat = new Platform(50,-400,1000,500,GAMESPEED,0,this);
        plat7 = new Platform(1050 ,-400,900,500,GAMESPEED,1,this);
        plat3 = new Platform(1950,-400,1300,500,GAMESPEED,2,this);
        plat4 = new Platform(3250,-400,1500,500,GAMESPEED,3,this);
        plats.add(plat4);
        plats.add(plat3);
        plats.add(plat);
        plats.add(plat7);

        int i = 0;
        //speed = 4;

        GLProfiler.enable();
    }

    public int getMaxHeight(){
        int offset = -(int) (Math.random() * 630);
        int max = 500 + offset;
        if(max > 550) max = 550;
        return max - 500;
    }

    public int maxJumpHeight() {
        int player;
        if (lastReset != null) {
            player = (int) lastReset.position.y + lastReset.height;
        } else {
            player = 200;
        }
        int downUp = (int) (Math.pow(-1, (int) (Math.random() * 10)));
        int offset = (int) (Math.random() * 100) + 50;

        int newY = player + (downUp * offset);

        if (newY > 450)
            newY = 450;
        if (newY < 150)
            newY = 150;
        return newY - 500;
    }

    public int maxInterval(){
        int last = (int) lastReset.position.x + lastReset.width;
        int offset = (int) (Math.random()*(500 + GAMESPEED));
        int finaldist = offset + last;
        //System.out.println(offset);
        if(finaldist < GAMESPEED) finaldist = (int) GAMESPEED;


        return finaldist;
    }


    public void shuffle(ArrayList list){
        Random random = new Random(list.size());

        for(int index = 0; index < list.size(); index += 1) {
            Collections.swap(list, index, index + random.nextInt(list.size() - index));
        }
    }


    public void update(float delta) {
        GAMESPEED += 0.1f;
        System.out.println(Gdx.graphics.getFramesPerSecond());

        bg.update(delta);
        if(!bg.isVisible)bg.reset(1300);
        bg2.update(delta);
        if(!bg2.isVisible)bg2.reset(1300);
        bg3.update(delta);
        if(!bg3.isVisible)bg3.reset(1300);
        bg4.update(delta);
        if(!bg4.isVisible)bg4.reset(1300);


        hero.update(delta);

        for (Danger d : dangers){
            d.update(delta);
        }

       for (int i = 0; i < plats.size(); i++) {
           Platform plat = plats.get(i);
           plat.update(delta);
          // plat.checkHeroColision(hero);
          if(hero.position.x > plat.position.x && plat.inDanger){

               if(plat.width > 1300 && r.nextInt() % 15 == 0) {
                   Danger g = new Danger((int) plat.position.x + plat.width, plat.height + 500, 30, 30, GAMESPEED + 300);
                   dangers.add(g);
                   plat.inDanger = false;
               }
           }



          for(Danger d : dangers){
               plat.checkDangerColision(d);
               if(d.collides(hero)) dead = true;
           }

           for(Drop drop : baddie.drops){
               plat.checkDropColision(drop);
           }



           if (!plat.isVisible) {
               if (!hiddentplats.contains(plat)) {
                   hiddentplats.add(plat);
               }

           }
       }

        baddie.update(delta);




        for (int i = 0; i< stars.size(); i++){
            Star s = stars.get(i);
            s.update(delta);
            s.checkHeroColision(hero);
            for(Platform p : plats){
                p.checkStarColision(s);
            }
            if(!s.isVisible){
                stars.remove(s);
                starPool.free(s);
            }
        }

        if(hiddentplats.size() >= 2) {
            shuffle(hiddentplats);
            Platform next = hiddentplats.get(0);
            hiddentplats.remove(0);
            int newX;
            int gap = 0;
            int height;
            if (lastReset == null) {
                newX = 3400;
                height = next.height + (int) next.position.y + 40;
            } else {
                newX = maxInterval();
                gap = newX - ((int)lastReset.position.x + lastReset.width);
                height = lastReset.height + (int) lastReset.position.y;
            }


            next.reset(newX, maxJumpHeight());

            int rand = r.nextInt();
            int xpos;
            if(gap > 500){
                xpos = newX - (gap / 2) - 120;
                height += 100;
            } else {
                xpos = (int) (next.position.x + (next.width / 2));
                height += 70;
            }
            if(rand * 0 == 0) {
                Star s = starPool.obtain();
                s.init(xpos, height + 40, this);
                stars.add(s);
            }
            lastReset = next;

        }

        if(hero.position.y == -50 || hero.position.x + hero.width < 0){
            dead = true;
        }


    }
}

