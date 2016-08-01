package com.jtbgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pool;

import java.util.ArrayList;

/**
 * Created by ggarc_000 on 23/06/2016.
 */
public class Baddie extends Scrollable {

    Rectangle hitbox;
    boolean isvisible;
    GameWorld world;
    ArrayList<Drop> drops = new ArrayList<>();
    Rectangle intersection = new Rectangle();

    Pool<Drop> dropPool = new Pool<Drop>() {
        @Override
        protected Drop newObject() {
            return new Drop(0,5000,10,20,world);
        }
    };

    int count;
    Vector3 touchpos;
    float rotation;

    public Baddie(int x, int width, int height, GameWorld world) {

        super(x, (int) world.hero.position.y, width, height, 0);
        touchpos = new Vector3(0,0,0);
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


        if(Gdx.input.isTouched()){
            int activeTouch = 0;
            for (int i = 0; i < 2; i++) {
                if (Gdx.input.isTouched(i)) activeTouch++;
            }

            if(activeTouch == 2){
                if(world.JUMPS > 0) {
                    Drop drop = dropPool.obtain();
                    drop.init(position.x +5, position.y-10);
                    drops.add(drop);
                }
            } else {
                world.render.cam.unproject(touchpos.set(Gdx.input.getX(), Gdx.input.getY(), 0));
                float camWidth = world.render.cam.viewportWidth;
                if (touchpos.x > camWidth / 2) {
                    position.x += 8;
                    rotation = -30;
                } else  if (touchpos.x < camWidth / 2){
                    position.x -= 8;
                    rotation = 30;
                }
            }

        } else{
            rotation = 0;
        }

        for(int i = 0; i < drops.size(); i++){

            if(!drops.get(i).isVisible){

                dropPool.free(drops.get(i));
                drops.remove(drops.get(i));
            }
        }


        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if(world.JUMPS > 0) {
                System.out.println("drop");
                Drop drop = dropPool.obtain();
                drop.init(position.x +5, position.y-10);
                drops.add(drop);
            }
        }



        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            position.x -= 10;
            rotation = 30;

        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            position.x += 10;
            rotation = -30;
        } else{
            //rotation = 0;
        }



    }

    public void reset(){
        position.x = -200;
    }

    public void checkColision(Hero hero){
        if (collides(hero)) {

            Intersector.intersectRectangles(hitbox, hero.hitbox, intersection);




        }
    }

    @Override
    public boolean collides(Hero hero){
        return Intersector.overlaps(hero.hitbox,hitbox);
    }
}
