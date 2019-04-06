package dev;

import dev.display.Camera;
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
}
