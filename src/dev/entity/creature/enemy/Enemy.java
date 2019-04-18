package dev.entity.creature.enemy;

import dev.Handler;
import dev.entity.creature.Creature;

public abstract class Enemy extends Creature{
	
	protected long ticks = 0, startTime;
	
	protected boolean inTimer = false;
	
	protected float speed;
	
	public Enemy(Handler handler, int x, int y) {
		super(handler, x, y);
	}

}
