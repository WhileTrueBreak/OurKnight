package dev.states;

import java.awt.Graphics;

import dev.Handler;
import dev.world.World;

public class GameState extends State{
	
	private World world;
	private Handler handler;
	
	public GameState(Handler handler) {
		world = new World(handler);
		this.handler = handler;
	}

	@Override
	public void update() {
		long start = System.currentTimeMillis();
		world.update();
		System.out.println("Update Time: " + (System.currentTimeMillis()-start));
	}

	@Override
	public void render(Graphics g) {
		long start = System.currentTimeMillis();
		world.render(g);
		System.out.println("Render Time: " + (System.currentTimeMillis()-start));
	}

}
