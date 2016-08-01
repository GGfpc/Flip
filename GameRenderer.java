package com.jtbgame;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;


/**
 * Created by ggarc_000 on 23/06/2016.
 */
public class GameRenderer {

    GameWorld world;
    SpriteBatch batch;
    Texture player;
    Texture bad;
    Texture platform;
    Texture platform7;
    Texture platform3;
    Texture platform4;
    Texture star;
    Texture drop;
    Texture mountain;
    Texture mountain2;
    Texture mountain3;
    Texture mountain4;
    Texture SUN;
    BitmapFont font;
    TextureAtlas heroatlas;
    TextureAtlas shipatlas;
    TextureAtlas herojump;
    Texture heroroll;
    TextureAtlas rollatlas;
    Animation rollanim;
    Animation heroanim;
    Animation jumpanim;
    Animation shipanim;
    float elapsed;
    Vector3 heropos;
    OrthographicCamera cam;

    int shake;
    float x;
    float y;
    int remainingrolls = 5;
    int remainingShakes = 20;


    public GameRenderer(GameWorld world, Viewport view,OrthographicCamera cam) {
        this.world = world;
        world.render = this;
        heropos = new Vector3(0,0,0);
        mountain = new Texture(Gdx.files.internal("mountain.png"));
        heroroll = new Texture(Gdx.files.internal("hero-roll/roll.png"));
        rollatlas = new TextureAtlas(Gdx.files.internal("hero-roll/roll.atlas"));
        rollanim = new Animation(1/12f,rollatlas.getRegions());
        mountain.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        mountain2 = new Texture(Gdx.files.internal("mountain2.png"));
        mountain2.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        mountain3 = new Texture(Gdx.files.internal("mountain3.png"));
        mountain3.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        mountain4 = new Texture(Gdx.files.internal("mountain4.png"));
        mountain4.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        SUN = new Texture(Gdx.files.internal("SUN.png"));
        heroatlas = new TextureAtlas(Gdx.files.internal("sprite/Hero.atlas"));
        shipatlas = new TextureAtlas(Gdx.files.internal("ship/ship.atlas"));
        heroanim = new Animation(1/12f,heroatlas.getRegions());
        shipanim = new Animation(1/15f,shipatlas.getRegions());
        herojump = new TextureAtlas(Gdx.files.internal("jumpsprite/jump.atlas"));
        jumpanim = new Animation(1/13f,herojump.getRegions());
        batch = new SpriteBatch();
        platform = new Texture(Gdx.files.internal("plat1000.png"));
        platform.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        platform7 = new Texture(Gdx.files.internal("plat900.png"));
        platform7.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        platform3 = new Texture(Gdx.files.internal("plat1300.png"));
        platform3.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        platform4 = new Texture(Gdx.files.internal("plat1500.png"));
        platform4.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        drop = new Texture(Gdx.files.internal("drop.png"));
        star = new Texture(Gdx.files.internal("star.png"));
        this.cam = cam;
        bad = new Texture(Gdx.files.internal("baddie.png"));
        x = cam.position.x;
        y = cam.position.y;
        font = new BitmapFont();
    }

    public void render(){
        Gdx.gl.glClearColor(134 / 255.0f, 75 / 255.0f, 102 / 255.0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(cam.combined);

		batch.begin();
        batch.draw(SUN, -10, 0);

        batch.draw(mountain4, world.bg4.position.x, world.bg4.position.y,784*2,299);
        batch.draw(mountain2, world.bg2.position.x, world.bg2.position.y, 1100, 600);
        batch.draw(mountain, world.bg.position.x, world.bg.position.y,1100,600);
        batch.draw(mountain3, world.bg3.position.x, world.bg3.position.y, 1100, 600);

        batch.draw(shipanim.getKeyFrame(elapsed, true), world.baddie.position.x, world.baddie.position.y,world.baddie.hitbox.width / 2.0f, world.baddie.hitbox.height /2.0f, world.baddie.hitbox.width, world.baddie.hitbox.height,1,1,world.baddie.rotation);

        font.draw(batch, "" + world.GAMESPEED, 500, 400);
        batch.draw(platform, world.plat.position.x, world.plat.position.y+2,world.plat.hitbox.width,world.plat.hitbox.height);
        batch.draw(platform7, world.plat7.position.x, world.plat7.position.y+2,world.plat7.hitbox.width,world.plat7.hitbox.height);
        batch.draw(platform4, world.plat4.position.x, world.plat4.position.y+2,world.plat4.hitbox.width,world.plat4.hitbox.height);
        batch.draw(platform3, world.plat3.position.x, world.plat3.position.y+2, world.plat3.hitbox.width, world.plat3.hitbox.height);
        elapsed += Gdx.graphics.getDeltaTime();

        if(world.hero.isJumping){
            if(world.hero.rotation > -15) {
                world.hero.rotation-=0.5;
            }
            batch.draw(jumpanim.getKeyFrame(elapsed, true), world.hero.position.x, world.hero.position.y,world.hero.hitbox.width / 2.0f, world.hero.hitbox.height /2.0f,64,64,1,1,world.hero.rotation);
        } else if(remainingrolls > 0){
            world.hero.rotation = 0;
            batch.draw(rollanim.getKeyFrame(elapsed, true), world.hero.position.x, world.hero.position.y,world.hero.hitbox.width / 2.0f, world.hero.hitbox.height /2.0f,64,64,1,1,world.hero.rotation);
            remainingrolls--;
        } else {
            world.hero.rotation = 0;
            batch.draw(heroanim.getKeyFrame(elapsed, true), world.hero.position.x, world.hero.position.y);
        }




        for(Drop d: world.baddie.drops){
            batch.draw(drop,d.position.x,d.position.y,d.hitbox.width,d.hitbox.height);
        }
        for(Star s : world.stars) {
            batch.draw(star, s.position.x, s.position.y, s.hitbox.width, s.hitbox.height);
        }
        font.draw(batch, "" + world.JUMPS, 100, 500);

        for (Danger d : world.dangers){
            batch.draw(bad, d.position.x, d.position.y);
            if(d.state == 0){
                camShake(cam,1);
            } else if(d.state == 1){
                if(remainingShakes > 0) {
                    camShake(cam, 10);
                    remainingShakes--;
                }
                if(remainingShakes == 0){
                    remainingShakes = 20;
                    d.state = 2;
                }
            } else if(d.state == 2){
                camShake(cam,0);
            }
        }

        batch.end();


        if(world.hero.position.y > 450){
            heropos.set(x, world.hero.position.y - 50, 0);
            cam.position.lerp(heropos,0.3f);
        } else {
            heropos.set(x,y,0);
            cam.position.lerp(heropos,0.3f);
        }

        camShake(cam,shake);
        cam.update();


    }

    public void camShake(OrthographicCamera cam, float size){

        float shakeFreqX = 10;
        float shakeFreqY = 8;
        float shakeFreqY2 = 20;
        float shakeSizeX =  size;
        float shakeSizeY =  size;
        float shakeSizeY2 = size;
        float t = elapsed;
        float xAdjustment = (float) (Math.sin(t * 60)*shakeSizeX);
        float yAdjustment = (float) (Math.sin(t * 60)*shakeSizeY + Math.cos(t * shakeFreqY2)*shakeSizeY2);
        cam.position.x += xAdjustment;
        cam.position.y += yAdjustment;
        cam.update();

    }

    public void dispose(){
        batch.dispose();

        platform .dispose();
        platform7.dispose();
        platform3.dispose();
        platform4.dispose();
        drop.dispose();
        font.dispose();
    }
}
