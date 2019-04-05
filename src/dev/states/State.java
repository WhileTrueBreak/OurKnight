package dev.states;

import java.awt.Graphics;

public abstract class State {
	
	static State current_state = null;
	
	public static State getCurrentState() {
		return current_state;
	}
	
	public static void setCurrentState(State s) {
		current_state = s;
	}
	
	public abstract void update();
	
	public abstract void render(Graphics g);
	
}
