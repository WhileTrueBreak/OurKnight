//TEMPERARY CODE TO TEST ENEMY

package dev.entity.creature.enemy;

import java.awt.Color;
import java.awt.Graphics;

import dev.Handler;

public class BasicEnemy extends Enemy{

	public BasicEnemy(Handler handler, int x, int y) {
		super(handler, x, y);
		health = 10;
		speed = 3;
	}
	
	private void move() {
		int destX = handler.getWorld().getPlayerX();
		int destY = handler.getWorld().getPlayerY();
		float dx = destX - x, dy = destY - y;
		float mag = (float) Math.sqrt(dx*dx+dy*dy);
		if(mag > speed) {
			dx = dx*speed/mag;
			dy = dy*speed/mag;
		}
		x += dx;
		y += dy;
	}
	
	@Override
	public void update() {
		move();
	}

	@Override
	public void render(Graphics g) {
		g.setColor(new Color(0, 255, 0));
		g.fillRect((int)(x-handler.getCamera().getXoff()), (int)(y-handler.getCamera().getYoff()), 32, 32);
	}

}
