package dev.entity;

import java.awt.Graphics;
import java.awt.Rectangle;

import dev.Handler;

public abstract class Entity {
	
	public static int width = 32, height = 32;
	
	public int x;

	protected int y;
	protected Handler handler;
	
	public Entity(Handler handler, int x, int y) {
		this.x = x;
		this.y = y;
		this.handler = handler;
	}
	
	private Rectangle hitbox = new Rectangle(x, y, width, height);
	
	public abstract void update();
	
	public abstract void render(Graphics g);

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Rectangle getHitbox() {
		return hitbox;
	}
	public void setHitboxAttrb(int x, int y, int width, int height) {
		hitbox.x = x;
		hitbox.y = y;
		hitbox.width = width;
		hitbox.height = height;
	}
}
