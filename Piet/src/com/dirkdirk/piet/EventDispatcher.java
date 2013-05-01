package com.dirkdirk.piet;

import java.util.ArrayList;

public class EventDispatcher {

	private ArrayList<EventListener> listeners;
	
	public EventDispatcher() {
		listeners = new ArrayList();
	}
	
	public void registerListener(EventListener listener) {
		listeners.add(listener);
	}
	
	public void hitNote(float distance) {
		for (EventListener listener : listeners) {
			listener.hitNote(distance);
		}
	}
	
	public void missNote() {
		for (EventListener listener : listeners) {
			listener.missNote();
		}
	}
	
	public void powerUp(float value) {
		for (EventListener listener : listeners) {
			listener.powerUp(value);
		}
	}
	
	public void levelUp(int level) {
		for (EventListener listener : listeners) {
			listener.levelUp(level);
		}
	}
	
}
