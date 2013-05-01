package com.dirkdirk.piet;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.Animation;
import com.esotericsoftware.spine.Bone;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.esotericsoftware.spine.SkeletonRenderer;

public class ForegroundManager implements EventListener {

	private SkeletonData skeletonData;
	private Skeleton skeleton;
	private SkeletonRenderer renderer;
	
	private Animation levelUpExplode;
	
	private float time = 0;
	private SpriteBatch batch;

	private ArrayList<AnimationInstance> currentAnimations;
	
	public ForegroundManager(SpriteBatch batch) {
		this.batch = batch;
		currentAnimations = new ArrayList();
		
		renderer = new SkeletonRenderer();
		
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("foreground.atlas"));
		SkeletonJson json = new SkeletonJson(atlas);
		skeletonData = json.readSkeletonData(Gdx.files.internal("foreground.json"));
		
		skeleton = new Skeleton(skeletonData);
		skeleton.setToBindPose();
		
		// Set up references to animations
		levelUpExplode = skeletonData.findAnimation("levelUpExplode");
		
		final Bone root = skeleton.getRootBone();
		root.x = Gdx.graphics.getWidth() / 2;
		root.y = Gdx.graphics.getHeight() / 2;
		skeleton.updateWorldTransform();
	}
	
	public void update(float delta) {
		time += delta;
		Iterator<AnimationInstance> i = currentAnimations.iterator();
		while (i.hasNext()) {
		   AnimationInstance animInstance = i.next(); // must be called before you can call i.remove()
		   animInstance.update(delta);
		   if (animInstance.isFinished()) {
			   i.remove();
		   }
		   animInstance.animation.apply(skeleton, animInstance.time, false);
		}

		skeleton.updateWorldTransform();
		skeleton.update(Gdx.graphics.getDeltaTime());
	}
	
	public void render() {
		renderer.draw(batch, skeleton);
	}
	
	@Override
	public void hitNote(float distance) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void missNote() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void powerUp(float value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void levelUp(int level) {
		Gdx.app.log("SEEMS", "We HERE");
		currentAnimations.add(new AnimationInstance(levelUpExplode));
	}
	
}
