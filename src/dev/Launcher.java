package dev;

import dev.audio.MusicPlayer;
import dev.utils.ThreadPool;

public class Launcher {
	
	public static void main(String[] args) {
		System.out.println("Running");
		new Main("Our Craft | A communist alternative to Soul Craft", 1024, 1024).start();
	}
	
}
