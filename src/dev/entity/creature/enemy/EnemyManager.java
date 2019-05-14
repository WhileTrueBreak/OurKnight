package dev.entity.creature.enemy;

import java.awt.Graphics;
import java.util.ArrayList;

import dev.Handler;
import dev.entity.staticEntity.StaticEntity;

public class EnemyManager {
	
	ArrayList<Enemy>enemies;
	Handler handler;
	
	public EnemyManager(Handler handler) {
		enemies = new ArrayList<Enemy>();
		this.handler = handler;
	}
	
	public void update() {
		for(Enemy e:enemies) {
			e.update();
		}
	}
	
	public void render(Graphics g) {
		for(Enemy e:enemies) {
			e.render(g);
		}
	}
	
	public ArrayList<Enemy> getStaticEntities(){
		return enemies;
	}
	
	public void addEnemy(Enemy e) {
		enemies.add(e);
	}
	
	public void removeEnemy(Enemy e) {
		enemies.remove(e);
	}
	
	public ArrayList<Enemy> getEnemies(){
		return enemies;
	}
	
}
