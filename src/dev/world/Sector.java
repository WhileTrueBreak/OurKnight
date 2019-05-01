package dev.world;

import java.awt.Graphics;
import java.util.ArrayList;

import dev.Handler;
import dev.entity.staticEntity.StaticEntityManager;
import dev.tiles.Floor;
import dev.tiles.Tile;

public class Sector {

	public static final int SECTOR_WIDTH = 16, SECTOR_HEIGHT = 16;
	
	//managers
	StaticEntityManager staticEntityManager;
	
	//tiles
	ArrayList<Tile>tiles = new ArrayList<Tile>();
	
	private Handler handler;
	private int sectorX, sectorY;
	
	public Sector(Handler handler, int x, int y) {
		this.handler = handler;
		sectorX = x;
		sectorY = y;
		staticEntityManager = new StaticEntityManager(handler);
		
	}
	
	public void loadSectorTiles(int[][] tileMap, int x, int y) {
		for(int i = x;i < x+SECTOR_WIDTH;i++) {
			for(int j = y;j < y+SECTOR_HEIGHT;j++) {
				if(tileMap[i][j] == 1) {
					Tile tile = new Floor(handler, i*Tile.TILE_WIDTH, j*Tile.TILE_HEIGHT, 0);
					tiles.add(tile);
				}
			}
		}
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
