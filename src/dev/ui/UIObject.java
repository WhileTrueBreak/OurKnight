package dev.ui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

public abstract class UIObject {

	protected int x, y, width, height;
	protected boolean hovering = false;
	protected Rectangle bounds;

	public UIObject(int x, int y, int width, int height, Rectangle bounds) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.bounds = bounds;
	}
	
	public abstract void update();

	public abstract void render(Graphics g);

	public abstract void onClick();

	public void onMouseMove(MouseEvent e) {
		if(bounds.contains(e.getX(), e.getY())) {
			hovering = true;
		}else {
			hovering = false;
		}
	}

	public void onMouseRelease(MouseEvent e) {
		if(hovering) onClick();
	}
}
