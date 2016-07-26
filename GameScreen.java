package com.jtbgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.AudioDevice;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class GameScreen implements Screen {
	SpriteBatch batch;
	OrthographicCamera cam;
	Rectangle rectangle;
	Viewport view;
	JTB game;
	GameWorld world;
	GameRenderer renderer;
	public static float speed;



	public GameScreen(JTB game){
		this.game = game;
		world = new GameWorld();
		cam = new OrthographicCamera();
		cam.setToOrtho(false, 1024, 600);
		renderer = new GameRenderer(world,view,cam);
		view = new ExtendViewport(1024,600,cam);
		view.apply();
		speed = 1f;
    }

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		delta *= speed;
		world.update(delta);
		renderer.render();
		if(world.dead){
			game.setScreen(new MainMenuScreen(game));
			dispose();
		}
	}

	@Override
	public void resize(int width, int height) {
		view.update(width,height);

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
		renderer.dispose();
	}
}
