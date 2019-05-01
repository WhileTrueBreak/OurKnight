package dev.world;

import java.awt.Graphics;
import java.util.ArrayList;

import dev.Handler;

public class SectorManager {
	ArrayList<Sector> sectors = new ArrayList<Sector>();
	public SectorManager(Handler handler) {
		for(int i = 0;i < World.WORLD_SECTOR_HEIGHT*World.WORLD_SECTOR_WIDTH;i++) {
			sectors.add(null);
		}
	}
	
	public void update() {
		for(Sector s:sectors) {
			if(s != null)
				s.update();
		}
	}
	
	public void render(Graphics g) {
		for(Sector s:sectors) {
			if(s != null)
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
