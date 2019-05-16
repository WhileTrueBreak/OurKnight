package dev;

import dev.utils.ThreadPool;
import dev.utils.audio.MusicPlayer;

public class Launcher {
	
	public static void main(String[] args) {
		System.out.println("Running");
		ThreadPool pool = new ThreadPool(2);
		Main game = new Main("Our Craft | A communist alternative to Soul Craft", 1024, 1024);
		MusicPlayer mplayer = new MusicPlayer("reallygoodsong");
		pool.runTask(mplayer);
		pool.runTask(game);
		pool.join();
	}
	
}
