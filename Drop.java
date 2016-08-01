package com.jtbgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;


/**
 * Created by ggarc_000 on 19/07/2016.
 */
public class Drop extends Scrollable implements Poolable {

    Rectangle hitbox;
    GameWorld world;
    Vector2 position;
    Vector2 speed;
    Vector2 acceleration;
    Vector2 tmp;
    int gravity;
    boolean isVisible;

    public Drop(int x, int y, int width, int height, GameWorld world) {
        super(x, y, width, height, 500);
        hitbox = new Rectangle(x,y,height,width);
        position = new Vector2(x,y);
        speed = new Vector2(0,-700);
        acceleration = new Vector2(-4050,0);
        this.world = world;
        isVisible = true;
        tmp = new Vector2(0,0);
    }

    public void checkHeroColision(Hero hero) {
        if (collides(hero)) {
            hero.isJumping = true;
            hero.jump();
        }
    }

    public void init(float x, float y){
        position.set(x,y);
        isVisible = true;
        world.JUMPS--;
        world.font.getData().setScale(0.6f);
        speed.set(0, -700);
        acceleration.set(-4050, 0);
    }

    @Override
    public boolean collides(Hero hero) {
        return Intersector.overlaps(hero.hitbox,hitbox);
    }

    @Override
    public void update(float delta) {
      //  super.update(delta);
        System.out.println(isVisible);
        checkHeroColision(world.hero);

        tmp.set(acceleration);
        tmp.scl(delta);
        speed.add(tmp);
        if(speed.y < -700){
            speed.y = -700;
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
        hitbox.setPosition(position.x, position.y);
        if(position.x + width < 0 || position.y + height < 0){
            isVisible = false;
        }

    }

    @Override
    public void reset() {
        position.y = 5000;
    }
}
