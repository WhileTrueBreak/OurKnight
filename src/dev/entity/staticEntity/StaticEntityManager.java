package dev.entity.staticEntity;

import java.awt.Graphics;
import java.util.ArrayList;

import dev.Handler;

public class StaticEntityManager {
	
	ArrayList<StaticEntity>staticEntities;
	Handler handler;
	
	public StaticEntityManager(Handler handler) {
		staticEntities = new ArrayList<StaticEntity>();
		this.handler = handler;
	}
	
	public void update() {
		for(StaticEntity e:staticEntities) {
			e.update();
		}
	}
	
	public void render(Graphics g) {
		for(StaticEntity e:staticEntities) {
			e.render(g);
		}
	}
	
	public void addStaticEntity(StaticEntity e) {
		staticEntities.add(e);
		//handler.getWorld().getEntityManager().addEntity(e);
	}
	
	public void removeStaticEntity(StaticEntity e) {
		staticEntities.remove(e);
		//handler.getWorld().getEntityManager().removeEntity(e);
	}
	
	public ArrayList<StaticEntity> getStaticEntities(){
		return staticEntities;
	}
	
}
