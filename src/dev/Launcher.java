package dev;

import dev.utils.ThreadPool;
import dev.utils.audio.MusicPlayer;

public class Launcher {
	
	public static void main(String[] args) {
		System.out.println("Running");
		ThreadPool pool = new ThreadPool(2);
		pool.runTask(new Main("Our Craft | A communist alternative to Soul Craft", 1024, 1024));
		pool.runTask(new MusicPlayer("reallygoodsong"));
		pool.join();
	}
	
}
