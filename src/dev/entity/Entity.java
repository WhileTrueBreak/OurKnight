package dev.entity;

import java.awt.Graphics;
import java.awt.Rectangle;

import dev.Handler;

public abstract class Entity {
	
	public static int width = 32, height = 32;
	
	protected int x, y;
	protected Rectangle hitbox;
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

	public abstract Rectangle getHitbox();
}
