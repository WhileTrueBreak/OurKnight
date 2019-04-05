	package dev.entity.creature;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import dev.Handler;

public class Player extends Creature{

	public Player(Handler handler, int x, int y) {
		super(handler, x, y);
		health = 10;
		speed = 10;//pixels per frame
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
		
		if (mag != 0) {
			x += dx*speed/mag;
			y += dy*speed/mag;
		}
	}
	
	@Override
	public void update() {
		move();
	}

	@Override
	public void render(Graphics g) {
		g.setColor(new Color(255, 0, 0));
		g.fillRect(x, y, 30, 30);
	}

}
