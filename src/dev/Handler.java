package dev;

import java.awt.Rectangle;

import dev.display.Camera;
import dev.entity.creature.Player;
import dev.input.KeyManager;
import dev.input.MouseManager;
import dev.world.World;

public class Handler {

	private Main main;
	private World world;
	
	public Handler(Main main) {
		this.main = main;
	}
	
	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public Main getMain() {
		return main;
	}
	
	public Camera getCamera() {
		return main.getCamera();
	}

	public int getWidth() {
		return main.getWidth();
	}

	public int getHeight() {
		return main.getHeight();
	}
	
	public Player getPlayer() {
		return world.getPlayer();
	}
	
	public KeyManager getKeyManager() {
		return main.getKeyManager();
	}
	
	public MouseManager getMouseManager() {
		return main.getMouseManager();
	}
	
	public Rectangle getScreenBound() {
		return new Rectangle((int)getCamera().getXoff(), (int)getCamera().getYoff(), getWidth(), getHeight());
	}
	
}
