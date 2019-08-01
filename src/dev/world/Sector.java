package dev.world;

import java.awt.Color;
import java.awt.Graphics;

import dev.Handler;
import dev.entity.staticEntity.StaticEntityManager;
import dev.tiles.Tile;

public class Sector {

	public static final int SECTOR_WIDTH = 16, SECTOR_HEIGHT = 16;
	public static final int SECTOR_PIXEL_WIDTH = 16*Tile.TILE_WIDTH, SECTOR_PIXEL_HEIGHT = 16*Tile.TILE_HEIGHT;

	//tiles
	private int[][] tileMap = new int[SECTOR_WIDTH][SECTOR_HEIGHT];
	
	//managers
	StaticEntityManager staticEntityManager;

	private Handler handler;
	private int sectorX, sectorY;
	
	public Sector(Handler handler, int x, int y) {
		this.handler = handler;
		sectorX = x;
		sectorY = y;
		staticEntityManager = new StaticEntityManager(handler);

	}

	public void loadSectorTiles(int[][] tileMap) {
		this.tileMap = tileMap;
	}

	public void update() {
		staticEntityManager.update();
	}

	public void render(Graphics g) {
		for(int i = 0;i < tileMap.length;i++) {
			for(int j = 0;j < tileMap[i].length;j++) {
				Tile.tiles[tileMap[i][j]].render(g, (int)((i+sectorX*SECTOR_WIDTH)*Tile.TILE_WIDTH - handler.getCamera().getXoff()), 
						(int)((j+sectorY*SECTOR_HEIGHT)*Tile.TILE_HEIGHT - handler.getCamera().getYoff()));
			}
		}
		if((int)(handler.getPlayer().getX()/SECTOR_PIXEL_WIDTH) == sectorX && 
				(int)(handler.getPlayer().getY()/SECTOR_PIXEL_HEIGHT) == sectorY) {
			g.setColor(new Color(0, 0, 255));
			g.drawRect((int)(sectorX*SECTOR_PIXEL_WIDTH-handler.getCamera().getXoff()),
					(int)(sectorY*SECTOR_PIXEL_HEIGHT-handler.getCamera().getYoff()),
					SECTOR_PIXEL_WIDTH-1, SECTOR_PIXEL_HEIGHT-1);
		}
	}

	public int getSectorX() {
		return sectorX;
	}

	public int getSectorY() {
		return sectorY;
	}

	public StaticEntityManager getStaticEntityManager() {
		return staticEntityManager;
	}

	public int[][] getTileMap() {
		return tileMap;
	}

}
