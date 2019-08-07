package dev.world.save;

import java.util.ArrayList;

import dev.world.Sector;

public class SaveManager implements Runnable {


	private Thread thread;
	private boolean running;

	ArrayList<Sector>queue;

	public SaveManager(ArrayList<Sector>queue){
		this.queue = queue;
	}

	public synchronized void start() {
		if (running) {
			return;
		}
		running = true;
		thread = new Thread(this);
		thread.setName("SaveManager");
		thread.start();
	}

	public synchronized void stop() {
		if (!running) {
			return;
		}
		running = false;
		System.out.println("["+thread.getName()+"]\tTerminated");
	}

	@Override
	public void run() {
		
		stop();
	}


}
