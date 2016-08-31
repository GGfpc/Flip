package com.jtbgame;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by ggarc_000 on 31/08/2016.
 */
public class Collectible extends Scrollable {


    Rectangle hitbox;

    public Collectible(int x, int y, int width, int height, float scrollspeed) {
        super(x, y, width, height, scrollspeed);
        hitbox = new Rectangle(x,y,width,height);
    }

    


}
