package dev.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.File;

import dev.Handler;
import dev.ui.ClickListener;
import dev.ui.UIManager;
import dev.ui.button.UIButtonImpactAnimation;
import dev.utils.Draw;

public class MenuState extends State{

	private Handler handler;

	private boolean isLoading = false, loadSave = false;;
	private int timer = 2;

	private UIManager uiManager;

	public MenuState(Handler handler) {
		this.handler = handler;	
		uiManager = new UIManager();
		handler.getMouseManager().setUIManager(uiManager);
		//add new save button
		uiManager.addUIObject(new UIButtonImpactAnimation(handler, handler.getWidth()/2-100, 400, 200, 50, "New Save", 
				new Rectangle(handler.getWidth()/2-100, 400, 200, 50), new ClickListener() {
			public void onClick() {
				loadSave = false;
				isLoading = true;
			}
		}));
		//check is save exists
		if(new File("world/info.json").exists() && new File("world/sectors").exists()) {
			//add continue button
			uiManager.addUIObject(new UIButtonImpactAnimation(handler, handler.getWidth()/2-100, 470, 200, 50, "Continue", 
					new Rectangle(handler.getWidth()/2-100, 470, 200, 50), new ClickListener() {
				public void onClick() {
					loadSave = true;
					isLoading = true;
				}
			}));
		}
		//add some useless buttons
		uiManager.addUIObject(new UIButtonImpactAnimation(handler, handler.getWidth()/2-100, 540, 200, 50, "Settings", 
				new Rectangle(handler.getWidth()/2-100, 540, 200, 50), new ClickListener() {
			public void onClick() {
			}
		}));
		//add quit button
		uiManager.addUIObject(new UIButtonImpactAnimation(handler, handler.getWidth()/2-100, 610, 200, 50, "Quit", 
				new Rectangle(handler.getWidth()/2-100, 610, 200, 50), new ClickListener() {
			public void onClick() {
				System.exit(0);
			}
		}));
	}
	@Override
	public void update() {
		uiManager.update();
		if(timer <= 0) {
			GameState gameState;
			gameState = new GameState(handler);
			gameState.setLoadSave(loadSave);
			gameState.init();
			State.setCurrentState(gameState);
		}
		if(handler.getKeyManager().isKeyPressed(KeyEvent.VK_S)) isLoading = true;
		if(isLoading) timer--;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(new Color(51, 51, 51));
		g.fillRect(0, 0, handler.getWidth(), handler.getHeight());
		//Text//
		if(isLoading) {//loading graphics

		}else {//normal graphics
			g.setColor(new Color(255, 255, 255));
			Draw.drawCenteredString(g, "MENU", new Rectangle((int)(handler.getWidth()/2-200), 100, 400, 200), new Font("Arial", Font.PLAIN, 100));
			uiManager.render(g);
		}
	}
}
