package dev.ui;

import java.awt.Graphics;
import java.util.ArrayList;

public class UIManager {
	
	ArrayList<UIObject>UIObjects = new ArrayList<UIObject>();
	
	UIManager(){
		
	}
	
	public void update() {
		for(UIObject ui:UIObjects) ui.update();
	}
	
	public void render(Graphics g) {
		for(UIObject ui:UIObjects) ui.render(g);
	}
	
}
