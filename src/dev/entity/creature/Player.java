package dev.entity.creature;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

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
			y += dy*speed/mag;
			System.out.println(x);
			System.out.println("t" + tempx);
//			handler.getCamera().move(dx*speed/mag, dy*speed/mag);
		}
		if (checkCollide()) {
			x = tempx;
			y = tempy;
		}
	}
	
	private boolean checkCollide() {
		for (Entity e:handler.getWorld().getEntityManager().getEntities()) {
			if (getHitbox().intersects(e.getHitbox()) && e != this) {
				e.onCollision();
				System.out.println("intersect");
				return true;
			}else {
				continue;
			}
		}
		return false;
	}
	
	@Override
	public void update() {
//		checkCollide();
		move();
		setHitboxAttrb(x,y,width,height);
		handler.getCamera().focusOnEntity(this);
	}

	@Override
	public void render(Graphics g) {
		g.setColor(new Color(255, 0, 0));
		g.fillRect((int)(x-handler.getCamera().getXoff()), (int)(y-handler.getCamera().getYoff()), width, height);
	}

	@Override
	public void onCollision() {
		// TODO Auto-generated method stub
		
	}

}
