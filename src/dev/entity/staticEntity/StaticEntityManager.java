package dev.entity.staticEntity;

import java.awt.Graphics;
import java.util.ArrayList;

public class StaticEntityManager {
	
	ArrayList<StaticEntity>staticEntities;
	
	public StaticEntityManager() {
		staticEntities = new ArrayList<StaticEntity>();
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
	}
	
	public void removeStaticEntity(StaticEntity e) {
		staticEntities.remove(e);
	}
	
	public ArrayList<StaticEntity> getStaticEntities(){
		return staticEntities;
	}
	
}
