package dev.ui.button;

import java.awt.Rectangle;

import dev.Handler;
import dev.ui.ClickListener;
import dev.ui.UIObject;

public abstract class UIButton extends UIObject{
	
	protected ClickListener clicker;

	public UIButton(Handler handler, int x, int y, int width, int height, String text, Rectangle bounds, ClickListener clicker) {
		super(handler, x, y, width, height, text, bounds);
		this.clicker = clicker;
	}
	
	public void onClick() {
		clicker.onClick();
	}

}
