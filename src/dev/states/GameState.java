package dev.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import dev.Handler;
import dev.world.World;

public class GameState extends State{
	
	private World world;
	private Handler handler;
	
	private boolean loadSave = false;
	
	public GameState(Handler handler) {
		this.handler = handler;
	}
	
	public void init() {
		world = new World(handler, loadSave);
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

	public void setLoadSave(boolean loadSave) {
		this.loadSave = loadSave;
	}
	
}
