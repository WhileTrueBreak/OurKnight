package dev.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class UIButton extends UIObject{
	
	ClickListener clicker;

	public UIButton(int x, int y, int width, int height, Rectangle bounds, ClickListener clicker) {
		super(x, y, width, height, bounds);
		this.clicker = clicker;
	}

	@Override
	public void update() {
		
	}

	@Override
	public void render(Graphics g) {
		if(hovering) {
			g.setColor(new Color(255, 0, 0));
		}else {
			g.setColor(new Color(0, 255, 0));
		}
		g.fillRect(x, y, width, height);
	}

	@Override
	public void onClick() {
		clicker.onClick();
	}

}
