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

	@Override
	public void run() {
		//create save helpers
		ArrayList<SaveHelper>saveHelpers = new ArrayList<SaveHelper>();
		for(int i = 0;i < 4;i++) {
			saveHelpers.add(new SaveHelper("SaveHelper"+i));
		}
		for(SaveHelper helper:saveHelpers) helper.start();
		//saving
		int currentIndex = 0;
		while(queue.size()>currentIndex && running) {
			for(SaveHelper helper:saveHelpers) {
				if(helper.isDone() && queue.size()>currentIndex) {
					helper.setSector(queue.get(currentIndex));
					currentIndex++;
				}else if(helper.isDone()) {
					break;
				}
			}
		}
		//stopping all helper threads
		for(SaveHelper helper:saveHelpers) {
			System.out.println("[SaveManager]\t"+helper);
			while(!helper.isDone()) {}
			helper.stop();
		}
		saveHelpers = null;
		stop();
	}


}
