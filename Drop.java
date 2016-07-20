package com.jtbgame;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by ggarc_000 on 19/07/2016.
 */
public class Drop extends Scrollable {

    Rectangle hitbox;
    GameWorld world;
    Vector2 position;
    Vector2 speed;
    Vector2 acceleration;
    int gravity;

    public Drop(int x, int y, int width, int height, GameWorld world) {
        super(x, y, width, height, 500);
        hitbox = new Rectangle(x,y,height,width);
        position = new Vector2(x,y);
        speed = new Vector2(0,-700);
        acceleration = new Vector2(-4050,0);
        this.world = world;
    }

    public void checkHeroColision(Hero hero) {
        if (collides(hero)) {
            hero.jump();
        }
    }

    @Override
    public boolean collides(Hero hero) {
        return Intersector.overlaps(hero.hitbox,hitbox);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        checkHeroColision(world.hero);
        speed.add(acceleration.cpy().scl(delta));
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

        position.add(speed.cpy().scl(delta));
        hitbox.setPosition(position.x, position.y);
    }
}
