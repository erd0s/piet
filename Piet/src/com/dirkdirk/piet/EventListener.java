package com.dirkdirk.piet;

public interface EventListener {
	
	/**
	 * @param distance
	 * The distance in seconds from hitting the note in the exact correct spot 
	 */
	public void hitNote(float distance);
	
	public void missNote();
	
	public void powerUp(float value);
	
	public void levelUp(int level);
	
}
