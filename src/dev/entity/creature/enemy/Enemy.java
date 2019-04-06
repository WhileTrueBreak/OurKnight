package dev.entity.creature.enemy;

import java.awt.Color;
import java.awt.Graphics;

import dev.Handler;
import dev.entity.creature.Creature;

public abstract class Enemy extends Creature{

	protected float speed;
	
	public Enemy(Handler handler, int x, int y) {
		super(handler, x, y);
	}

}
