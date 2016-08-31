package com.jtbgame;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

/**
 * Created by ggarc_000 on 09/08/2016.
 */
public class Spike extends Scrollable {

    Rectangle hitbox;
    GameWorld world;
    int id;
    static Spike lastReset;


    public Spike(int x, int y, int width, int height, float scrollspeed, GameWorld world) {
        super(x, y, width, height, scrollspeed);
        hitbox = new Rectangle(x,y,width,height);
        this.world = world;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        hitbox.setPosition(position);
        if(collides(world.hero)){
            world.hero.isHit = true;
        }
        speed.x = world.GAMESPEED;

        if((world.render.rotation != 0 || (world.SCORE >= 25 && id == 2 && world.spikeOrGlue)) && !isVisible){
            position.y = 5000;
        } else {
            if (!isVisible) {
                Lane l = world.getEmptyLane();
                if (l != null) {
                    l.setItem(this);
                }
                int xpos = 0;
                if (lastReset == null || lastReset.id == id) {
                    xpos = 700;
                } else {
                    xpos = (int) lastReset.position.x + 400;
                }
                reset(xpos, (int) position.y);
                lastReset = this;
            }
        }
    }

    @Override
    public boolean collides(Hero hero) {
        return Intersector.overlaps(hero.hitbox, hitbox);
    }

    public void reset(int newX, int newY) {
        super.reset(newX);
        position.y = newY;
    }

    @Override
    public String toString() {
        return "SPIKE " + id;
    }
}
