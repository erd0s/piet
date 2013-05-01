package com.dirkdirk.piet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.dirkdirk.piet.screens.GameScreen;

public class TrackLoader {
	
	public static TrackManager loadTrack(String filename, GameScreen gameScreen) {
		JsonValue root = new JsonReader().parse(Gdx.files.internal(filename));
		
		TrackManager trackManager = new TrackManager(gameScreen);
		for (JsonValue trackMap = root.getChild("notes"); trackMap != null; trackMap = trackMap.next()) {
			String key = trackMap.getString("key");
			float time = trackMap.getFloat("time");
			float powerUpValue = trackMap.getFloat("powerUp");
			if (powerUpValue == 0)
				trackManager.add(new TrackPoint(key, time));
			else 
				trackManager.add(new TrackPoint(key, time, powerUpValue));
		}
		
		return trackManager;
	}
}
