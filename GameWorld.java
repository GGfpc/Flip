package com.jtbgame;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by ggarc_000 on 23/06/2016.
 */
public class GameWorld {

    Hero hero;

    ArrayList<Platform> plats = new ArrayList<>();
    ArrayList<Platform> hiddentplats = new ArrayList<>();
    Platform lastReset;
    Platform plat;
    Platform plat7;
    Platform plat3;
    Platform plat4;
    BitmapFont font;
    Baddie baddie;
    float GAMESPEED = 600;


    public GameWorld(){
        hero = new Hero(50,500,30,30);
        baddie = new Baddie(0,15,15,this);


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
        int offset = (int) (Math.random()*1050);
        int finaldist = offset + last;
        //System.out.println(offset);
        if(finaldist < 400) finaldist = 400;

        System.out.println(finaldist);
        return finaldist;
    }


    public void update(float delta) {
        GAMESPEED += 0.1f;

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

        if(hiddentplats.size() >= 2) {
            Collections.shuffle(hiddentplats);
            Platform next = hiddentplats.get(0);
            hiddentplats.remove(0);
            int newX;
            if (lastReset == null) {
                newX = 1300;
            } else {
                newX = maxInterval();
            }

            next.reset(newX, maxJumpHeight());
            lastReset = next;
        }























    }
}

