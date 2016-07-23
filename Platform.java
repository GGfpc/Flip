package com.jtbgame;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by ggarc_000 on 30/06/2016.
 */
public class Platform extends Scrollable {

    Rectangle hitbox;
    int id;
    GameWorld world;

    public Platform(int x, int y, int width, int height, float scrollspeed, int id, GameWorld world) {
        super(x, y, width, height, scrollspeed);
        hitbox = new Rectangle(x,y,width,height);
        this.id = id;
        this.world = world;

    }

    public void checkHeroColision(Hero hero){
        if (collides(hero)) {
            if (hero.ignorecol) {
                hero.ignorecol = false;
            } else {
                hero.isJumping = false;
                Rectangle intersection = new Rectangle();
                Intersector.intersectRectangles(hitbox, hero.hitbox, intersection);

                if (intersection.width > intersection.height) {
                    if (hero.position.y >= intersection.y) {
                        hero.landing = false;
                        hero.position.y = position.y + height + 2;
                    } else {
                        hero.position.y = position.y - hero.height - 0.01f;
                    }
                } else if (intersection.height >= intersection.width && hero.position.x < position.x) {
                    hero.position.x = position.x - hero.width;
                }

            }
        }
    }

    public void checkDropColision(Drop drop){
        if (Intersector.overlaps(drop.hitbox, hitbox)) {
            Rectangle intersection = new Rectangle();
            Intersector.intersectRectangles(hitbox, drop.hitbox, intersection);
            if (intersection.width > intersection.height){
                drop.position.y = position.y + height;
                drop.position.x = intersection.x;
                drop.acceleration = new Vector2(0,0);
                drop.speed.x = -speed.x;
                drop.speed.y = 0;
            } else if(intersection.height >= intersection.width && drop.position.x < position.x){
                drop.position.x = position.x - drop.width;
            }

        }
    }

    public void checkStarColision(Star star){
        if (Intersector.overlaps(star.hitbox,hitbox)) {
            Rectangle intersection = new Rectangle();
            Intersector.intersectRectangles(hitbox, star.hitbox, intersection);
            if (intersection.width > intersection.height){
                star.position.y = position.y + height + 5;
            }

        }
    }


    public void reset(int newX, int newY) {
        super.reset(newX);
        position.y = newY;

    }

    @Override
    public boolean collides(Hero hero){
        return Intersector.overlaps(hero.hitbox,hitbox);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        hitbox.setPosition(super.position);
        speed.x = world.GAMESPEED;

    }
}
