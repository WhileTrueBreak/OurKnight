package dev.entity;

import java.awt.Graphics;
import java.util.ArrayList;

public class EntityManager {
	
	ArrayList<Entity> entities = new ArrayList<Entity>();
	
	public void addEntity(Entity entity) {
		entities.add(entity);
	}
	
	public void removeEntity(Entity entity) {
		entities.remove(entity);
	}
	
	public void update() {
		for (Entity entity:entities) {
			entity.update();
		}
	}
	
	public void render(Graphics g) {
		for (Entity entity:entities) {
			entity.render(g);
		}
	}

}
