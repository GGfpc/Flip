package com.jtbgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by ggarc_000 on 23/06/2016.
 */
public class MainMenuScreen implements Screen {


    final JTB game;
    OrthographicCamera camera;

    public MainMenuScreen(JTB game){
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false,800,480);
    }




    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(235 / 255.0f, 245 / 255.0f, 223 / 255.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.begin();
        game.batch.setProjectionMatrix(camera.combined);
        game.font.getData().setScale(1.5f);
        game.font.setColor(1, 1, 1, 1);
        game.font.draw(game.batch, "FLIP!", 160, 390);
        game.font.getData().setScale(0.13f);
        game.font.setColor(237 / 255.0f, 180 / 255.0f, 88 / 255.0f, 1);
        game.font.draw(game.batch, "PRESS SPACE TO START | SPACE TO FLIP | UP TO JUMP | P TO PAUSE", 80, 100);
        game.batch.end();

        if(Gdx.input.isTouched() || Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            game.setScreen(new GameScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}

