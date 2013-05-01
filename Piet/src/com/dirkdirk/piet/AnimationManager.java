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

public class AnimationManager implements EventListener {
	
	private SpriteBatch batch;
	private SkeletonRenderer renderer;
	private SkeletonData skeletonData;
	private Skeleton skeleton;
	
	private Animation[] leftArm = new Animation[2];
	private Animation[] leftLeg = new Animation[2];
	private Animation[] rightArm = new Animation[2];
	private Animation[] rightLeg = new Animation[2];
	private Animation headBop;
	private Animation levelUpSpin;
	
	public static final int LEFT_ARM = 0;
	public static final int LEFT_LEG = 1;
	public static final int RIGHT_LEG = 2;
	public static final int RIGHT_ARM = 3;
	
	private String[] levels = {"level1","level2", "level3", "level4", "level5", "level6"}; 
	
	private float time = 0;
	
	private ArrayList<AnimationInstance> currentAnimations;
	
	public AnimationManager(SpriteBatch batch) {
		this.batch = batch;
		renderer = new SkeletonRenderer();
		skeletonData = Assets.skeletonData;
		skeleton = Assets.skeleton;
		
		currentAnimations = new ArrayList();
		
		skeleton.setToBindPose();
		
		// Set up references to animations
		leftArm[0] = skeletonData.findAnimation("leftArm1");
		leftArm[1] = skeletonData.findAnimation("leftArm2");
		rightArm[0] = skeletonData.findAnimation("rightArm1");
		rightArm[1] = skeletonData.findAnimation("rightArm2");
		leftLeg[0] = skeletonData.findAnimation("leftLeg1");
		leftLeg[1] = skeletonData.findAnimation("leftLeg2");
		rightLeg[0] = skeletonData.findAnimation("rightLeg1");
		rightLeg[1] = skeletonData.findAnimation("rightLeg2");
		headBop = skeletonData.findAnimation("head bop");
		levelUpSpin = skeletonData.findAnimation("levelUpSpin");
		
		skeleton.setSkin("level1");
		skeleton.setSlotsToBindPose();
		
		Bone root = skeleton.getRootBone();
		root.x = Gdx.graphics.getWidth() / 2;
		root.y = 150;
		skeleton.updateWorldTransform();
	}
	
	public void update(float delta) {
		time += delta;
		
		// Apply the headbop always
		headBop.apply(skeleton, time, true);
		
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
	
	public void startAnimation(int bodyPart) {
		// Check that animation isn't already in the playing list, if it is remove it and add a fresh one
		Iterator<AnimationInstance> i = currentAnimations.iterator();
		while (i.hasNext()) {
		   AnimationInstance animInstance = i.next(); // must be called before you can call i.remove()
		   if (animInstance.type == bodyPart) {
			   i.remove();
		   }
		}
		
		int seed;
		switch (bodyPart) {
			case LEFT_LEG:
				seed = (int) Math.floor(Math.random() * leftLeg.length);
				currentAnimations.add(new AnimationInstance(leftLeg[seed]));
				break;
			case LEFT_ARM:
				seed = (int) Math.floor(Math.random() * leftArm.length);
				currentAnimations.add(new AnimationInstance(leftArm[seed]));
				break;
			case RIGHT_LEG:
				seed = (int) Math.floor(Math.random() * rightLeg.length);
				currentAnimations.add(new AnimationInstance(rightLeg[seed]));
				break;
			case RIGHT_ARM:
				seed = (int) Math.floor(Math.random() * rightArm.length);
				currentAnimations.add(new AnimationInstance(rightArm[seed]));
				break;
		}
		
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
		
	}
	@Override
	public void levelUp(int level) {
		//currentAnimations.add(new AnimationInstance(levelUpSpin));
		skeleton.setSkin(levels[level-1]);
		
	}
}
