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
		// TODO Auto-generated method stub
		world.update();
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		world.render(g);
		
	}

}
