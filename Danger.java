package com.jtbgame;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by ggarc_000 on 27/07/2016.
 */
public class Danger extends Scrollable {

    Vector2 acceleration;
    Rectangle hitbox;
    int state;
    float rotation;
    Vector2 futurepos = new Vector2();
    Vector2 tmp = new Vector2();

    public Danger(int x, int y, int width, int height, float scrollspeed) {
        super(x, y, width, height, scrollspeed);
        acceleration = new Vector2(0,3000);
        hitbox = new Rectangle(x,y,width,height);
    }


    public boolean collides(Hero hero) {
        return Intersector.overlaps(hero.hitbox, hitbox);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        tmp.set(acceleration);
        tmp.scl(delta);
        speed.add(tmp);
        futurepos.set(position);
        futurepos.sub(tmp);
        futurepos.sub(tmp);
        hitbox.setPosition(position);
        if(!isVisible){
            state = 2;
        }

    }
}
