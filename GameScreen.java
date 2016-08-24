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
	public static OrthographicCamera cam;
	Viewport view;
	JTB game;
	GameWorld world;
	GameRenderer renderer;
	public static float speed;


	public GameScreen(JTB game){
		this.game = game;

		cam = new OrthographicCamera();
		cam.setToOrtho(false, 640, 360);
		view = new ExtendViewport(640,360,cam);
		view.apply();
		world = new GameWorld();
		renderer = new GameRenderer(world,view,cam);

    }



	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		world.update(delta);
		renderer.render();
		if(world.dead){
			//game.setScreen(new MainMenuScreen(game));
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
