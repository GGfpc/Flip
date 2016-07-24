package com.jtbgame;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * Created by ggarc_000 on 20/07/2016.
 */
public class Star extends Scrollable implements Poolable {

    Rectangle hitbox;
    GameWorld world;

    public Star(int x, int y, int width, int height, float scrollspeed) {
        super(x, y, width, height, scrollspeed);
        hitbox = new Rectangle(x,y,width,height);


    }

    public void init(float x, float y,GameWorld world){
        position.set(x,y);
        isVisible = true;
        this.world = world;
    }

    public void checkHeroColision(Hero hero) {
        if (collides(hero)) {
            world.JUMPS += 10;
            position.y = -100;
        }

    }

    @Override
    public void update(float delta) {
        super.update(delta);
        hitbox.setPosition(super.position);
        speed.x = world.GAMESPEED;
    }

    @Override
    public boolean collides(Hero hero) {
        return Intersector.overlaps(hero.hitbox, hitbox);
    }

    @Override
    public void reset() {
        position.x = 5000;
    }
}
