package dev.world;

import java.awt.Graphics;
import java.util.ArrayList;

import dev.Handler;

public class SectorManager {
	ArrayList<Sector> sectors = new ArrayList<Sector>();
	public SectorManager(Handler handler) {
		
	}
	
	public void update() {
		for(Sector s:sectors) {
			s.update();
		}
	}
	
	public void render(Graphics g) {
		for(Sector s:sectors) {
			s.render(g);
		}
	}
	
	public ArrayList<Sector> getSector() {
		return sectors;
	}
	
	public void addSector(Sector s) {
		sectors.add(s);
	}
}
