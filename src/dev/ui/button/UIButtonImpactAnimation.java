package dev.ui.button;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

import dev.Handler;
import dev.ui.ClickListener;

public class UIButtonImpactAnimation extends UIButton{
	
	private float strikePercent = 0;
	
	public UIButtonImpactAnimation(Handler handler, int x, int y, int width, int height, String text, Rectangle bounds, ClickListener clicker) {
		super(handler, x, y, width, height, text, bounds, clicker);
	}

	@Override
	public void update() {
		if(hovering) {
			if(strikePercent < 1) {
				strikePercent += 0.1*handler.getSpeedMult();
			}
		}else {
			if(strikePercent > 0) {
				strikePercent -= 0.1*handler.getSpeedMult();
			}
		}
	}

	@Override
	public void render(Graphics g) {
		Font font = new Font("Arial", Font.PLAIN, (int)(height/1.5f));
		g.setColor(new Color(255, 255, 255));
		FontMetrics metrics = g.getFontMetrics(font);
		int tx = this.x+(this.width - metrics.stringWidth(text)) / 2;
	    int ty = this.y+((this.height - metrics.getHeight()) / 2) + metrics.getAscent();
		g.setFont(font);
		g.drawString(text, tx, ty);
		int c = (int) (strikePercent*255);
		if(c < 0) c = 0;
		if(c > 255) c = 255;
		System.out.println(strikePercent);
		if(strikePercent > 0) {
			g.setColor(new Color(c, c, c));
			g.drawLine((int)(x+width/2-strikePercent*width/2), y, (int)(x+width/2+strikePercent*width/2), y);
			g.drawLine((int)(x+width/2-strikePercent*width/2), y+height, (int)(x+width/2+strikePercent*width/2), y+height);
		}
	}

}
