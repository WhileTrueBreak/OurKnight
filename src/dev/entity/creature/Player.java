package dev.entity.creature;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.Area;

import dev.Handler;
import dev.entity.Entity;

public class Player extends Creature{

	public Player(Handler handler, int x, int y) {
		super(handler, x, y);
		health = 10;
		speed = 4;//pixels per frame
	}
	
//	private Rectangle hitbox = new Rectangle(x,y,width,height);
	
	private void move() {
		boolean up = handler.getMain().getKeyManager().isKeyPressed(KeyEvent.VK_W);
		boolean left = handler.getMain().getKeyManager().isKeyPressed(KeyEvent.VK_A);
		boolean down = handler.getMain().getKeyManager().isKeyPressed(KeyEvent.VK_S);
		boolean right = handler.getMain().getKeyManager().isKeyPressed(KeyEvent.VK_D);
		
		float dx = 0, dy = 0;
		
		if (up) dy --;
		if (down) dy ++;
		if (left) dx --;
		if (right) dx ++;
		
		float mag = (float) Math.sqrt(dx*dx+dy*dy);
		int tempx = x, tempy = y;
		if (mag != 0) {
			x += dx*speed/mag;
			setHitboxAttrb(x,y,width,height);
			if (checkCollide()) {
				x = tempx;
			}
			y += dy*speed/mag;
			setHitboxAttrb(x,y,width,height);
			if (checkCollide()) {
				y = tempy;
			}
//			handler.getCamera().move(dx*speed/mag, dy*speed/mag);
		}else if(checkCollide()) {}
//		if (checkCollide()) {
//			x = tempx;
//			y = tempy;
//		}
	}
	
//	private boolean checkCollide() {
//		for (Entity e:handler.getWorld().getEntityManager().getEntities()) {
//			if (getHitbox().getBounds().intersects(e.getHitbox().getBounds()) && e != this) {
//				e.onCollision();
//				if (e.isSolid()) return true;
//			}else {
//				continue;
//			}
//		}
//		return false;
//	}
	
	@Override
	public void update() {
//		checkCollide();
		setHitboxAttrb(x,y,width,height);
		move();
		setHitboxAttrb(x,y,width,height);
		handler.getCamera().focusOnEntity(this);
	}

	@Override
	public void render(Graphics g) {
		g.setColor(new Color(255, 0, 0));
		g.fillRect((int)(x-handler.getCamera().getXoff()), (int)(y-handler.getCamera().getYoff()), width, height);
//		g.setColor(new Color(0, 0, 255));
//		g.drawRect((int)(hitbox.x-handler.getCamera().getXoff()), (int)(hitbox.y-handler.getCamera().getYoff()), hitbox.width, hitbox.height);
	}

	@Override
	public void onCollision() {
		// TODO Auto-generated method stub
		
	}

	public int getHealth() {
		// TODO Auto-generated method stub
		return health;
	}
	
	public void setHealth(int a) {
		health = a;
	}

}
