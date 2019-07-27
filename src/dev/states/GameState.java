package dev.states;

import java.awt.Color;
import java.awt.Font;
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
		world.update();
	}

	@Override
	public void render(Graphics g) {
		world.render(g);
		//render fps info
		g.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		g.setColor(Color.red);
	    g.drawString(Integer.toString((int) Math.round(handler.getFps())) + "fps", handler.getWidth()-50, 20);
	}

}
