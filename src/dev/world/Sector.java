package dev.world;

import java.awt.Graphics;
import java.util.ArrayList;

import dev.Handler;
import dev.entity.staticEntity.StaticEntityManager;
import dev.tiles.Tile;

public class Sector {

	public static final int width = 16, height = 16;
	
	//managers
	StaticEntityManager staticEntityManager;
	
	//tiles
	ArrayList<Tile>tiles = new ArrayList<Tile>();
	
	private int sectorX, sectorY;
	
	public Sector(Handler handler, int x, int y) {
		sectorX = x;
		sectorY = y;
		staticEntityManager = new StaticEntityManager(handler);
		
	}
	
	public void update() {
		for(Tile t:tiles) {
			t.update();
		}
		staticEntityManager.update();
	}
	
	public void render(Graphics g) {
		for(Tile t:tiles) {
			t.render(g);
		}
		staticEntityManager.render(g);
	}

	public int getSectorX() {
		return sectorX;
	}

	public int getSectorY() {
		return sectorY;
	}
	
}
