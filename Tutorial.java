package com.jtbgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;

/**
 * Created by ggarc_000 on 01/08/2016.
 */
public class Tutorial {

    GameWorld world;
    GameRenderer renderer;
    int i;
    boolean first = false;
    boolean second = false;
    boolean third = false;
    int gamespeed;
    boolean hasrefueled;
    int stop;
    Preferences prf;

    public Tutorial(GameWorld world, GameRenderer renderer) {
        this.world = world;
        this.renderer = renderer;
        renderer.tut = this;
        prf = Gdx.app.getPreferences("JTB");
        int lvl = prf.getInteger("Tutorial");
        System.out.println(lvl);
        switch (lvl){
            case 1:
                first = true;
                break;
            case 3:
                third = true;
                world.baddie.canDrop = true;
                world.baddie.canMove = true;
                break;
            default:
                world.baddie.canDrop = true;
                world.baddie.canMove = true;
                break;
        }


    }

    public void start(){
       // i++;


        stop = (int)(world.plat.position.x + world.plat.width) - (int) world.hero.position.x;



        if(stop <= 400 && GameScreen.speed >= 0 && first){
            GameScreen.speed -= (GameScreen.speed * 0.02);
            renderer.runanimspeed += (renderer.runanimspeed * 0.04);


            if(GameScreen.speed < 0.1) {
                world.baddie.canMove = true;
                GameScreen.speed = 0f;
                if(world.refuel.position.x == -4000) {
                    world.refuel.setX(620);
                }
                if(world.baddie.position.x < 580 && world.SCORE == 0) {
                    renderer.tutorialFont.draw(renderer.batch, "Press the left and right sides \n to use your ship", 30, 450);
                }
            }

            if(world.baddie.position.x >= 580){
                world.baddie.canDrop = true;
                world.GAMESPEED = 0;
                renderer.tutorialFont.draw(renderer.batch, "Two finger tap to drop the goo", 30, 450);
            }


            if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && world.baddie.canDrop){
                first = false;
                second = true;
                GameScreen.speed = 0.01f;
            }


        }
        if(second) {
            if (GameScreen.speed < 1) {
                world.GAMESPEED = 600;

                GameScreen.speed += GameScreen.speed * 0.8;
                renderer.runanimspeed = 1/12f;
            } else {
                GameScreen.speed = 1;
                second = false;
                i = 0;
                third = true;
                prf.putInteger("Tutorial",3);
                prf.flush();
            }
        }
        if(world.SCORE >= 1 && third){
            world.baddie.useDelta = true;
            stop = (int)(world.plat7.position.x + world.plat7.width) - (int) world.hero.position.x;
            System.out.println(stop);
            if(stop <= 2500 && world.hero.isJumping){
                gamespeed = (int) world.GAMESPEED;
                GameScreen.speed -= (GameScreen.speed * 0.04);
                if(GameScreen.speed <= 0.1){
                    if(world.baddie.position.x < 500) {
                        renderer.tutorialFont.draw(renderer.batch, "Some gaps need multiple jumps", 85, 80);
                    }
                    if(!hasrefueled) {
                        world.refuel.setX(530);
                        hasrefueled = true;
                    }
                    if(world.baddie.position.x >= 500){
                        renderer.tutorialFont.draw(renderer.batch, "Drop a lot of goo to make the jump", 85, 80);
                        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
                            GameScreen.speed = 0.5f;

                            world.GAMESPEED = gamespeed;
                            third = false;
                        }
                    }

                }
            }
        }
        if(!first && !second && !third && !world.hero.isJumping){

            prf.putInteger("Tutorial",2);
            renderer.shouldtTutorial = false;
            prf.flush();


        }

    }
}
