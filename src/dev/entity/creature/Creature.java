package dev.entity.creature;

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
			if (getHitbox().getBounds().intersects(e.getHitbox().getBounds()) && e != this) {
				e.onCollision();
				if (e.isSolid()) return true;
			}else {
				continue;
			}
		}
		return false;
	}
	
}
