package dev.ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class UIManager {
	
	ArrayList<UIObject>UIObjects;
	
	public UIManager(){
		UIObjects = new ArrayList<UIObject>();
	}
	
	public void update() {
		for(UIObject ui:UIObjects) ui.update();
	}
	
	public void render(Graphics g) {
		for(UIObject ui:UIObjects) ui.render(g);
	}
	
	public void onMouseMove(MouseEvent e) {
		for(UIObject ui:UIObjects) ui.onMouseMove(e);
	}
	
	public void onMouseRelease(MouseEvent e) {
		for(UIObject ui:UIObjects) ui.onMouseRelease(e);
	}
	
	public void addUIObject(UIObject o) {
		UIObjects.add(o);
	}
	
	public void removeUIObject(UIObject o) {
		UIObjects.remove(o);
	}
	
}
