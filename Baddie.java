package com.jtbgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

/**
 * Created by ggarc_000 on 23/06/2016.
 */
public class Baddie extends Scrollable {

    Rectangle hitbox;
    boolean isvisible;
    GameWorld world;
    ArrayList<Drop> drops = new ArrayList<>();
    int count;

    public Baddie(int x, int width, int height, GameWorld world) {

        super(x, (int) world.hero.position.y, width, height, 0);
        hitbox = new Rectangle(x,super.position.y,height,width);
        isVisible = false;
        this.world = world;
    }

    @Override
    public void update(float delta) {
        position.y = world.hero.position.y + 70;
        count++;
        super.update(delta);
        for (Drop d : drops){
            d.update(delta);
        }
        hitbox.setPosition(super.position);



        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                Drop drop = new Drop((int) position.x, (int) position.y, 10, 20, world);
                drops.add(drop);

        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            position.x -= 10;

        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            position.x += 10;

        }


    }

    public void reset(){
        position.x = -200;
    }

    public void checkColision(Hero hero){
        if (collides(hero)) {
            Rectangle intersection = new Rectangle();
            Intersector.intersectRectangles(hitbox, hero.hitbox, intersection);




        }
    }

    @Override
    public boolean collides(Hero hero){
        return Intersector.overlaps(hero.hitbox,hitbox);
    }
}
