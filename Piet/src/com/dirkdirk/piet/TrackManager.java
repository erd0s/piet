package com.dirkdirk.piet;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.dirkdirk.piet.screens.GameScreen;

public class TrackManager {
	
	public ArrayList<TrackPoint> trackPoints;
	
	private SpriteBatch batch; 
	private GameScreen gameScreen;
	
	// This is the total time until the TrackPoint hits the bar
	public static float totalTimeUntilBar = 3f;
	
	public TrackManager(GameScreen gameScreen) {
		this.gameScreen = gameScreen;
		this.batch = gameScreen.batch;
		trackPoints = new ArrayList();
	}
	
	public void add(TrackPoint p) {
		trackPoints.add(p);
	}
	
	// Passes in the gameTime rather than delta, as we're mostly dealing with the full time numbers
	public void update(float gameTime) {
		// Update all the trackpoints 
		for (TrackPoint trackPoint : trackPoints) {
			trackPoint.update(gameTime);
		}
		
	}
	
	public void render() {
		// Draw the bar
		batch.draw(Assets.trackLines, 0, 0, 0, 0, 512, 384, 2, 2, 0);
		
		// Render any track points that are in the state ONSCREEN
		for (TrackPoint trackPoint : trackPoints) {
			if (trackPoint.state == TrackPoint.STATE_ONSCREEN) {
				batch.draw(trackPoint.icon, trackPoint.xPos - trackPoint.width/2, trackPoint.yPos - trackPoint.height/2);
			}
		}
		
		// Now render any track points that are in the state HIT, with regard to their hitTime
		for (TrackPoint trackPoint: trackPoints) {
			if (trackPoint.state == TrackPoint.STATE_HIT) {
				// Total time tweening should be 1/3 of TrackManager.totalTimeUntilBar
				float percentage = trackPoint.hitTime / (totalTimeUntilBar / 3);
				float reversePercentage = 1 - percentage;
				float interpolatedPercentage = Interpolation.circleIn.apply(reversePercentage);
				Color c = batch.getColor();
				batch.setColor(c.r, c.g, c.b, interpolatedPercentage);
				// Incorporate rotation and x offset into the draw
				float x = trackPoint.xPos - trackPoint.width / 2 + 100 * trackPoint.signMultiplier * percentage;
				float y = trackPoint.yPos - trackPoint.height / 2;
				float interpolatedRotation = percentage * trackPoint.rotationFactor;
				
				// Outline the power points
//				Color col = batch.getColor();
//				if (trackPoint.powerUpValue > 0) {
//					batch.setColor(col.r, 1, col.b, col.a);
//				}
//				
				batch.draw(trackPoint.icon, x, y, 73/2, 90/2, 73, 90, 1, 1, interpolatedRotation);
				batch.setColor(c.r, c.g, c.b, c.a);
			}
		}
	}
	
	public void keyPress(int type) {
		// Check if they've hit a TrackPoint
		float hitTime = gameScreen.gameTime;
		boolean hit = false;
		for (TrackPoint trackPoint : trackPoints) {
			// Only look at relevant TrackPoints
			if (trackPoint.state == TrackPoint.STATE_ONSCREEN && type == trackPoint.type) {
				// Check if we're within the current hit range
				if (trackPoint.isWithinHitRange(hitTime)) {
					// Yes we are, so change this points state, exit the loop (we can only hit one point per keypress)
					// Here we'd also want to trigger some kind of event that other classes can listen to and do stuff, like score, fx etc.
					trackPoint.setHit(gameScreen.gameTime);
					hit = true;
					float distance = Math.abs(trackPoint.time - gameScreen.gameTime);
					gameScreen.eventDispatcher.hitNote(distance);
					// Was this a powerup? If so, dispatch the powerup event
					if (trackPoint.powerUpValue > 0) {
						gameScreen.eventDispatcher.powerUp(trackPoint.powerUpValue);
					}
					break;
				}
			}
		}
		if (hit == false) {
			// If we got to here they didn't hit anything, so trigger an event to do something, like decrease the minimeter, play a sound etc.
			gameScreen.eventDispatcher.missNote();
		}
	}
}
