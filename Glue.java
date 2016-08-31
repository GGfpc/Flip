package com.jtbgame;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by ggarc_000 on 25/08/2016.
 */
public class Glue extends Scrollable {

    Rectangle hitbox;
    GameWorld world;
    Vector2 a;
    Vector2 b;
    Vector2 inters = new Vector2(0,0);


    public Glue(int x, int y, int width, int height, float scrollspeed, GameWorld world) {
        super(x, y, width, height, scrollspeed);
        hitbox = new Rectangle(x,y,width,height);
        this.world = world;
        a = new Vector2();
        b = new Vector2();
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        if(((world.SCORE >= 10 && world.SCORE < 15) || !world.spikeOrGlue) && !isVisible){
            position.y = 5000;
        }

        a.set(position.x, position.y + height);
        b.set(position.x + width, position.y + height);
        hitbox.setPosition(position);
        checkHeroCollision(world.hero);

        if(position.x < -300 || position.x > 700){
            isVisible = false;
        } else {
            isVisible = true;
        }


        if(position.x > 700 || position.x < -300){
            if(speed.x == -200){
                speed.x = world.GAMESPEED + 50;
                position.x = 699;
            } else {
                speed.x = -200;
                position.x = -299;
            }
            Lane l = world.getEmptyLane();
            if(l != null){
                l.setItem(this);
            }
            if(world.activeObjects < 2) {
                reset((int) position.x, (int) position.y);
            }
        }





    }

    public void reset(int newX, int newY) {
        super.reset(newX);
        position.y = newY;
    }

    public void checkHeroCollision(Hero hero){
        if(collides(hero)){
            if(!hero.isStuck && hero.isJumping) {
                hero.isStuck = true;
                hero.rotation = 0;
                hero.speed.y = 0;
                hero.speed.x = -speed.x;
                hero.acceleration.set(0,0);
                hero.isJumping = false;
            }

            Intersector.intersectLines(hero.futurepos, hero.position, a, b, inters);

            if (inters != null) {
                hero.isJumping = false;
                if (hero.futurepos.y >= position.y + height) {
                    world.hero.position.set(inters.x, position.y + height);
                    hero.upDown = false;

                } else if(hero.futurepos.y <= position.y ) {
                    world.hero.position.set(inters.x,position.y - world.hero.height);
                    hero.upDown = true;
                }
            }

        }
    }


    @Override
    public String toString() {
        return "GLUE";
    }

    @Override
    public boolean collides(Hero hero) {
        return Intersector.overlaps(hero.hitbox,hitbox);
    }
}
