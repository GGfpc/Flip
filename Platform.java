package com.jtbgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.physics.box2d.RayCastCallback;

/**
 * Created by ggarc_000 on 30/06/2016.
 */
public class Platform extends Scrollable {

    Rectangle hitbox;
    int id;
    boolean inDanger;
    GameWorld world;
    Vector2 a;
    Vector2 b;
    Vector2 inters = new Vector2(0,0);
    Rectangle intersection = new Rectangle();

    public Platform(int x, int y, int width, int height, float scrollspeed, int id, GameWorld world) {
        super(x, y, width, height, scrollspeed);
        hitbox = new Rectangle(x,y,width,height);
        this.id = id;
        this.world = world;
        a = new Vector2();
        b = new Vector2();
        inDanger = true;
    }

    public void checkHeroColision(Hero hero){
        if (collides(hero)) {

            if (hero.ignorecol) {
                hero.ignorecol = false;
            } else {
                if(hero.isJumping){
                    if(hero.jumpPosition.y > hero.position.y + 30){
                        world.render.remainingrolls = 20;
                    }
                }
                hero.isJumping = false;
                //Intersector.intersectRectangles(hitbox, hero.hitbox, intersection);



                Intersector.intersectLines( hero.futurepos, hero.position, a,b, inters);
                System.out.println(position.y + height);
                System.out.println(inters);
                if (inters != null) {
                    if (hero.futurepos. y >= position.y + height) {
                        hero.landing = false;
                        hero.position.set(hero.position.x, position.y + height + 2);
                        hero.speed.set(0, 0);
                        hero.hitbox.setPosition(hero.position);
                        // hero.acceleration.set(0,0);
                        // hero.update(Gdx.graphics.getDeltaTime());

                    } else {
                        hero.position.x = position.x - hero.width;
                    }
                }
            }
        }
    }

    public void checkDropColision(Drop drop){
        if (Intersector.overlaps(drop.hitbox, hitbox)) {

            Intersector.intersectRectangles(hitbox, drop.hitbox, intersection);
            if (intersection.width > intersection.height){
                drop.position.y = position.y + height;
                drop.position.x = intersection.x;
                drop.acceleration.set(0,0);
                drop.speed.x = -speed.x;
                drop.speed.y = 0;
            } else if(intersection.height >= intersection.width && drop.position.x < position.x){
                drop.position.x = position.x - drop.width;
            }

        }
    }

    public void checkDangerColision(Danger danger){
        if (Intersector.overlaps(danger.hitbox, hitbox)) {
           // Intersector.intersectRectangles(hitbox, danger.hitbox, intersection);


            Intersector.intersectLines( danger.futurepos, danger.position, a,b, inters);
            if (inters != null) {
                danger.state = 1;
                danger.position.y = position.y + height;
                danger.hitbox.setPosition(danger.position);
                danger.position.x = inters.x;
                danger.acceleration.set(0,0);
                danger.speed.x = speed.x;
                danger.speed.y = 0;
                checkDangerColision(danger);
            }

        }
    }

    public void checkStarColision(Star star){
        if (Intersector.overlaps(star.hitbox,hitbox)) {
            Intersector.intersectRectangles(hitbox, star.hitbox, intersection);
            if (intersection.width > intersection.height){
                star.position.y = position.y + height + 5;
            }

        }
    }


    public void reset(int newX, int newY) {
        super.reset(newX);
        position.y = newY;
        inDanger = true;
    }

    @Override
    public boolean collides(Hero hero){
        return Intersector.overlaps(hero.hitbox,hitbox);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        a.set(position.x, position.y + height);
        b.set(position.x + width, position.y + height);
        hitbox.setPosition(super.position);
        speed.x = world.GAMESPEED;

    }
}
