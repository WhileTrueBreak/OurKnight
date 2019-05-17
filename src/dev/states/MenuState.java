package dev.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import dev.Handler;
import dev.audio.MusicPlayer;
import dev.utils.Utils;

public class MenuState extends State{
	
	private Handler handler;
	
	private boolean isLoading = false;
	private int timer = 1;
	
	public MenuState(Handler handler) {
		this.handler = handler;
	}
	@Override
	public void update() {
		if(timer <= 0) {
			State gameState;
			gameState = new GameState(handler);
			State.setCurrentState(gameState);
			new MusicPlayer("effects/blip").start();
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
		if(isLoading) {//loading graphics
			String text = "Loading...";
			g.setFont(new Font("TimesRoman", Font.PLAIN, 100));
			g.setColor(new Color(255, 0, 0));
			g.drawString(text, handler.getWidth()/2-200, 100);
		}else {//normal graphics
			g.setFont(new Font("TimesRoman", Font.PLAIN, 100));
			g.setColor(new Color(255, 0, 0));
			g.drawString("Menu", handler.getWidth()/2-100, 100);
			g.drawString("Press 's' to continue", handler.getWidth()/2-400, 300);
		}
	}
}
