package dev.world;

import java.awt.Graphics;
import java.util.ArrayList;

import dev.Handler;

import dev.entity.creature.Player;
import dev.entity.creature.enemy.EnemyManager;
import dev.entity.staticEntity.StaticEntity;
import dev.entity.staticEntity.Wall;
import dev.tiles.Tile;
import dev.ui.UIManager;

public class World {

	public static int WORLD_SECTOR_WIDTH = 10, WORLD_SECTOR_HEIGHT = 10;

	//managers
	EnemyManager enemyManager;
	SectorManager sectorManager;
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
		ui = new UIManager(handler);

		player = new Player(handler, 400, 400);
		//ui.addUI(new Health(handler, 30, 30));

		loadWorld();
	}

	private void loadWorld() {
		long startTime = System.currentTimeMillis();
		//loading Tiles
		int[][] tileMap = new int[WORLD_SECTOR_WIDTH*Sector.SECTOR_WIDTH][WORLD_SECTOR_HEIGHT*Sector.SECTOR_HEIGHT];
		for(int x = 0;x < tileMap.length;x++) {
			for(int y = 0;y < tileMap[x].length;y++) {
				tileMap[x][y] = 1;
			}
		}
		//loading sectors
		for(int x = 0;x < WORLD_SECTOR_WIDTH;x++) {
			for(int y = 0;y < WORLD_SECTOR_HEIGHT;y++) {
				Sector sector = new Sector(handler, x, y);
				ArrayList<StaticEntity>e = new ArrayList<StaticEntity>();
				sector.loadSectorTiles(tileMap, e, x*Sector.SECTOR_WIDTH, y*Sector.SECTOR_HEIGHT);
				sectorManager.addSector(sector);
			}
		}
		ArrayList<StaticEntity>staticEntities = new ArrayList<StaticEntity>();
		//load borber walls
		for(int x = 0;x < WORLD_SECTOR_WIDTH*Sector.SECTOR_WIDTH;x++) {
			for(int y = 0;y < WORLD_SECTOR_HEIGHT*Sector.SECTOR_HEIGHT;y++) {
				if(x == 0 || y== 0 || x == WORLD_SECTOR_WIDTH*Sector.SECTOR_WIDTH-1 || y == WORLD_SECTOR_HEIGHT*Sector.SECTOR_HEIGHT-1) {
					staticEntities.add(new Wall(handler, x*Tile.TILE_WIDTH, y*Tile.TILE_HEIGHT, Tile.TILE_WIDTH, Tile.TILE_HEIGHT, 0));
				}
			}
		}
		save(staticEntities);
		staticEntities = new ArrayList<StaticEntity>();
		//load walls
		for(int x = 0;x < WORLD_SECTOR_WIDTH*Sector.SECTOR_WIDTH;x++) {
			for(int y = 0;y < WORLD_SECTOR_HEIGHT*Sector.SECTOR_HEIGHT;y++) {
				if(Math.random() < 0.1f) {
					staticEntities.add(new Wall(handler, x*Tile.TILE_WIDTH, y*Tile.TILE_HEIGHT, Tile.TILE_WIDTH, Tile.TILE_HEIGHT, 0));
				}
			}
		}
		save(staticEntities);
		staticEntities = new ArrayList<StaticEntity>();
		System.out.println("Time taken: " + (System.currentTimeMillis()-startTime));
	}
	
	//TODO refactor this method
	private void save (ArrayList<StaticEntity> entities) {
		//putting  static entities into sectors
		for(StaticEntity e:entities) {
			int sectorX = (int)(e.getX()/Sector.SECTOR_PIXEL_WIDTH);
			int sectorY = (int)(e.getY()/Sector.SECTOR_PIXEL_HEIGHT);

			sectorManager.getSector(sectorX, sectorY).getStaticEntityManager().addStaticEntity(e);
		}
	}
	
	public void update() {
		
		sectorManager.update();
		player.update();
		ui.update();
		
		//		staticEntityManager.update();
		//		player.update();
		//		enemyManager.update();
	}

	public void render(Graphics g) {
		long st = System.currentTimeMillis();
		sectorManager.render(g);
		player.render(g);
		ui.render(g);
		System.out.println("time: " + (System.currentTimeMillis()-st));
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

	public SectorManager getSectorManager() {
		return sectorManager;
	}

	public UIManager getUIManager() {
		return ui;
	}

	public void setUIManager(UIManager ui) {
		this.ui = ui;
	}



}
