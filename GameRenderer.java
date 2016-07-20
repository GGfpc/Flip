package com.jtbgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by ggarc_000 on 23/06/2016.
 */
public class GameRenderer {

    GameWorld world;
    OrthographicCamera cam;
    SpriteBatch batch;
    Texture player;
    Texture bad;
    Texture platform;
    Texture platform7;
    Texture platform3;
    Texture platform4;
    Texture drop;
    BitmapFont font;
    float x;
    float y;

    public GameRenderer(GameWorld world) {
        this.world = world;
        batch = new SpriteBatch();
        player = new Texture(Gdx.files.internal("player.png"));
        bad = new Texture(Gdx.files.internal("baddie.png"));
        platform = new Texture(Gdx.files.internal("platform.png"));
        platform7 = new Texture(Gdx.files.internal("platform7.png"));
        platform3 = new Texture(Gdx.files.internal("platform3.png"));
        platform4 = new Texture(Gdx.files.internal("platform4.png"));
        drop = new Texture(Gdx.files.internal("drop.png"));
        cam = new OrthographicCamera();
        cam.setToOrtho(false,1024, 600);
        x = cam.position.x;
        y = cam.position.y;
        font = new BitmapFont();
    }

    public void render(){
        Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
        batch.draw(bad, world.baddie.position.x, world.baddie.position.y,world.baddie.hitbox.width,world.baddie.hitbox.height);

        font.draw(batch,"" + world.GAMESPEED,500,400);
        batch.draw(platform, world.plat.position.x, world.plat.position.y,world.plat.hitbox.width,world.plat.hitbox.height);
        batch.draw(platform7, world.plat7.position.x, world.plat7.position.y,world.plat7.hitbox.width,world.plat7.hitbox.height);
        batch.draw(platform4, world.plat4.position.x, world.plat4.position.y,world.plat4.hitbox.width,world.plat4.hitbox.height);
        batch.draw(platform3, world.plat3.position.x, world.plat3.position.y,world.plat3.hitbox.width,world.plat3.hitbox.height);
        batch.draw(player, world.hero.position.x, world.hero.position.y);
        for(Drop d: world.baddie.drops){
            batch.draw(drop,d.position.x,d.position.y,d.hitbox.width,d.hitbox.height);
        }
        batch.end();


        if(world.hero.position.y > 450){

            cam.position.y = world.hero.position.y - 100;
        } else {
            cam.position.y = y;
        }
            cam.update();



    }
}
