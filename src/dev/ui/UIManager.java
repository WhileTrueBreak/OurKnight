package dev.ui;

import java.awt.Graphics;
import java.util.ArrayList;

import dev.Handler;

public class UIManager {
	
	private Handler handler;
	
	private ArrayList<UI> ui;
	
	public UIManager(Handler handler) {
		this.handler = handler;
		ui = new ArrayList<UI>();
	}
	
	public void addUI(UI ui) {
		this.ui.add(ui);
	}
	
	public void removeUI(UI u) {
		ui.remove(u);
	}
	
	public ArrayList<UI> getUI() {
		return ui;
	}
	
	public void update() {
		for (UI u:ui) {
			u.update();
		}
	}
	
	public void render(Graphics g) {
		for (UI u:ui) {
			u.render(g);
		}
	}

}
