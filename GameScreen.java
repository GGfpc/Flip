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




public class GameScreen implements Screen {
	SpriteBatch batch;
	Texture player;
	OrthographicCamera cam;
	Rectangle rectangle;
	JTB game;
	GameWorld world;
	GameRenderer renderer;
	public static float speed;



	public GameScreen(JTB game){
		this.game = game;
		world = new GameWorld();
		renderer = new GameRenderer(world);
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
		player.dispose();
		batch.dispose();
	}
}
