package dev.ui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import dev.Handler;

public abstract class UIObject {

	protected int x, y, width, height;
	protected String text;
	protected boolean hovering = false;
	protected Rectangle bounds;
	protected Handler handler;

	public UIObject(Handler handler, int x, int y, int width, int height, String text, Rectangle bounds) {
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = text;
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
