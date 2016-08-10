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
    Rectangle hitbox;
    int gravity;
    float rotation;
    GameWorld world;
    Vector2 futurepos = new Vector2(0,0);
    boolean isJumping;
    float elapsed;
    boolean upDown;


    public Hero(int x, int y, int width, int height, GameWorld world) {
        this.width = width;
        this.height = height;
        position = new Vector2(x,y);
        speed = new Vector2(0,0);


        acceleration = new Vector2(0,-980);

        hitbox = new Rectangle(x,y,width,height);
        tmp = new Vector2(0,0);
        this.world = world;

    }

    public void update(float delta){
        tmp.set(acceleration);
        tmp.scl(delta);
        speed.add(tmp);
        System.out.println(speed.y  );
        if(Math.abs(speed.y) > 600){
            if(speed.y > 0) {
                speed.y = 600;
            }else{
                speed.y = -600;
            }
        }
        if(isJumping){
            rotation -=5.5;
        } else {
            rotation = 0;
        }

        elapsed += delta;
        tmp.set(speed);
        tmp.scl(delta);
        position.add(tmp);
        futurepos.set(position);
        futurepos.sub(tmp);
        futurepos.sub(tmp);
        hitbox.setPosition(position.x, position.y);

        if (position.y < -50){
            position.y = -50;
            world.dead = true;
        }

        if(position.y > 400){
            position.y = 400;
            world.dead = true;
        }

        for(Platform plat : world.plats){
            plat.checkHeroColision(this);
        }


        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isTouched()) {
            jump();

        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            flip();

        }


    }

    public void jump(){
        if(!isJumping) {
            isJumping = true;
            if(speed.y < 0){
                speed.y = 400;
            } else {
                speed.y = -400;
            }

        }

    }

    public void flip(){
        if(!isJumping) {
            upDown = !upDown;
            elapsed = 0;
            isJumping = true;
            acceleration.y *= -1;
            speed.y = acceleration.y;
        }
    }
}
