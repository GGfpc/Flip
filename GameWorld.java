package com.jtbgame;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by ggarc_000 on 23/06/2016.
 */
public class GameWorld {

    Hero hero;
    Scrollable bg;
    Scrollable bg2;
    Scrollable bg3;
    Scrollable bg4;
    ArrayList<Platform> plats = new ArrayList<>();
    ArrayList<Star> stars = new ArrayList<>();
    ArrayList<Platform> hiddentplats = new ArrayList<>();
    Platform lastReset;
    Platform plat;
    Platform plat7;
    Platform plat3;
    Platform plat4;
    BitmapFont font;
    Baddie baddie;
    float GAMESPEED = 600;
    int JUMPS = 100;

    GameRenderer render;
    boolean dead;

    public GameWorld(){
        hero = new Hero(50,500,30,63);
        baddie = new Baddie(0,32,32,this);
        bg = new Scrollable(0,-100,1167,597,20);
        bg2 = new Scrollable(0,-100,1167,597,20);
        bg3 = new Scrollable(-1300,-100,1167,597,20);
        bg4 = new Scrollable(-300,-200,1167,597,10);

        font = new BitmapFont();
        plat = new Platform(50,-300,1000,500,GAMESPEED,0,this);
        plat7 = new Platform(550 ,-300,800,500,GAMESPEED,1,this);
        plat3 = new Platform(1250,-300,1300,500,GAMESPEED,2,this);
        plat4 = new Platform(1550,-300,1500,500,GAMESPEED,3,this);
        plats.add(plat4);
        plats.add(plat3);
        plats.add(plat);
        plats.add(plat7);

        int i = 0;
        //speed = 4;
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

        if (newY > 550)
            newY = 550;
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


    public void update(float delta) {
        GAMESPEED += 0.1f;

        bg.update(delta);
        if(!bg.isVisible)bg.reset(1300);
        bg2.update(delta);
        if(!bg2.isVisible)bg2.reset(1300);
        bg3.update(delta);
        if(!bg3.isVisible)bg3.reset(1300);
        bg4.update(delta);
        if(!bg4.isVisible)bg4.reset(1300);

        hero.update(delta);
        baddie.update(delta);

       for (Platform plat : plats){

            plat.update(delta);

            plat.checkHeroColision(hero);
           for(Drop drop : baddie.drops){
               plat.checkDropColision(drop);
           }
            if (!plat.isVisible) {
                if (!hiddentplats.contains(plat)){
                    hiddentplats.add(plat);
                 }



            }
        }

        for (Star s : stars){
            s.update(delta);
            s.checkHeroColision(hero);
            for(Platform p : plats){
                p.checkStarColision(s);
            }
        }

        if(hiddentplats.size() >= 2) {
            Collections.shuffle(hiddentplats);
            Platform next = hiddentplats.get(0);
            hiddentplats.remove(0);
            int newX;
            int gap = 0;
            int height;
            if (lastReset == null) {
                newX = 1300;
                height = next.height + (int) next.position.y + 40;
            } else {
                newX = maxInterval();
                gap = newX - ((int)lastReset.position.x + lastReset.width);
                height = lastReset.height + (int) lastReset.position.y;
            }
            Random r = new Random();

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
                Star s = new Star(xpos, height + 40, 40, 40, 20, this);
                stars.add(s);
            }
            lastReset = next;

        }

        if(hero.position.y == -50 || hero.position.x + hero.width < 0){
            dead = true;
        }


    }
}

