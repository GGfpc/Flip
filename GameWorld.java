package com.jtbgame;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * Created by ggarc_000 on 23/06/2016.
 */
public class GameWorld {
    float elapsed;
    Hero hero;
    Glue glue;
    ArrayList<Platform> plats = new ArrayList<>();
    ArrayList<Lane> lanes = new ArrayList<>();
    Platform lastResetHigh;
    Platform lastResetLow;
    float GAMESPEED = 300;
    int SCORE;
    GameRenderer render;
    boolean dead = false;
    Spike spike;
    Spike otherspike;
    int lastelapsedScore;
    int activeObjects = 0;
    Random r = new Random();
    boolean spikeOrGlue = true;

    public GameWorld(){
        hero = new Hero(120,35,30,30,this);
        int pos = 90;
        for(int i = 0; i < 5; i++){
            Lane lane = new Lane(pos, this, i);
            lanes.add(lane);
            pos+=40;

        }

        for(int i = 0; i < 5; i++){
            Platform plat = new Platform(i*300,-80,300,100,2,i,this);
            Platform platUP = new Platform(i*300,345,300,100,2,i,this);
            hero.currentplat = plat;
            plats.add(plat);
            plats.add(platUP);
            platUP.upDown = true;
            plat.upDown = false;
            lastResetHigh = platUP;
            lastResetLow = plat;
        }
        glue = new Glue(-999,200,100,30,-150,this);
        //lanes.get(0).setItem(glue);
        spike = new Spike(-700,300,100,30,300,this);
        spike.id = 1;
        otherspike = new Spike(-1150,400,100,30,300,this);
        otherspike.id = 2;
    }

    public int maxInterval(Platform platform){
        int last;
        if(platform.position.y < 0){
            last = (int) lastResetLow.position.x + lastResetLow.width;
        } else {
            last = (int) lastResetHigh.position.x + lastResetHigh.width;
        }

        int offset = (60 + r.nextInt(150));
        int signal = (int) Math.pow(-1,r.nextInt());
       // offset *= signal;
        if(hero.elapsed > 3.5 && platform.upDown == hero.upDown){
            offset = 600;
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

    public Lane getEmptyLane(){
        Lane empty;
        empty = lanes.get(r.nextInt(5));
       // System.out.println( contains(Lane.previous,empty.id));
        if(empty == null || contains(Lane.previous,empty.id)){
            getEmptyLane();
        }
        return empty;
    }


    public void update(float delta) {


        GAMESPEED += 0.025;
        elapsed += delta;
        hero.update(delta);
        for (Lane l : lanes){
           l.update();
        }
        if(SCORE >= 0){
           glue.update(delta);
        }

        if(SCORE >= 10) {
            spike.update(delta);
            if(SCORE >= 15) {
                otherspike.update(delta);
            }

        }

        if(SCORE >= 25){
            if(SCORE % 10 == 0){
                spikeOrGlue = !spikeOrGlue;
            }
        }

        for (Platform p : plats){
            p.update(delta);
        }
        if((int) elapsed % 5 == 0 && (int) elapsed != lastelapsedScore){
            lastelapsedScore = (int) elapsed;
            SCORE++;
        }
    }

    private boolean contains(int arr[], int a){
        for(int i = 0; i < arr.length; i++){
            if(arr[i] ==  a){
                return true;
            }
        }
        return false;
    }
}

