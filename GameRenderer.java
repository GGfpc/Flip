package com.jtbgame;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;



/**
 * Created by ggarc_000 on 23/06/2016.
 */
public class GameRenderer {

    GameWorld world;
    BitmapFont font;
    SpriteBatch batch;
    TextureRegion hero;
    Texture spike;
    Texture platform;
    GlyphLayout layout;
    float elapsed;
    Vector3 heropos;
    OrthographicCamera cam;

    float runanimspeed = 1.0f / 12.0f;

    public GameRenderer(GameWorld world, Viewport view, OrthographicCamera cam) {
        this.world = world;
        world.render = this;
        hero = new TextureRegion(new Texture(Gdx.files.internal("hero.png")),100,30);
        spike = new Texture(Gdx.files.internal("spike.png"));
        platform = new Texture(Gdx.files.internal("platform.png"));
        batch = new SpriteBatch();
        this.cam = cam;
        layout = new GlyphLayout();
        font = new BitmapFont(Gdx.files.internal("munto.fnt"));
        font.setColor(1,1,1,0.5f);
        font.getData().setScale(1.5f);

    }

    public void render() {
        Gdx.gl.glClearColor(235 / 255.0f, 245 / 255.0f, 223 / 255.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        layout.setText(font,"" + world.SCORE);
        font.draw(batch, "" + world.SCORE, 390 - layout.width, 280);
        batch.draw(spike, world.spike.position.x, world.spike.position.y);
        batch.draw(spike,world.otherspike.position.x,world.otherspike.position.y);
        for (Platform p : world.plats){
            batch.draw(platform,p.position.x,p.position.y,p.width + 5,p.height);
        }
        batch.draw(hero,world.hero.position.x,world.hero.position.y, world.hero.width / 2.0f,world.hero.height/2.0f,world.hero.hitbox.width,world.hero.hitbox.height,1,1,world.hero.rotation);
        batch.end();



    }

    public void camShake(OrthographicCamera cam, float size) {
        float shakeFreqX = 10;
        float shakeFreqY = 8;
        float shakeFreqY2 = 20;
        float shakeSizeX = size;
        float shakeSizeY = size;
        float shakeSizeY2 = size;
        float t = elapsed;
        float xAdjustment = (float) (Math.sin(t * 60) * shakeSizeX);
        float yAdjustment = (float) (Math.sin(t * 60) * shakeSizeY + Math.cos(t * shakeFreqY2) * shakeSizeY2);
        cam.position.x += xAdjustment;
        cam.position.y += yAdjustment;
        cam.update();

    }

    public void dispose() {
        batch.dispose();

    }
}
