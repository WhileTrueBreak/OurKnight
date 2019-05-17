package dev.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import dev.Handler;

public class MenuState extends State{
	
	private Handler handler;
	
	private boolean isLoading = false;
	private int timer = 4;
	
	public MenuState(Handler handler) {
		this.handler = handler;
	}
	@Override
	public void update() {
		if(timer <= 0) {
			State gameState;
			gameState = new GameState(handler);
			State.setCurrentState(gameState);
		}
		if(handler.getKeyManager().isKeyPressed(KeyEvent.VK_S)) {
			isLoading = true;
		}
		if(isLoading)
			timer--;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(new Color(0, 255, 255));
		g.fillRect(0, 0, handler.getWidth(), handler.getHeight());
		//Text//
		if(isLoading) {
			String text = "Loading...";
			g.setFont(new Font("TimesRoman", Font.PLAIN, 100));
			g.setColor(new Color(255, 0, 0));
			g.drawString(text, handler.getWidth()/2-200, 100);
		}else {
			String text = "Menu";
			g.setFont(new Font("TimesRoman", Font.PLAIN, 100));
			g.setColor(new Color(255, 0, 0));
			g.drawString(text, handler.getWidth()/2-100, 100);
		}
	}
}
