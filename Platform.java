package com.jtbgame;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by ggarc_000 on 30/06/2016.
 */
public class Platform extends Scrollable {

    Rectangle hitbox;
    int id;
    boolean inDanger;
    GameWorld world;
    Vector2 a;
    Vector2 b;
    Vector2 inters = new Vector2(0, 0);
    Rectangle intersection = new Rectangle();
    boolean upDown;

    public Platform(int x, int y, int width, int height, float scrollspeed, int id, GameWorld world) {
        super(x, y, width, height, scrollspeed);
        hitbox = new Rectangle(x, y, width, height);
        this.id = id;
        this.world = world;
        a = new Vector2();
        b = new Vector2();
        inDanger = true;
    }

    public void checkHeroColision(Hero hero) {
        if (collides(hero)) {
            Intersector.intersectLines(hero.futurepos, hero.position, a, b, inters);

            if (inters != null) {
                hero.isJumping = false;
                if (hero.futurepos.y >= position.y + height) {
                    hero.position.set(hero.position.x, position.y + height);
                   // hero.speed.set(0, 0);
                    hero.hitbox.setPosition(hero.position);

                } else if(hero.futurepos.y <= position.y ) {
                    hero.position.set(hero.position.x, position.y -hero.height);
                    hero.hitbox.setPosition(hero.position);
                } else {
                    hero.position.x = position.x - hero.width;
                }
            }
        }

    }


    public void reset(int newX, int newY) {
        super.reset(newX);
        position.y = newY;
        Random r = new Random();
        width = 300 + r.nextInt(800);
        hitbox.width = width;
        inDanger = true;

    }

    @Override
    public boolean collides(Hero hero) {
        return Intersector.overlaps(hero.hitbox, hitbox);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        a.set(position.x, position.y + height);
        b.set(position.x + width, position.y + height);
        hitbox.setPosition(super.position);
        speed.x = world.GAMESPEED;

        if(!isVisible){
         //   world.SCORE++;

            Random r = new Random();
            int signal = (int) Math.pow(-1,r.nextInt());
            int offset = r.nextInt(10) * signal;

            if(position.y < 0) {
                reset(world.maxInterval(this),(int) position.y + offset);
                world.lastResetLow = this;
            } else {
                reset(world.maxInterval(this),(int) position.y + offset);
                world.lastResetHigh = this;

            }
        }

    }


}
