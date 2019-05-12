package dev.entity.creature;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import dev.Handler;

public class Player extends Creature{

	public Player(Handler handler, int x, int y) {
		super(handler, x, y);
		health = 10;
		speed = 4;//pixels per frame
		handler.getCamera().focusOnEntity(this, 0);
	}

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
		float tempx = x, tempy = y;
		if (mag != 0 && !checkCollide()) {
			//TODO fix collison so the player can move right next to the wall
			x += dx*speed/mag;
			if (checkCollide()) {
				x = tempx;
				while(!checkCollide()) {
					x += 0.01f*Math.signum(dx*speed/mag);
				}
			}
			y += dy*speed/mag;
			if (checkCollide()) {
				y = tempy;
				while(!checkCollide()) {
					y += 0.01f*Math.signum(dy*speed/mag);
				}
			}
		}
	}

	@Override
	public void update() {
		move();
		handler.getCamera().focusOnEntity(this, 10);
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

	public int getHealth() {
		// TODO Auto-generated method stub
		return health;
	}

	public void setHealth(int a) {
		health = a;
	}

}
