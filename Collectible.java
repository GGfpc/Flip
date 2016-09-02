package com.jtbgame;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by ggarc_000 on 31/08/2016.
 */
public class Collectible extends Scrollable {


    Rectangle hitbox;
    Platform plat;
    boolean hit;
    Vector2 hitposition = new Vector2(0,0);

    public Collectible(int x, int y, int width, int height, float scrollspeed, Platform plat) {
        super(x, y, width, height, scrollspeed);
        hitbox = new Rectangle(x,y,width,height);
        this.plat = plat;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        hitbox.setPosition(position);
        speed.x = plat.speed.x;
        if(!hit){
            hitposition.set(position.x + width / 2.0f,position.y + height / 2.0f);
        }
    }

    @Override
    public boolean collides(Hero hero) {
        return Intersector.overlaps(hero.hitbox,hitbox);
    }
}
