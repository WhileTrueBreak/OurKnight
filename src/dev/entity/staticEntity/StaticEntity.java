package dev.entity.staticEntity;

import dev.Handler;
import dev.entity.Entity;

public abstract class StaticEntity extends Entity{

	protected int width, height;
	
	protected boolean destructible = false;
	protected int health = 1;
	
	protected int spriteID;
	
	public StaticEntity(Handler handler, int x, int y, int width, int height, int spriteID) {
		super(handler, x, y);
		this.spriteID = spriteID;
		this.width = width;
		this.height = height;
	}
	
}
