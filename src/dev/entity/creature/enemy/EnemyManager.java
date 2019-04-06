package dev.entity.creature.enemy;

import java.awt.Graphics;
import java.util.ArrayList;

public class EnemyManager {
	
	ArrayList<Enemy>enemies;
	
	public EnemyManager() {
		enemies = new ArrayList<Enemy>();
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
