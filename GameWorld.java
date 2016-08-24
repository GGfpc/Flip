package com.jtbgame;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by ggarc_000 on 23/06/2016.
 */
public class GameWorld {
    float elapsed;
    Hero hero;
    ArrayList<Platform> plats = new ArrayList<>();
    Platform lastResetHigh;
    Platform lastResetLow;
    float GAMESPEED = 300;
    int SCORE;
    GameRenderer render;
    boolean dead = false;
    Spike spike;
    Spike otherspike;
    int lastelapsedScore;

    public GameWorld(){
        hero = new Hero(35,35,30,30,this);
        for(int i = 0; i < 5; i++){
            Platform plat = new Platform(i*300,-30,300,50,2,i,this);
            Platform platUP = new Platform(i*300,345,300,50,2,i,this);
            plats.add(plat);
            plats.add(platUP);
            platUP.upDown = true;
            plat.upDown = false;
            lastResetHigh = platUP;
            lastResetLow = plat;
        }
        spike = new Spike(700,150,100,30,300,this);
        otherspike = new Spike(1150,100,100,30,300,this);
    }

    public int maxInterval(Platform platform){
        Random r = new Random();
        int last;
        if(platform.position.y < 0){
            last = (int) lastResetLow.position.x + lastResetLow.width;
        } else {
            last = (int) lastResetHigh.position.x + lastResetHigh.width;
        }

        int offset = (60 + r.nextInt(60));
        int signal = (int) Math.pow(-1,r.nextInt());
       // offset *= signal;
        if(hero.elapsed > 5 && platform.upDown == hero.upDown){
            offset = 400;
            hero.elapsed = 0;
        }

        int finaldist = last + offset;
        //System.out.println(offset);
        return finaldist;
    }


    public void shuffle(ArrayList list){
        Random random = new Random(list.size());
        for(int index = 0; index < list.size(); index += 1) {
            Collections.swap(list, index, index + random.nextInt(list.size() - index));
        }
    }


    public void update(float delta) {
        GAMESPEED += 0.01f;
        elapsed += delta;
        hero.update(delta);
        if(SCORE >= 5) {
            spike.update(delta);
            otherspike.update(delta);
        }
        for (Platform p : plats){
            p.update(delta);
        }
        if((int) elapsed % 5 == 0 && (int) elapsed != lastelapsedScore){
            lastelapsedScore = (int) elapsed;
            SCORE++;
        }
    }
}

