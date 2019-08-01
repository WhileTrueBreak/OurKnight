package dev.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import dev.Handler;
import dev.ui.ClickListener;
import dev.ui.UIManager;
import dev.ui.button.UIButtonImpactAnimation;

public class MenuState extends State{
	
	private Handler handler;
	
	private boolean isLoading = false;
	private int timer = 2;
	
	private UIManager uiManager;
	
	public MenuState(Handler handler) {
		this.handler = handler;	
		uiManager = new UIManager();
		handler.getMouseManager().setUIManager(uiManager);
		
		uiManager.addUIObject(new UIButtonImpactAnimation(handler, handler.getWidth()/2-100, 400, 200, 50, "Start", 
				new Rectangle(handler.getWidth()/2-100, 400, 200, 50), new ClickListener() {
			public void onClick() {
				isLoading = true;
			}
		}));
	}
	@Override
	public void update() {
		uiManager.update();
		if(timer <= 0) {
			State gameState;
			gameState = new GameState(handler);
			State.setCurrentState(gameState);
		}
		if(handler.getKeyManager().isKeyPressed(KeyEvent.VK_S)) isLoading = true;
		if(isLoading) timer--;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(new Color(179, 179, 179));
		g.fillRect(0, 0, handler.getWidth(), handler.getHeight());
		//Text//
		if(isLoading) {//loading graphics
			String text = "Loading...";
			g.setFont(new Font("TimesRoman", Font.PLAIN, 100));
			g.setColor(new Color(255, 0, 0));
			g.drawString(text, handler.getWidth()/2-200, 100);
		}else {//normal graphics
			uiManager.render(g);
		}
	}
}
