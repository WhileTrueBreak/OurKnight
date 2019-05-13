package dev.world;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import dev.Handler;
import dev.entity.staticEntity.StaticEntity;
import dev.entity.staticEntity.StaticEntityManager;
import dev.tiles.Floor;
import dev.tiles.Tile;

public class Sector {

	public static final int SECTOR_WIDTH = 16, SECTOR_HEIGHT = 16;
	public static final int SECTOR_PIXEL_WIDTH = 16*Tile.TILE_WIDTH, SECTOR_PIXEL_HEIGHT = 16*Tile.TILE_HEIGHT;

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

	public void loadSectorTiles(int[][] tileMap, ArrayList<StaticEntity> staticEntities, int x, int y) {
		for(StaticEntity e:staticEntities){
			staticEntityManager.addStaticEntity(e);
		}
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
		if((int)(handler.getPlayer().getX()/SECTOR_PIXEL_WIDTH) == sectorX && 
				(int)(handler.getPlayer().getY()/SECTOR_PIXEL_HEIGHT) == sectorY) {
			g.setColor(new Color(0, 0, 255));
			g.drawRect((int)(sectorX*SECTOR_PIXEL_WIDTH-handler.getCamera().getXoff()),
					(int)(sectorY*SECTOR_PIXEL_HEIGHT-handler.getCamera().getYoff()),
					SECTOR_PIXEL_WIDTH-1, SECTOR_PIXEL_HEIGHT-1);
		}
		staticEntityManager.render(g);
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

}
