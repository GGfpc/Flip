package com.jtbgame;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation;


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
    Animation heroanim;
    Animation jumpanim;
    Animation shipanim;
    float elapsed;


    float x;
    float y;
    int i;

    public GameRenderer(GameWorld world) {
        this.world = world;
        world.render = this;
        mountain = new Texture(Gdx.files.internal("mountain.png"));
        mountain2 = new Texture(Gdx.files.internal("mountain2.png"));
        mountain3 = new Texture(Gdx.files.internal("mountain3.png"));
        mountain4 = new Texture(Gdx.files.internal("mountain4.png"));
        SUN = new Texture(Gdx.files.internal("SUN.png"));
        heroatlas = new TextureAtlas(Gdx.files.internal("sprite/Hero.atlas"));
        shipatlas = new TextureAtlas(Gdx.files.internal("ship/ship.atlas"));
        heroanim = new Animation(1/12f,heroatlas.getRegions());
        shipanim = new Animation(1/15f,shipatlas.getRegions());
        herojump = new TextureAtlas(Gdx.files.internal("jumpsprite/jump.atlas"));
        jumpanim = new Animation(1/10f,herojump.getRegions());
        batch = new SpriteBatch();
        player = new Texture(Gdx.files.internal("player.png"));
        bad = new Texture(Gdx.files.internal("baddie.png"));
        platform = new Texture(Gdx.files.internal("platform.png"));
        platform7 = new Texture(Gdx.files.internal("platform7.png"));
        platform3 = new Texture(Gdx.files.internal("platform3.png"));
        platform4 = new Texture(Gdx.files.internal("platform4.png"));
        drop = new Texture(Gdx.files.internal("drop.png"));
        star = new Texture(Gdx.files.internal("star.png"));
        cam = new OrthographicCamera();
        cam.setToOrtho(false, 1024, 600);
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
        batch.draw(mountain4, world.bg4.position.x, world.bg4.position.y);
        batch.draw(mountain2, world.bg2.position.x, world.bg2.position.y);
        batch.draw(mountain, world.bg.position.x, world.bg.position.y);
        batch.draw(mountain3, world.bg3.position.x, world.bg3.position.y);

        batch.draw(shipanim.getKeyFrame(elapsed, true), world.baddie.position.x, world.baddie.position.y,world.baddie.hitbox.width / 2.0f, world.baddie.hitbox.height /2.0f, world.baddie.hitbox.width, world.baddie.hitbox.height,1,1,world.baddie.rotation);

        font.draw(batch,"" + world.GAMESPEED,500,400);
        batch.draw(platform, world.plat.position.x, world.plat.position.y+2,world.plat.hitbox.width,world.plat.hitbox.height);
        batch.draw(platform7, world.plat7.position.x, world.plat7.position.y+2,world.plat7.hitbox.width,world.plat7.hitbox.height);
        batch.draw(platform4, world.plat4.position.x, world.plat4.position.y+2,world.plat4.hitbox.width,world.plat4.hitbox.height);
        batch.draw(platform3, world.plat3.position.x, world.plat3.position.y+2, world.plat3.hitbox.width, world.plat3.hitbox.height);
        elapsed += Gdx.graphics.getDeltaTime();
        System.out.println(world.hero.isJumping);
        if(!world.hero.isJumping){
            batch.draw(heroanim.getKeyFrame(elapsed, true), world.hero.position.x, world.hero.position.y);
        } else {
            batch.draw(jumpanim.getKeyFrame(elapsed, true), world.hero.position.x, world.hero.position.y);
        }


        for(Drop d: world.baddie.drops){
            batch.draw(drop,d.position.x,d.position.y,d.hitbox.width,d.hitbox.height);
        }
        for(Star s : world.stars) {
            batch.draw(star, s.position.x, s.position.y, s.hitbox.width, s.hitbox.height);
        }
        font.draw(batch,"" + world.JUMPS,100,500);


        batch.end();


        if(world.hero.position.y > 450){

            cam.position.y = world.hero.position.y - 100;
        } else {
            cam.position.y = y;
        }
            cam.update();

        i++;



    }

    public void dispose(){
        batch.dispose();
        player.dispose();
        bad.dispose();
        platform .dispose();
        platform7.dispose();
        platform3.dispose();
        platform4.dispose();
        drop.dispose();
        font.dispose();
    }
}
