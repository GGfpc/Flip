package com.jtbgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.AudioDevice;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
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
	boolean paused;


	public GameScreen(JTB game){
		this.game = game;

		cam = new OrthographicCamera();
		cam.setToOrtho(false, 640, 360);
		view = new ExtendViewport(640,360,cam);
		view.apply();
		world = new GameWorld();
		renderer = new GameRenderer(world,view,cam);

		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(new GestureDetector(new GestureDetector.GestureListener() {
			@Override
			public boolean touchDown(float x, float y, int pointer, int button) {
				return false;
			}

			@Override
			public boolean tap(float x, float y, int count, int button) {

				if(count == 2){
					paused = true;
					System.out.println("pAAAAAAAAAAAAUSE");
					return true;
				} else if(paused) {
					paused = false;
					return true;
				}else if(world.dead){
					retry();
				} else {
					world.hero.jump();
					System.out.println("jump");
					return true;
				}
				return false;
			}

			@Override
			public boolean longPress(float x, float y) {
				return false;
			}

			@Override
			public boolean fling(float velocityX, float velocityY, int button) {
				System.out.println("fling");
               /* if (Math.abs(velocityX) < Math.abs(velocityY)) {
                    if (velocityY < 0 && !upDown) {
                        flip();
                        System.out.println("flipDown");
                    } else if (velocityY > 0 && upDown) {
                        flip();
                        System.out.println("flipUp");
                    }
                }*/

				world.hero.flip();
				return true;
			}

			//<editor-fold desc="Description">
			@Override
			public boolean pan(float x, float y, float deltaX, float deltaY) {
				return false;
			}

			@Override
			public boolean panStop(float x, float y, int pointer, int button) {
				return false;
			}

			@Override
			public boolean zoom(float initialDistance, float distance) {
				return false;
			}

			@Override
			public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
				return false;
			}

			@Override
			public void pinchStop() {

			}
			//</editor-fold>
		}));

		multiplexer.addProcessor(new InputProcessor() {
			@Override
			public boolean keyDown(int keycode) {
				if (keycode == Input.Keys.SPACE) {
					world.hero.flip();
					return true;
				}
				if (keycode == Input.Keys.UP) {
					world.hero.jump();
					return true;
				}
				return false;
			}

			@Override
			public boolean keyUp(int keycode) {
				return false;
			}

			@Override
			public boolean keyTyped(char character) {
				return false;
			}

			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				return false;
			}

			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button) {
				return false;
			}

			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {
				return false;
			}

			@Override
			public boolean mouseMoved(int screenX, int screenY) {
				return false;
			}

			@Override
			public boolean scrolled(int amount) {
				return false;
			}
		});

		multiplexer.addProcessor(new InputProcessor() {
			@Override
			public boolean keyDown(int keycode) {
				return false;
			}

			@Override
			public boolean keyUp(int keycode) {
				if (keycode == Input.Keys.P) {
					paused = !paused;
					return true;
				}
				if (world.dead) {
					if (keycode == Input.Keys.SPACE) {
						retry();
					}
				}
				return false;
			}

			@Override
			public boolean keyTyped(char character) {
				return false;
			}

			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				return false;
			}

			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button) {
				return false;
			}

			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {
				return false;
			}

			@Override
			public boolean mouseMoved(int screenX, int screenY) {
				return false;
			}

			@Override
			public boolean scrolled(int amount) {
				return false;
			}
		});

		Gdx.input.setInputProcessor(multiplexer);
    }


	public void retry() {
		game.setScreen(new GameScreen(game));
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		if(!paused && !world.dead) {
			renderer.isPaused = false;
			world.update(delta);
		} else {
			renderer.isPaused = true;
		}
		renderer.render();



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
