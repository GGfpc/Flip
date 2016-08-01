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
    Vector2 tmp;
    Vector2 jumpPosition;
    Rectangle hitbox;
    int gravity;
    boolean landing;
    boolean isJumping;
    boolean ignorecol;
    float rotation;
    GameWorld world;
    Vector2 futurepos = new Vector2(0,0);


    public Hero(int x, int y, int width, int height, GameWorld world) {
        this.width = width;
        this.height = height;
        position = new Vector2(x,y);
        jumpPosition = new Vector2(0,0);
        speed = new Vector2(0,0);
        acceleration = new Vector2(0,-980);
        hitbox = new Rectangle(x,y,width,height);
        tmp = new Vector2(0,0);
        ignorecol = true;
        this.world = world;

    }

    public void update(float delta){



        tmp.set(acceleration);
        tmp.scl(delta);
        speed.add(tmp);
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
        tmp.set(speed);
        tmp.scl(delta);
        position.add(tmp);
        futurepos.set(position);
        futurepos.sub(tmp);
        futurepos.sub(tmp);
        hitbox.setPosition(position.x, position.y);

        if (position.y < -50){
            position.y = -50;
        }

        for(Platform plat : world.plats){
            plat.checkHeroColision(this);
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
        jumpPosition.set(position);
        speed.y = 825;
    }
}
