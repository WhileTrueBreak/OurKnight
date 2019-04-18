package dev.ui;

import java.awt.Graphics;

import dev.Handler;

public abstract class UI {
	
	protected Handler handler;
	
	protected int x,y;
	
	public UI(Handler handler, int x, int y) {
		this.handler = handler;
		this.x = x;
		this.y = y;
	}
	
	public abstract void update();
	
	public abstract void render(Graphics g);
}
