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
		//create save helpers
		ArrayList<SaveHelper>saveHelpers = new ArrayList<SaveHelper>();
		for(int i = 0;i < 10;i++) {
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
				}
			}
			System.out.println("["+thread.getName()+"]\t" + (queue.size()>currentIndex && running));
		}
		//stopping all helper threads
		System.out.println("["+thread.getName()+"]\tHelper count: "+saveHelpers.size());
		for(SaveHelper helper:saveHelpers) {
			while(!helper.isDone()) {}
			System.out.println("["+thread.getName()+"]\tHelper ID: " + saveHelpers.indexOf(helper));
			helper.stop();
		}
		saveHelpers = null;
		stop();
	}


}
