//TEMPERARY CODE TO TEST ENEMY

package dev.entity.creature.enemy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import dev.Handler;
import dev.entity.Entity;

public class BasicEnemy extends Enemy{
	
	public int width = 64, height = 64;

	public BasicEnemy(Handler handler, int x, int y) {
		super(handler, x, y);
		health = 10;
		speed = 3;
	}
	
//	private Rectangle hitbox = new Rectangle(x, y, width, height);
	
	private void move() {
		int destX = handler.getWorld().getPlayer().getX();
		int destY = handler.getWorld().getPlayer().getY();
		float dx = destX - x, dy = destY - y;
		float mag = (float) Math.sqrt(dx*dx+dy*dy);
		if(mag > speed) {
			dx = dx*speed/mag;
			dy = dy*speed/mag;
		}
		x += dx;
		y += dy;
	}
	
//	public Rectangle getHitbox() {
//		return hitbox;
//	}
	
	@Override
	public void update() {
		move();
		setHitboxAttrb(x,y,width,height);
	}

	@Override
	public void render(Graphics g) {
		g.setColor(new Color(0, 255, 0));
		g.fillRect((int)(x-handler.getCamera().getXoff()), (int)(y-handler.getCamera().getYoff()), width, height);
		g.setColor(new Color(0, 0, 255));
		g.drawRect((int)(hitbox.x-handler.getCamera().getXoff()), (int)(hitbox.y-handler.getCamera().getYoff()), hitbox.width, hitbox.height);

	}

	@Override
	public void onCollision() {
		// TODO Auto-generated method stub
		System.out.println("enemy");
	}

}
