package dev.ui;

import java.awt.Color;
import java.awt.Graphics;

import dev.Handler;

public class Health extends UI {
	
	private int fullhealth;

	public Health(Handler handler, int x, int y) {
		super(handler, x, y);
		fullhealth = handler.getPlayer().getHealth();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(new Color(255,50,50));
		g.fillRect(x, y, 400/fullhealth*handler.getWorld().getPlayer().getHealth(), 20);
		g.setColor(new Color(0));
		g.drawRect(x, y, 400, 20);
	}
}
