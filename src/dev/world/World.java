package dev.world;

import java.awt.Graphics;

import dev.Handler;
import dev.entity.EntityManager;
import dev.entity.creature.Player;
import dev.entity.creature.enemy.EnemyManager;
import dev.entity.staticEntity.StaticEntity;
import dev.ui.UIManager;

public class World {
	
	public static int WORLD_SECTOR_WIDTH = 4, WORLD_SECTOR_HEIGHT = 4;
	
	//managers
	EnemyManager enemyManager;
	SectorManager sectorManager;
	EntityManager entities;
	UIManager ui;
	
	//player
	private Player player;
	
	//handler
	private Handler handler;
	
	public World(Handler handler) {
		this.handler = handler;
		handler.setWorld(this);
		
		sectorManager = new SectorManager(handler);
		
		enemyManager = new EnemyManager(handler);
		entities = new EntityManager();
		ui = new UIManager(handler);
		
		player = new Player(handler, 400, 400);
		entities.addEntity(player);
		//ui.addUI(new Health(handler, 30, 30));
		
		loadWorld();
	}
	
	private void loadWorld() {
		StaticEntity[][] staticMap = new StaticEntity[WORLD_SECTOR_WIDTH*Sector.SECTOR_WIDTH][WORLD_SECTOR_HEIGHT*Sector.SECTOR_HEIGHT];
		int[][] tileMap = new int[WORLD_SECTOR_WIDTH*Sector.SECTOR_WIDTH][WORLD_SECTOR_HEIGHT*Sector.SECTOR_HEIGHT];
		for(int x = 0;x < tileMap.length;x++) {
			for(int y = 0;y < tileMap[x].length;y++) {
				tileMap[x][y] = 1;
			}
		}
		for(int x = 0;x < WORLD_SECTOR_WIDTH;x++) {
			for(int y = 0;y < WORLD_SECTOR_HEIGHT;y++) {
				Sector sector = new Sector(handler, x, y);
				sector.loadSectorTiles(tileMap, staticMap, x*Sector.SECTOR_WIDTH, y*Sector.SECTOR_HEIGHT);
				sectorManager.addSector(sector);
			}
		}
	}
	
	public void update() {
		sectorManager.update();
		entities.update();
		ui.update();
//		staticEntityManager.update();
//		player.update();
//		enemyManager.update();
	}
	
	public void render(Graphics g) {
		sectorManager.render(g);
		entities.render(g);
		ui.render(g);
//		staticEntityManager.render(g);
//		player.render(g);
//		enemyManager.render(g);
	}
	
	public Player getPlayer() {
		return player;
	}

	public EnemyManager getEnemyManager() {
		return enemyManager;
	}

	public EntityManager getEntityManager() {
		return entities;
	}
	

	public UIManager getUIManager() {
		return ui;
	}

	public void setUIManager(UIManager ui) {
		this.ui = ui;
	}
	
	
	
}
