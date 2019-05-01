package dev.tiles;

import java.awt.Graphics;
import java.awt.Rectangle;

import dev.Handler;
import dev.entity.Entity;

public abstract class Tile {
	
	public static int TILE_WIDTH = 32, TILE_HEIGHT = 32;
	
	protected Rectangle bounds;
	protected int x,y,spriteID;
	
	protected Handler handler;
	
	public Tile(Handler handler, int x, int y, int spriteid) {
		this.x=x;
		this.y=y;
		this.handler=handler;
	}
	
	public abstract void update();
	public abstract void render(Graphics g);
	
	public void onCollision(Entity e) {
		
	}
	
	public boolean isSolid() {
		return false;
	}
}
