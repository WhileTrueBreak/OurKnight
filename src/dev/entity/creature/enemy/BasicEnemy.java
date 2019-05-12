//TEMPERARY CODE TO TEST ENEMY

package dev.entity.creature.enemy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Date;

import dev.Handler;
import dev.entity.Entity;
import dev.ui.Health;
import dev.ui.UI;

public class BasicEnemy extends Enemy{
	
	public int width = 64, height = 64;

	public BasicEnemy(Handler handler, int x, int y) {
		super(handler, x, y);
		health = 10;
		speed = 2;
	}
	
	private void move() {
		float destX = handler.getWorld().getPlayer().getX();
		float destY = handler.getWorld().getPlayer().getY();
		float dx = destX - x, dy = destY - y;
		float mag = (float) Math.sqrt(dx*dx+dy*dy);
		if(mag > speed) {
			dx = dx*speed/mag;
			dy = dy*speed/mag;
		}
		
		float tempx = x, tempy = y;
		x += dx;
		if (collided()) {
			x = tempx;
		}
		y += dy;
		if (collided()) {
			y = tempy;
		}
	}
	
	@Override
	public void update() {
		move();
	}

	@Override
	public void render(Graphics g) {
		g.setColor(new Color(0, 255, 0));
		g.fillRect((int)(x-handler.getCamera().getXoff()), (int)(y-handler.getCamera().getYoff()), width, height);
	}

	@Override
	public void onCollision() {
		if (inTimer) {
			startTimer(2);
		}else {
			System.out.println("hit");
			handler.getPlayer().setHealth(handler.getWorld().getPlayer().getHealth()-1);
			startTimer(2);
		}
	}
	
	private boolean startTimer(int s) {
		if (!inTimer) {
			startTime = System.currentTimeMillis();
			inTimer = true;
		}
		if (ticks < s*1000) {
		    ticks = (new Date()).getTime() - startTime;
		    return false;
		}
		inTimer = false;
		ticks = 0;
		return true;
	}
}

