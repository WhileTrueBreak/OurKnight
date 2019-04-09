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
		Entity[] all = new Entity[entities.size()];
		int n_ = 0;
		for (Entity entity:entities) {
			all[n_] = entity;
			n_++;
		}
		int n = all.length;
		int f = 0;
		boolean isSorted;
		
		for (int i = 0; i < n - 1; i++) {
			isSorted = true;
			for (int j = 0; j < n - i - 1; j++) {
				if (all[j].getY() > all[j + 1].getY()) {
					Entity temp = all[j];
					all[j] = all[j + 1];
					all[j + 1] = temp;
					isSorted = false;
				}
			}
			if(isSorted) {
				break;
			}
		}
		for (int i=0;i<all.length;i++) {
			all[i].render(g);
		}
	}
}
