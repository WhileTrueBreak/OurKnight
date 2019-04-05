package dev.entity;

import java.awt.Graphics;

import dev.Handler;

public abstract class Entity {
	
	protected int x, y;
	protected Handler handler;
	
	public Entity(Handler handler, int x, int y) {
		this.x = x;
		this.y = y;
		this.handler = handler;
	}
	
	public abstract void update();
	
	public abstract void render(Graphics g);

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
}
