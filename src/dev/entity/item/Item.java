package dev.entity.item;

import dev.Handler;
import dev.entity.Entity;

public abstract class Item extends Entity{

	public Item(Handler handler, int x, int y) {
		super(handler, x, y);
	}

}
