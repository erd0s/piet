package com.dirkdirk.piet.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dirkdirk.piet.AnimationManager;
import com.dirkdirk.piet.Assets;
import com.dirkdirk.piet.BackgroundManager;
import com.dirkdirk.piet.EventDispatcher;
import com.dirkdirk.piet.ForegroundManager;
import com.dirkdirk.piet.ScoreManager;
import com.dirkdirk.piet.TrackLoader;
import com.dirkdirk.piet.TrackManager;

public class GameScreen implements Screen {
	
	public SpriteBatch batch;

	public AnimationManager animManager;
	public TrackManager trackManager;
	public BackgroundManager backgroundManager;
	public EventDispatcher eventDispatcher;
	public ScoreManager scoreManager;
	public ForegroundManager foregroundManager;
	
	public float gameTime;
	
	private Game game;
	
	public GameScreen(Game game) {
		this.game = game;
	}

	@Override
	public void render(float delta) {
		Gdx.app.log("render", "is called");
		gameTime += delta;
		
		animManager.update(delta);
		trackManager.update(gameTime);
		backgroundManager.update(delta);
		foregroundManager.update(delta);
		
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		backgroundManager.render();
		trackManager.render();
		animManager.render();
		scoreManager.render();
		foregroundManager.render();
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
	}

	@Override
	public void show() {
		Gdx.app.log("render", "is called");
		
		batch = new SpriteBatch();
		animManager = new AnimationManager(batch);
		backgroundManager = new BackgroundManager(batch);
		foregroundManager = new ForegroundManager(batch);
		scoreManager = new ScoreManager(this);
		eventDispatcher = new EventDispatcher();
		eventDispatcher.registerListener(backgroundManager);
		eventDispatcher.registerListener(animManager);
		eventDispatcher.registerListener(scoreManager);
		eventDispatcher.registerListener(foregroundManager);
		
		trackManager = TrackLoader.loadTrack("track.json", this);
		
		Assets.music.play();
		
		Gdx.input.setInputProcessor(new InputAdapter() {
			public boolean keyDown(int keycode) {
				switch (keycode) {
					case Keys.J:
						animManager.startAnimation(AnimationManager.RIGHT_ARM);
						trackManager.keyPress(AnimationManager.RIGHT_ARM);
						break;
					case Keys.N:
						animManager.startAnimation(AnimationManager.RIGHT_LEG);
						trackManager.keyPress(AnimationManager.RIGHT_LEG);
						break;
					case Keys.F:
						animManager.startAnimation(AnimationManager.LEFT_ARM);
						trackManager.keyPress(AnimationManager.LEFT_ARM);
						break;
					case Keys.V:
						animManager.startAnimation(AnimationManager.LEFT_LEG);
						trackManager.keyPress(AnimationManager.LEFT_LEG);
						break;
				}
				return true;
			}
		});
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		Assets.music.stop();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
