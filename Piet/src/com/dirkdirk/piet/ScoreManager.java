package com.dirkdirk.piet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dirkdirk.piet.screens.GameScreen;

public class ScoreManager implements EventListener {
	
	public int comboCounter;
	public int score;
	public float powerUpValue = 0;
	public int currentLevel = 1;
	
	private GameScreen gameScreen;
	private SpriteBatch batch;
	
	private BitmapFont font;
	
	private int minComboForLevelUp = 4;
	
	public ScoreManager(GameScreen gameScreen) {
		this.gameScreen = gameScreen;
		this.batch = gameScreen.batch;
		score = 0;
		
		font = new BitmapFont();
		font.setScale(2);
	}
	
	public void render() {
		renderScore();
		renderCombo();
		renderPower();
	}
	
	private void renderCombo() {
		font.draw(batch, "COMBO: " + comboCounter, 300, 730);
	}
	private void renderPower() {
		font.draw(batch, "POWERUP: " + powerUpValue, 500, 730);
		
	}
	
	private void renderScore() {
		// Each number is 80, we'll have them all packed together, max points of 999,999
		// Numbers start from the left at 272, + 80 each time
		String formatted = String.format("%06d", score);
		for (int i = 0; i < 6; i++) {
			int index = Integer.parseInt((String) Character.toString(formatted.charAt(i)));
			float left = 272 + 80 * i;
			batch.draw(Assets.numbers[index], left, 50);
		}
	}
	
	public void hitNote(float distance) {
		comboCounter++;
		
		// Update score
		Gdx.app.log("distance", "" + distance);
		Gdx.app.log("TrackPoint.hitRnage", "" + TrackPoint.hitRange);
		Gdx.app.log("both", "" + (distance / TrackPoint.hitRange));
		score += 100 - Math.abs((double)(distance / TrackPoint.hitRange));
	}
	// Only covers when we strike note and it doesn't hit
	public void missNote() {
		Gdx.app.log("MISSED", "YUP");
		comboCounter = 0;
		Gdx.app.log("comboCounter", "" + comboCounter);
	}
	public void powerUp(float value) {
		powerUpValue += value;
		Gdx.app.log("you hit", "a powerup");
		// Check if this powerup caused a level up
		if (powerUpValue >= 0.9f && comboCounter > minComboForLevelUp && currentLevel < 6) {
			// Level up
			Gdx.app.log("LEVEL UP", "MOTHER FUCKERS");
			powerUpValue = 0;
			// Do whatever to actually impose the level up
			currentLevel++;
			gameScreen.eventDispatcher.levelUp(currentLevel);
		}
	}
	
	@Override 
	public void levelUp(int level) {
		
	}
	
}
