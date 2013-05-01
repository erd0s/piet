package com.dirkdirk.piet;

import com.esotericsoftware.spine.Animation;

/**
 * Holds a single disposable instance of an animation. We use objects of this class
 * to keep track of the different body animations and their penetration.
 */
public class AnimationInstance {
	public float time = 0;
	public Animation animation;
	public int type;
	
	public AnimationInstance(Animation animation) {
		this.animation = animation;
		
		String name = animation.getName();
		if (name.equals("rightArm1") || name.equals("rightArm2")) {
			type = AnimationManager.RIGHT_ARM;
		}
		else if (name.equals("leftArm1") || name.equals("leftArm2")) {
			type = AnimationManager.LEFT_ARM;
		}
		else if (name.equals("rightLeg1") || name.equals("rightLeg2")) {
			type = AnimationManager.RIGHT_LEG;
		}
		else if (name.equals("leftLeg1") || name.equals("leftLeg2")) {
			type = AnimationManager.LEFT_LEG;
		}
	}
	
	public void update(float delta) {
		time += delta;
	}
	
	public boolean isFinished() {
		if (time > animation.getDuration()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public String getName() {
		return animation.getName();
	}
}
