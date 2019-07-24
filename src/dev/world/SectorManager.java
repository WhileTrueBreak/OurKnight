package dev.world;

import java.awt.Graphics;
import java.util.ArrayList;

import dev.Handler;
import dev.entity.Entity;

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
		//gets all sectors in screen
		for(int i = (int)(handler.getCamera().getXoff()-handler.getWidth())/(Sector.SECTOR_PIXEL_WIDTH);
				i < Math.ceil((handler.getCamera().getXoff()+handler.getWidth()*2)/(Sector.SECTOR_PIXEL_WIDTH));i++) {
			for(int j = (int)(handler.getCamera().getYoff()-handler.getHeight())/(Sector.SECTOR_PIXEL_HEIGHT);
					j < Math.ceil((handler.getCamera().getYoff()+handler.getHeight()*2)/(Sector.SECTOR_PIXEL_HEIGHT));j++) {
				Sector sector = getSector(i, j);
				//updates those sectors
				if(sector != null) sector.update();
			}
		}
	}
	
	public void render(Graphics g) {
		//long start = System.currentTimeMillis();
		//gets all sectors in screen
		for(int i = (int)(handler.getCamera().getXoff())/(Sector.SECTOR_PIXEL_WIDTH);
				i < Math.ceil((handler.getCamera().getXoff()+handler.getWidth())/(Sector.SECTOR_PIXEL_WIDTH));i++) {
			for(int j = (int)(handler.getCamera().getYoff())/(Sector.SECTOR_PIXEL_HEIGHT);
					j < Math.ceil((handler.getCamera().getYoff()+handler.getHeight())/(Sector.SECTOR_PIXEL_HEIGHT));j++) {
				Sector sector = getSector(i, j);
				//renders secters in screen
				if(sector != null) sector.render(g);
			}
		}
		//if(System.currentTimeMillis()-start > -1)System.out.println("Sector Render Takes: " + (System.currentTimeMillis()-start) + " milliseconds");
	}
	
	//prepares the static entities for the world to render
	public ArrayList<Entity> getRenderEntities(){
		ArrayList<Entity> entities = new ArrayList<Entity>();
		for(int i = (int)(handler.getCamera().getXoff())/(Sector.SECTOR_PIXEL_WIDTH);
				i < Math.ceil((handler.getCamera().getXoff()+handler.getWidth())/(Sector.SECTOR_PIXEL_WIDTH));i++) {
			for(int j = (int)(handler.getCamera().getYoff())/(Sector.SECTOR_PIXEL_HEIGHT);
					j < Math.ceil((handler.getCamera().getYoff()+handler.getHeight())/(Sector.SECTOR_PIXEL_HEIGHT));j++) {
				Sector sector = getSector(i, j);
				if(sector != null) entities.addAll(sector.getStaticEntityManager().getStaticEntities());
			}
		}
		return entities;
	}
	
	public Sector getSector(int x, int y) {
		int index = x+y*World.WORLD_SECTOR_WIDTH;
		if(index < 0 || index >= sectors.size()) return null;
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
