package dev.world;

import java.awt.Graphics;
import java.util.ArrayList;

import dev.Handler;
import dev.tiles.Tile;

public class SectorManager {
	
	ArrayList<Sector> sectors = new ArrayList<Sector>();
	
	private Handler handler;
	
	public SectorManager(Handler handler) {
		this.handler = handler;
		for(int i = 0;i < World.WORLD_SECTOR_HEIGHT*World.WORLD_SECTOR_WIDTH;i++) {
			sectors.add(null);
		}
	}
	
	public void update() {
		
		for(Sector s:sectors) {
			if(s != null)
				if(s.getSectorX()*Sector.SECTOR_WIDTH*Tile.TILE_WIDTH-handler.getCamera().getXoff() < handler.getWidth()*2 &&
						s.getSectorY()*Sector.SECTOR_HEIGHT*Tile.TILE_HEIGHT-handler.getCamera().getYoff() < handler.getHeight()*2 &&
						s.getSectorX()*Sector.SECTOR_WIDTH*Tile.TILE_WIDTH-handler.getCamera().getXoff() + Sector.SECTOR_WIDTH*Tile.TILE_WIDTH > -handler.getWidth() &&
						s.getSectorY()*Sector.SECTOR_HEIGHT*Tile.TILE_HEIGHT-handler.getCamera().getYoff() + Sector.SECTOR_HEIGHT*Tile.TILE_HEIGHT > -handler.getHeight())
					s.update();
		}
		
	}
	
	public void render(Graphics g) {
		
		for(Sector s:sectors) {
			if(s != null)
				if(s.getSectorX()*Sector.SECTOR_WIDTH*Tile.TILE_WIDTH-handler.getCamera().getXoff() < handler.getWidth() &&
						s.getSectorY()*Sector.SECTOR_HEIGHT*Tile.TILE_HEIGHT-handler.getCamera().getYoff() < handler.getHeight() &&
						s.getSectorX()*Sector.SECTOR_WIDTH*Tile.TILE_WIDTH-handler.getCamera().getXoff() + Sector.SECTOR_WIDTH*Tile.TILE_WIDTH > 0 &&
						s.getSectorY()*Sector.SECTOR_HEIGHT*Tile.TILE_HEIGHT-handler.getCamera().getYoff() + Sector.SECTOR_HEIGHT*Tile.TILE_HEIGHT > 0)
					s.render(g);
		}
		
	}
	
	public Sector getSector(int x, int y) {
		int index = x+y*World.WORLD_SECTOR_WIDTH;
		return sectors.get(index);
	}
	
	public ArrayList<Sector> getSectors() {
		return sectors;
	}
	
	public void addSector(Sector s) {
		int index = s.getSectorX()+s.getSectorY()*World.WORLD_SECTOR_WIDTH;
		sectors.remove(index);
		sectors.add(index, s);
	}
}
