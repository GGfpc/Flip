package com.jtbgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by ggarc_000 on 23/06/2016.
 */
public class Hero {

    int width;
    int height;
    Vector2 position;
    Vector2 speed;
    Vector2 acceleration;
    Rectangle hitbox;
    int gravity;
    boolean landing;
    boolean isJumping;
    boolean ignorecol;

    public Hero(int x, int y, int width, int height) {
        this.width = width;
        this.height = height;
        position = new Vector2(x,y);
        speed = new Vector2(0,0);
        acceleration = new Vector2(0,-980);
        hitbox = new Rectangle(x,y,width,height);
        ignorecol = true;

    }

    public void update(float delta){


        speed.add(acceleration.cpy().scl(delta));
        if(speed.y < -600){
            speed.y = -600;
        }

        if(speed.y > 0){
            gravity++;
            acceleration.y -= 10 * gravity;
        } else {
            gravity = 0;
            acceleration.y = -980;
        }

        position.add(speed.cpy().scl(delta));
        hitbox.setPosition(position.x, position.y);

        if (position.y < -50){
            position.y = -50;
        }

       /* if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            position.x -= 100 * Gdx.graphics.getDeltaTime();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            position.x += 100 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isTouched()) {
            if(!isJumping) {
                jump();
            }

        }
        */


    }

    public void jump(){
        landing = true;
        ignorecol = true;
        isJumping = true;

        speed.y = 825;
    }
}
