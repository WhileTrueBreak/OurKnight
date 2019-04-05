package dev;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import dev.display.Display;
import dev.input.KeyManager;
import dev.states.GameState;
import dev.states.LobbyState;
import dev.states.MenuState;
import dev.states.State;

public class Main implements Runnable{
	
	private Display display;
	private boolean running = false;
	private Thread thread;
	
	private BufferStrategy bs;
	private Graphics g;
	
	private int width, height;
	private String title;
	
	//input
	private KeyManager keyManager;
	
	//handler
	private Handler handler;
	
	//states
	private State gameState;
	private State lobbyState;
	private State menuState;
	
	public Main(String title, int width, int height) {
		this.width = width;
		this.height = height;
		this.title = title;
	}
	
	private void init() {
		display = new Display(title, width, height);
		handler = new Handler(this);
		
		keyManager = new KeyManager();
		display.getJFrame().addKeyListener(keyManager);
		display.getCanvas().addKeyListener(keyManager);
		
		gameState = new GameState(handler);
		lobbyState = new LobbyState(handler);
		menuState = new MenuState(handler);
		State.setCurrentState(lobbyState);
	}

	private void update() {
		if(State.getCurrentState() != null)
			State.getCurrentState().update();
	}
	
	private void render() {
		bs = display.getCanvas().getBufferStrategy();
		if (bs == null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		g.clearRect(0, 0, width, height);
		// Draw Crap
		if(State.getCurrentState() != null)
			State.getCurrentState().render(g);
		
		// End Crap
		bs.show();
		g.dispose();
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public Display getDisplay() {
		return display;
	}

	public void setDisplay(Display display) {
		this.display = display;
	}
	
	public KeyManager getKeyManager() {
		return keyManager;
	}

	public void setKeyManager(KeyManager keyManager) {
		this.keyManager = keyManager;
	}
	//////////////////////////////////////////////////////

	//////////////////////////////////////////////////////

	public void run() {
		init();
		
		int fps = 60;
		double timeperTick = 1000000000/fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;
		
		while (running) {
			now = System.nanoTime();
			delta += (now - lastTime)/timeperTick;
			timer += now - lastTime;
			lastTime = now;
			if(delta >= 1) {
				update();
				render();
				ticks++;
				delta--;
			}
			if(timer >= 1000000000) {
				System.out.println(ticks);
				ticks = 0;
				timer = 0;
			}
		}
		
		stop();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

