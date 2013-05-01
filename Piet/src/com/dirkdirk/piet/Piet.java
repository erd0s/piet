package com.dirkdirk.piet;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dirkdirk.piet.screens.GameScreen;
import com.dirkdirk.piet.screens.HomeScreen;

public class Piet extends Game {
	
	public Screen homeScreen;
	public Screen gameScreen;
	
	@Override
	public void create() {
		Assets.load();
		
		// Create the screens
		homeScreen = new HomeScreen(this);
		gameScreen = new GameScreen(this);
		
		setScreen(homeScreen);
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		super.dispose();
		Assets.unload();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
}
