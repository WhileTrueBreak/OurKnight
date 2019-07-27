package dev.entity.creature.enemy;

import java.awt.Rectangle;

import dev.Handler;
import dev.entity.Entity;
import dev.entity.creature.Creature;

public abstract class Enemy extends Creature{
	
	protected long ticks = 0, startTime;
	
	protected boolean inTimer = false;
	
	protected float speed;
	
	protected Entity target;
	protected float targetX, targetY;
	
	public Enemy(Handler handler, int x, int y) {
		super(handler, x, y);
		target = handler.getPlayer();
		targetX = target.getX();
		targetY = target.getY();
	}
	
	protected void move(float destX, float destY, float speed) {
		float dx = destX - x, dy = destY - y;
		float mag = (float) Math.sqrt(dx*dx+dy*dy);
		handler.getWorld().requireNavmeshUpdate();
		if(mag > speed) {
			dx = (float) (dx*speed/mag)*1.1f;
			dy = (float) (dy*speed/mag)*1.1f;
		}
		x += dx;
		Rectangle cHitbox = collided();
		if (cHitbox != null) {
			if(Math.signum(dx*speed/mag) == 1)
				x = cHitbox.x-hitbox.width;
			else
				x = cHitbox.x+cHitbox.width;
		}
		y += dy;
		cHitbox = collided();
		if (cHitbox != null) {
			if(Math.signum(dy*speed/mag) == 1)
				y = cHitbox.y-hitbox.height;
			else
				y = cHitbox.y+cHitbox.height;
		}
	}

}
