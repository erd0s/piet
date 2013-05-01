package com.dirkdirk.piet.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dirkdirk.piet.Assets;
import com.esotericsoftware.spine.Animation;
import com.esotericsoftware.spine.Bone;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonRenderer;

public class HomeScreen implements Screen {
	
	private SpriteBatch batch;
	private SkeletonRenderer renderer;
	private Skeleton skeleton;
	private SkeletonData skeletonData;
	
	private float screenTime;
	
	private Animation layingDownAnimation;
	
	private Game game;
	
	public HomeScreen(Game game) {
		this.game = game;
		
		// We need a SpriteBatch
		batch = new SpriteBatch();
		
		// We need a SkeletonRenderer
		renderer = new SkeletonRenderer();
		skeleton = Assets.skeleton;
		skeletonData = Assets.skeletonData;
		
		layingDownAnimation = skeletonData.findAnimation("laying down");
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		screenTime += delta;
		
		// Apply the animation
		layingDownAnimation.apply(skeleton, screenTime, true);
		skeleton.updateWorldTransform();
		skeleton.update(delta);

		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		// Render
		batch.begin();
		renderer.draw(batch, skeleton);
		batch.end();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		screenTime = 0;
		
		skeleton.setToBindPose();
		skeleton.setSkin("happy eyes");
		skeleton.setSlotsToBindPose();
		
		Bone root = skeleton.getRootBone();
		root.x = Gdx.graphics.getWidth() / 2;
		root.y = 450;
		skeleton.updateWorldTransform();
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
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
