package com.dirkdirk.piet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TrackPoint {
	
	public int type;
	public float time;
	public float powerUpValue = 0;
	public TextureRegion icon;
	
	public static final int STATE_HIDDEN = 0;
	public static final int STATE_ONSCREEN = 1;
	public static final int STATE_HIT = 2;
	public static final int STATE_FINISHED = 3;
	public static final float[] xPositions = {66f, 156f, 868f, 958f};
	
	public final float width = 74f;
	public final float height = 90f;
	
	public float timeOfHit;
	
	public static float hitRange = 0.2f;
	
	public int state = 0;
	public float xPos;
	public float yPos;
	public float hitTime;
	public float rotationFactor;
	
	// This is used to make values negative on left, positive on right
	public float signMultiplier;

	public TrackPoint(String key, float time) {
		if (key.equals("F")) {
			type = AnimationManager.LEFT_ARM;
			icon = Assets.iconF;
			rotationFactor = (float) (Math.random() * 180f) + 90;
			signMultiplier = -1;
		}
		else if (key.equals("V")) {
			type = AnimationManager.LEFT_LEG;
			icon = Assets.iconV;
			rotationFactor = (float) (Math.random() * 180f) + 90;
			signMultiplier = -1;
		}
		else if (key.equals("J")) {
			type = AnimationManager.RIGHT_ARM;
			icon = Assets.iconJ;
			rotationFactor = - (float) (Math.random() * 180f) - 90;
			signMultiplier = 1;
		}
		else if (key.equals("N")) {
			type = AnimationManager.RIGHT_LEG;
			icon = Assets.iconN;
			rotationFactor = - (float) (Math.random() * 180f) - 90;
			signMultiplier = 1;
		}
		xPos = xPositions[type];
		
		this.time = time;
	}
	public TrackPoint(String key, float time, float powerUpValue) {
		this(key, time);
		this.powerUpValue = powerUpValue;
	}
	
	// Passes in the total level time, has more meaning than the delta, and means we don't have to store copies of the total time in every TrackPoint object
	public void update(float levelTime) {
		// If we're hidden check if we need to go to onscreen
		if (state == STATE_HIDDEN) {
			// Anything that is > TrackManager.totalTimeOnScreen should be set to STATE_ONSCREEN
			if (levelTime + TrackManager.totalTimeUntilBar > time) { 
				state = STATE_ONSCREEN;
			}
		}
		// If we're onscreen check if we need to be finished, and update the x pos
		if (state == STATE_ONSCREEN || state == STATE_HIT) {
			// Check if we're done
			if (levelTime > time + (TrackManager.totalTimeUntilBar/3)) {
				// We're done, and the player didn't hit this trackpoint
				state = STATE_FINISHED;
			}
			else {
				// Update the ypos
				float percentageIntoTotal = (levelTime + TrackManager.totalTimeUntilBar - time) / TrackManager.totalTimeUntilBar; 
				yPos = percentageIntoTotal * 576f;
			}
		}
		// If we're hit update the hit time
		if (state == STATE_HIT) {
			hitTime += Gdx.graphics.getDeltaTime();
		}
	}
	
	public void setHit(float timeOfHit) {
		state = STATE_HIT;
		hitTime = 0;
		this.timeOfHit = timeOfHit;
	}
	
	/**
	 * Determine if the TrackPoint is currently within the range where we can hit it
	 */
	public boolean isWithinHitRange(float hitTime) {
		if (hitTime < time + hitRange && hitTime > time - hitRange) {
			return true;
		}
		else {
			return false;
		}
	}
}
