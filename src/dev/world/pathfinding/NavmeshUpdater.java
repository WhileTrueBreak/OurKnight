package dev.world.pathfinding;

import java.util.ArrayList;

import dev.entity.Entity;
import dev.world.pathfinding.quadtree.Quadtree;

public class NavmeshUpdater implements Runnable{

	private Thread thread;
	private boolean running;
	
	private boolean done = false;
	
	private Quadtree quadtree;
	private ArrayList<Entity>entities = new ArrayList<Entity>();

	public NavmeshUpdater(ArrayList<Entity>entities, Quadtree quadtree){
		this.entities = entities;
		this.quadtree = quadtree;
	}
	
	public synchronized void start() {
		if (running) {
			return;
		}
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop() {
		if (!running) {
			return;
		}
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public Quadtree getUpdated() {
		stop();
		return (Quadtree) quadtree.clone();
	}
	
	private void update() {
		long ts = System.currentTimeMillis();
		System.out.println("[NavUpdater]\tUpdating...");
		done = false;
		this.quadtree.update(entities);
		done = true;
		System.out.println("[NavUpdater]\tUpdate took: "+ (System.currentTimeMillis()-ts) + "ms");
	}

	@Override
	public void run() {
		update();
	}
	
	public boolean isDone() {
		return done;
	}

}
