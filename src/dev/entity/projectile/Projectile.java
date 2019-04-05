package dev.entity.projectile;

import dev.Handler;
import dev.entity.Entity;

public abstract class Projectile extends Entity{

	public Projectile(Handler handler, int x, int y) {
		super(handler, x, y);
	}
	
}
