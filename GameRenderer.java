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
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;



/**
 * Created by ggarc_000 on 23/06/2016.
 */
public class GameRenderer {

    ParticleEffect explode;
    ParticleEffect trail;
    ParticleEffect starexplode;

    ParticleEffectPool starexplodepool;

    GameWorld world;
    BitmapFont font;
    SpriteBatch batch;
    TextureRegion hero;
    Texture spike;
    Texture collectible;
    Texture platform;
    Texture glue;
    GlyphLayout layout;
    float elapsed;
    Vector3 heropos;
    OrthographicCamera cam;
    float r,g,b;
    ColorSchemeManager colormanage;
    int lastUpdate = 3;
    String scoreString;
    float rotation = 0;
    int min = 15, max = 25;
    int lasthit = -1;
    float alpha;
    Array<ParticleEffectPool.PooledEffect> effects = new Array<>();
    boolean isPaused;
    Rectangle pauseButton;
    float pauserotation = 0;
    Vector2 position,speed;
    int currScore;

    float runanimspeed = 1.0f / 12.0f;

    public GameRenderer(GameWorld world, Viewport view, OrthographicCamera cam) {
        this.world = world;
        position = new Vector2(700,0);
        speed = new Vector2(-200,0);
        pauseButton = new Rectangle(10,350,20,20);
        explode = new ParticleEffect();
        explode.load(Gdx.files.internal("explosion.p"), Gdx.files.internal(""));
        explode.getEmitters().first().setPosition(world.hero.position.x, world.hero.position.y);
        explode.start();
        starexplode = new ParticleEffect();
        starexplode.load(Gdx.files.internal("starexplosion.p"), Gdx.files.internal(""));
        starexplodepool = new ParticleEffectPool(starexplode,5,10);
        trail = new ParticleEffect();
        trail.load(Gdx.files.internal("trail.p"), Gdx.files.internal(""));
        trail.getEmitters().first().setPosition(world.hero.position.x, world.hero.position.y);
        trail.start();
        glue = new Texture("sticky.png");
        colormanage = new ColorSchemeManager(world);
        world.render = this;
        hero = new TextureRegion(new Texture(Gdx.files.internal("hero.png")),100,30);
        spike = new Texture(Gdx.files.internal("spike.png"));
        collectible = new Texture(Gdx.files.internal("star.png"));
        platform = new Texture(Gdx.files.internal("platform.png"));
        batch = new SpriteBatch();
        this.cam = cam;
        layout = new GlyphLayout();
        font = new BitmapFont(Gdx.files.internal("munto.fnt"));
        font.setColor(1, 1, 1, 1f);
        font.getData().setScale(1.3f);
        Color plat = new Color(186/255.0f,212/255.0f,170/255.0f,1);
        Color bg = new Color(235/255.0f,245/255.0f,223/255.0f,1);
        colormanage.addScheme(0, new ColorScheme(bg, plat));
        colormanage.addScheme(1, new ColorScheme(new Color(167 / 255.0f, 198 / 255.0f, 218 / 255.0f, 1), new Color(87 / 255.0f, 117 / 255.0f, 144 / 255.0f, 1)));
        colormanage.addScheme(2, new ColorScheme(new Color(239 / 255.0f, 210 / 255.0f, 203 / 255.0f, 1), new Color(191 / 255.0f, 139 / 255.0f, 133 / 255.0f, 1)));
        colormanage.setCurrent(colormanage.getScheme(0));
        colormanage.desired = colormanage.getScheme(2);

    }

    public void render() {
        Gdx.gl.glClearColor(colormanage.updating.bg.r, colormanage.updating.bg.g, colormanage.updating.bg.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        speed.x = -world.GAMESPEED;
        if(currScore < world.SCORE){
            position.x = 700;
            currScore = world.SCORE;
        }

        if(!world.dead) {
            if (!isPaused) {
                position.add(speed.cpy().scl(Gdx.graphics.getDeltaTime()));
                font.setColor(colormanage.updating.plat.r, colormanage.updating.plat.g, colormanage.updating.plat.b, alpha);
                font.draw(batch, "" + world.SCORE, position.x, 280);
                batch.draw(glue, world.glue.position.x, world.glue.position.y, world.glue.width, world.glue.height);
                batch.draw(spike, world.spike.position.x, world.spike.position.y);
                batch.draw(spike, world.otherspike.position.x, world.otherspike.position.y);
                batch.setColor(colormanage.updating.plat);
                for (Platform p : world.plats) {

                    batch.draw(platform, p.position.x, p.position.y, p.width + 5, 100);
                }
                batch.setColor(1, 1, 1, 1);
                float opacity;
                batch.draw(hero, world.hero.position.x, world.hero.position.y, world.hero.width / 2.0f, world.hero.height / 2.0f, world.hero.hitbox.width, world.hero.hitbox.height, 1, 1, world.hero.rotation);
                if (!world.hero.upDown) {
                    trail.setPosition(world.hero.position.x, world.hero.position.y);
                    trail.getEmitters().get(0).setFlip(false, true);
                } else {
                    trail.setPosition(world.hero.position.x, world.hero.position.y + world.hero.height);
                    trail.getEmitters().get(0).setFlip(false, true);
                }
                float[] colors = {colormanage.updating.plat.r, colormanage.updating.plat.g, colormanage.updating.plat.b};
                trail.getEmitters().get(0).getTint().setColors(colors);

                trail.update(Gdx.graphics.getDeltaTime());
                if (!world.hero.isJumping) {
                    // trail.draw(batch);
                }

                // batch.draw(hero, pos.x - i*10, pos.y, world.hero.width / 2.0f, world.hero.height / 2.0f, world.hero.hitbox.width, world.hero.hitbox.height, 1, 1, world.hero.rotation);
                starexplode.update(Gdx.graphics.getDeltaTime());
                for (Collectible c : world.collectibles) {
                    batch.draw(collectible, c.position.x, c.position.y, c.width, c.height);
                    if (c.hit) {
                        ParticleEffect hit = starexplodepool.obtain();
                        hit.setPosition(c.hitposition.x, c.hitposition.y);
                        effects.add((ParticleEffectPool.PooledEffect) hit);
                        c.hit = false;
                    }
                }

                for (ParticleEffectPool.PooledEffect p : effects) {
                    p.update(Gdx.graphics.getDeltaTime());
                    p.draw(batch);
                    p.allowCompletion();
                    if (p.isComplete()) {
                        System.out.println("AhHH");

                    }
                }

                if (world.hero.isHit) {
                    trail.allowCompletion();
                    if (elapsed >= 0.1)
                        explode.allowCompletion();
                    explode.draw(batch);
                    explode.update(Gdx.graphics.getDeltaTime());
                    elapsed += Gdx.graphics.getDeltaTime();
                    world.hero.position.x = 5000;
                    world.hero.acceleration.set(0, 0);
                    world.hero.speed.set(0, 0);

                    if (elapsed >= 1.3)
                        world.dead = true;
                } else {
                    explode.setPosition(world.hero.position.x + (world.hero.width / 2.0f), world.hero.position.y + (world.hero.height / 2.0f));
                }

                if (world.SCORE >= 5) {
                    colormanage.updateScheme();

                }
                font.getData().setScale(0.6f, 0.1f);
                font.setColor(237 / 255.0f, 180 / 255.0f, 88 / 255.0f, 1);
                // font.draw(batch,"|",pauseButton.x,pauseButton.y);
                // font.draw(batch,"|",pauseButton.x + 15,pauseButton.y);
                alpha = 0.45f - (1 - colormanage.currentluminance);
                rotate();
                font.setColor(colormanage.updating.plat.r, colormanage.updating.plat.g, colormanage.updating.plat.b, alpha);
                font.getData().setScale(1.5f);
            }
        }

        batch.end();
        Matrix4 uiMatrix = cam.combined.cpy();
        uiMatrix.setToOrtho2D(0, 0, 640, 360);
        batch.setProjectionMatrix(uiMatrix);
        batch.begin();
        batch.enableBlending();
        if(world.dead){
            writeDead();
        } else if(isPaused){
            writeScore();
        }
        drawHUD();
        batch.end();
    }

    private void writeScore() {
        if(isPaused){
            font.getData().setScale(0.30f);
            font.setColor(colormanage.updating.plat.r, colormanage.updating.plat.g, colormanage.updating.plat.b, alpha);
            font.draw(batch, "SCORE    COLLECTED", 120, 240);
            font.draw(batch, "" + world.SCORE + "             " + world.collected, 175, 170);
            font.getData().setScale(0.15f);
            font.setColor(237 / 255.0f, 180 / 255.0f, 88 / 255.0f, alpha);
            font.draw(batch, "PAUSED", 280, 340);
            font.draw(batch, "PRESS P TO CONTINUE", 210, 55);
            font.setColor(colormanage.updating.plat.r, colormanage.updating.plat.g, colormanage.updating.plat.b, alpha);
            font.getData().setScale(1.5f);
            alpha = 1;

        } else {
        }
    }

    private void drawHUD(){
        if(!isPaused && !world.dead) {
            font.getData().setScale(0.1f);
            font.setColor(255 / 255.0f, 131 / 255.0f, 96 / 255.0f, 1f);
            batch.draw(collectible, 30, 305, 20, 20);
            font.getData().setScale(0.16f);
            font.draw(batch, "" + world.collected, 55, 325.8f);
            font.getData().setScale(0.13f);
            font.draw(batch, "SCORE: " + world.SCORE, 30, 295.8f);
            font.getData().setScale(1.5f);
        }
    }

    private void writeDead() {
        font.setColor(colormanage.updating.plat.r, colormanage.updating.plat.g, colormanage.updating.plat.b, 1);
        font.getData().setScale(0.20f);
        font.draw(batch, "SCORE        COLLECTED", 160, 200);
        font.draw(batch, "" + world.SCORE + "                 " + world.collected, 195, 150);
        font.setColor(237 / 255.0f, 180 / 255.0f, 88 / 255.0f, 1);
        font.getData().setScale(0.60f);
        font.draw(batch,"YOU LOST!", 100, 330);
        font.getData().setScale(0.15f);
        font.draw(batch,"PRESS SPACE TO CONTINUE", 182,65);
        font.setColor(colormanage.updating.plat.r, colormanage.updating.plat.g, colormanage.updating.plat.b, alpha);
    }

    private void rotate() {
        if(world.SCORE >= min && world.SCORE < max) {
            if (rotation < 90) {
                cam.rotate(0.5f);
                rotation+= 0.5f;
                cam.zoom += 0.005;
                cam.update();
            }
        }
        if(world.SCORE >= max){
            if(rotation > 0){
                cam.rotate(-0.5f);
                rotation-= 0.5f;
                cam.zoom -= 0.005;
                cam.update();
            }
        }
        pauserotation = rotation;
        if(world.SCORE > max && rotation == 0){
            min = max + 25;
            max = min + 25;
        }
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
