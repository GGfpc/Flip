package com.jtbgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import java.util.HashMap;
import java.util.Queue;
import java.util.Random;

/**
 * Created by ggarc_000 on 10/08/2016.
 */
public class ColorSchemeManager {

    private HashMap<Integer,ColorScheme> schemes = new HashMap<>();
    ColorScheme desired;
    private ColorScheme current;
    ColorScheme updating = new ColorScheme(Color.WHITE,Color.WHITE);
    int currentKey;
    int lastUpdate = 0;
    GameWorld world;
    float elapsed;
    float currentluminance;


    public void addScheme(int name, ColorScheme scheme){
        schemes.put(name, scheme);
        scheme.name = name;
    }

    public void setCurrent(ColorScheme current) {
        this.current = current;
        updating.plat = current.plat.cpy();
        updating.bg = current.bg.cpy();
        currentluminance = (0.299f*current.bg.r + 0.587f*current.bg.g + 0.114f*current.bg.b);
    }

    public ColorScheme getRandom(){
        Random r = new Random();
        ColorScheme selected = schemes.get(currentKey % 3);
        if(selected.name == current.name) {
            getRandom();
        }

        return selected;
    }

    public ColorScheme getCurrent() {
        return current;
    }

    public ColorSchemeManager(GameWorld world) {
        this.world = world;
    }

    public void updateScheme(){
        currentluminance = (0.299f*updating.bg.r + 0.587f*updating.bg.g + 0.114f*updating.bg.b);
        if(elapsed <= 6) {
            updating.bg.lerp(desired.bg, 0.05f);
            updating.plat.lerp(desired.plat, 0.05f);
            elapsed+= Gdx.graphics.getDeltaTime();
        } else if(world.SCORE % 5 == 0 && world.SCORE != lastUpdate){
            elapsed = 0;
            currentKey++;
            lastUpdate = world.SCORE;
            setCurrent(desired);
            desired = getRandom();

        }

    }



    public ColorScheme getScheme(int name){
        return schemes.get(name);
    }

}
