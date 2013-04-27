package com.dirkdirk.piet;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Bone;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.esotericsoftware.spine.SkeletonRenderer;
import com.esotericsoftware.spine.SkeletonRendererDebug;

public class Piet implements ApplicationListener {
	
	SpriteBatch batch;
	SkeletonRenderer renderer;
	SkeletonRendererDebug debugRenderer;

	TextureAtlas atlas;
	Skeleton skeleton;
	Bone root;
	AnimationState state;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		renderer = new SkeletonRenderer();
		debugRenderer = new SkeletonRendererDebug();

		atlas = new TextureAtlas(Gdx.files.internal("piet.atlas"));
		SkeletonJson json = new SkeletonJson(atlas);
		SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal("piet.json"));

		// Define mixing between animations.
		AnimationStateData stateData = new AnimationStateData(skeletonData);
		stateData.setMix("left arm dance", "right arm dance", 0.4f);
//		stateData.setMix("jump", "walk", 0.4f);

		state = new AnimationState(stateData);
		state.setAnimation("raise brows", false);

		skeleton = new Skeleton(skeletonData);

		root = skeleton.getRootBone();
		root.setX(400);
		root.setY(400);
		Gdx.app.log("hmm", "" + root.getX());

		skeleton.updateWorldTransform();
	}

	@Override
	public void dispose() {
		atlas.dispose();
	}

	@Override
	public void render() {
		state.update(Gdx.graphics.getDeltaTime());

		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		state.apply(skeleton);
		if (state.getAnimation().getName().equals("walk")) {
//			// After one second, change the current animation. Mixing is done by AnimationState for you.
			if (state.getTime() > 2) state.setAnimation("left arm dance", false);
		} else {
			if (state.getTime() > 1) state.setAnimation("right arm dance", false);
		}
		skeleton.updateWorldTransform();

		batch.begin();
		renderer.draw(batch, skeleton);
		batch.end();

//		debugRenderer.draw(batch, skeleton);
	}

	@Override
	public void resize(int width, int height) {
		batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
		debugRenderer.getShapeRenderer().setProjectionMatrix(batch.getProjectionMatrix());
		
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
