package dev.states;

import java.awt.Graphics;

import dev.Handler;

public class MenuState extends State{
	
	private Handler handler;
	
	public MenuState(Handler handler) {
		this.handler = handler;
	}
	@Override
	public void update() {
		State gameState;
		gameState = new GameState(handler);
		State.setCurrentState(gameState);
	}

	@Override
	public void render(Graphics g) {
		
	}

}
