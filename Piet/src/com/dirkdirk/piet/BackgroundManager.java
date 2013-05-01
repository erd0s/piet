package com.dirkdirk.piet;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.Animation;
import com.esotericsoftware.spine.Bone;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.esotericsoftware.spine.SkeletonRenderer;

public class BackgroundManager implements EventListener {
	
	private SkeletonData skeletonData;
	private Skeleton skeleton;
	private SkeletonRenderer renderer;
	
	private Animation pulse;
	private Animation shuffle;
	
	private float time = 0;
	private SpriteBatch batch;
	
	private String[] backgrounds = {"background1", "background2", "background3", "background4", "background5", "background6"};

	private ArrayList<AnimationInstance> currentAnimations;
	
	public BackgroundManager(SpriteBatch batch) {
		// Save the batch
		this.batch = batch;
		
		currentAnimations = new ArrayList();
		
		renderer = new SkeletonRenderer();
		
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("background.atlas"));
		SkeletonJson json = new SkeletonJson(atlas);
		skeletonData = json.readSkeletonData(Gdx.files.internal("background.json"));
		
		skeleton = new Skeleton(skeletonData);
		skeleton.setToBindPose();
		
		// Set up references to animations
		pulse = skeletonData.findAnimation("pulse");
		shuffle = skeletonData.findAnimation("shuffle");
		
		skeleton.setSkin("background1");
		skeleton.setSlotsToBindPose();
		
		
		final Bone root = skeleton.getRootBone();
		root.x = Gdx.graphics.getWidth() / 2;
		root.y = Gdx.graphics.getHeight() / 2;
		skeleton.updateWorldTransform();
	}
	
	public void update(float delta) {
		// Update the time
		time += delta;
		
		// Apply the shuffle animation, that happens continuously
		shuffle.apply(skeleton, time, true);
		
		// Apply any necessary animations to the skeleton
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
		currentAnimations.add(new AnimationInstance(pulse));
	}
	@Override
	public void missNote() {
		Gdx.app.log("BackgroundManager", "missNote");
	}
	@Override
	public void powerUp(float value) {
		// Maybe do some special coloured animation here to signify the powerup
	}
	@Override
	public void levelUp(int level) {
		skeleton.setSkin(backgrounds[level-1]);
	}
}
