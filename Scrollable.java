package com.jtbgame;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by ggarc_000 on 23/06/2016.
 */
public class Scrollable {

    int width;
    int height;
    Vector2 position;
    Vector2 speed;
    boolean isVisible;

    public Scrollable(int x, int y, int width, int height,float scrollspeed) {
        this.width = width;
        this.height = height;
        position = new Vector2(x,y);
        speed = new Vector2(scrollspeed,0);
        isVisible = true;
    }

    public void update(float delta){
        position.sub(speed.cpy().scl(delta));
        if(position.x + width < 0){
            isVisible = false;
        }
    }

    public void reset(int newX){
        position.x = newX;
        isVisible = true;
    }

    public boolean collides(Hero hero){
        return false;
    }


}
