package dev.entity.creature;

import java.awt.Rectangle;

import dev.Handler;
import dev.entity.Entity;

public abstract class Creature extends Entity{
	
	public Creature(Handler handler, int x, int y) {
		super(handler, x, y);
	}
	protected int health = 20;
	protected int speed = 20;
	
	protected int width = 32, height = 32;
	
	protected boolean checkCollide() {
		for (Entity e:handler.getWorld().getEntityManager().getEntities()) {
			Rectangle b1 = getHitbox().getBounds(), b2 = e.getHitbox().getBounds();
			b1.x = (int) x;
			b1.y = (int) y;
			b2.x = (int) e.getX();
			b2.y = (int) e.getY();
			if (b1.intersects(b2) && e != this) {
				e.onCollision();
				if (e.isSolid()) 
					return true;
			}else {
				continue;
			}
		}
		return false;
	}
	
}
