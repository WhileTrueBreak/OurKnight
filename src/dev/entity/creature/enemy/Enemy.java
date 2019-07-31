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
		boolean moved = true;
		
		float dx = destX - x, dy = destY - y;
		float mag = (float) Math.sqrt(dx*dx+dy*dy);
		if(mag > speed) {
			dx = (float) (dx*speed*handler.getSpeedMult()/mag);
			dy = (float) (dy*speed*handler.getSpeedMult()/mag);
		}
		//TODO find a better solution to this fix
		//fixes enemies getting stuck on edge of blocks
		if(Math.abs(dx) < 0.01f) x = destX;
		if(Math.abs(dy) < 0.01f) y = destY;
		
		x += dx;
		Rectangle cHitbox = collided();
		if (cHitbox != null) {
			moved = false;
			if(Math.signum(dx*speed*handler.getSpeedMult()/mag) == 1)
				x = cHitbox.x-hitbox.width;
			else
				x = cHitbox.x+cHitbox.width;
		}
		y += dy;
		cHitbox = collided();
		if (cHitbox != null) {
			moved = false;
			if(Math.signum(dy*speed*handler.getSpeedMult()/mag) == 1)
				y = cHitbox.y-hitbox.height;
			else
				y = cHitbox.y+cHitbox.height;
		}
		if(moved) handler.getWorld().requireNavmeshUpdate();
	}

}
