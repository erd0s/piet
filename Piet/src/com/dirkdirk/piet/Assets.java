package com.dirkdirk.piet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.esotericsoftware.spine.SkeletonRenderer;

public class Assets {
	
	public static TextureRegion iconN;
	public static TextureRegion iconF;
	public static TextureRegion iconV;
	public static TextureRegion iconJ;
	public static TextureRegion trackLines;
	public static TextureRegion[] numbers;

	public static SkeletonData skeletonData;
	public static Skeleton skeleton;
	
	public static Music music;
	
	public static void load() {
		numbers = new TextureRegion[10];
		TextureAtlas atlas;
		atlas = new TextureAtlas(Gdx.files.internal("assets.atlas"));
		// AtlasRegion extends TextureRegion
		iconN = atlas.findRegion("track-n");
		iconF = atlas.findRegion("track-f");
		iconV = atlas.findRegion("track-v");
		iconJ = atlas.findRegion("track-j");
		trackLines = atlas.findRegion("track-lines");
		
		music = Gdx.audio.newMusic(Gdx.files.internal("beats.ogg"));
		
		TextureAtlas numbersAtlas = new TextureAtlas(Gdx.files.internal("numbers.atlas"));
		numbers[0] = numbersAtlas.findRegion("num", 0);
		numbers[1] = numbersAtlas.findRegion("num", 1);
		numbers[2] = numbersAtlas.findRegion("num", 2);
		numbers[3] = numbersAtlas.findRegion("num", 3);
		numbers[4] = numbersAtlas.findRegion("num", 4);
		numbers[5] = numbersAtlas.findRegion("num", 5);
		numbers[6] = numbersAtlas.findRegion("num", 6);
		numbers[7] = numbersAtlas.findRegion("num", 7);
		numbers[8] = numbersAtlas.findRegion("num", 8);
		numbers[9] = numbersAtlas.findRegion("num", 9);

		// Load the piet animation
		
		TextureAtlas pietAtlas = new TextureAtlas(Gdx.files.internal("piet.atlas"));
		SkeletonJson json = new SkeletonJson(pietAtlas);
		skeletonData = json.readSkeletonData(Gdx.files.internal("piet.json"));
		
		skeleton = new Skeleton(skeletonData); 
	}
	
	public static void unload() {
		music.dispose();
	}
}
