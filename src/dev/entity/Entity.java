package dev.entity;

import java.awt.Graphics;
import java.awt.Rectangle;

import dev.Handler;

public abstract class Entity {
	
	public static int width = 32, height = 32;
	
	protected float x,y;
	protected boolean isSolid;
	protected Handler handler;
	protected Rectangle hitbox;
	
	public Entity(Handler handler, int x, int y) {
		this.x = x;
		this.y = y;
		this.handler = handler;
		isSolid = false;
		hitbox = new Rectangle(0, 0, width, height);
	}	
	public abstract void update();
	
	public abstract void render(Graphics g);

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
	
	public boolean isSolid() {
		return isSolid;
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
	
	public abstract void onCollision();

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
}
