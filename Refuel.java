package com.jtbgame;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by ggarc_000 on 02/08/2016.
 */
public class Refuel {

    Vector2 position;
    int width;
    int height;
    Rectangle hitbox;
    GameWorld world;

    public Refuel(int x, GameWorld world) {
        this.position = new Vector2(x,world.baddie.position.y-20);
        width = 40;
        height = 40;
        this.world = world;
        hitbox = new Rectangle(x,world.baddie.position.y -20,width,height);
    }

    public void setX(int x){
        position.set(x,world.baddie.position.y-20);
        hitbox.setPosition(position);
        world.font.getData().setScale(0.5f);
    }
}
