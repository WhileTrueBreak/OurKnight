package dev.world;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;

import dev.Handler;
import dev.entity.Entity;
import dev.entity.creature.Player;
import dev.entity.creature.enemy.EnemyManager;
import dev.entity.staticEntity.StaticEntity;
import dev.entity.staticEntity.Wall;
import dev.tiles.Tile;
import dev.ui.UIManager;
import dev.utils.noise.OpenSimplexNoise;
import dev.world.pathfinding.quadtree.Quadtree;

public class World {

	public static int WORLD_SECTOR_WIDTH = 2, WORLD_SECTOR_HEIGHT = 2;
	public static boolean RENDER_DEBUG = true;
	
	//managers
	EnemyManager enemyManager;
	SectorManager sectorManager;
	UIManager ui;

	//player
	private Player player;

	//handler
	private Handler handler;
	
	//pathfinding and navmesh
	private Quadtree quadtree;

	//Comparators
	private Comparator<Entity> renderOrder = new Comparator<Entity>() {
		@Override
		public int compare(Entity e1, Entity e2) {
			return e1.getY()+e1.getHitbox().y < e2.getY()+e2.getHitbox().y ? -1:e1.getY()+e1.getHitbox().y == e2.getY()+e2.getHitbox().y ? 0:1;
		}	
	};

	public World(Handler handler) {
		this.handler = handler;
		handler.setWorld(this);

		sectorManager = new SectorManager(handler);

		enemyManager = new EnemyManager(handler);
		ui = new UIManager(handler);

		player = new Player(handler, 400, 400);
		
		quadtree = new Quadtree(handler, handler.getWidth(), handler.getHeight());
		
		loadWorld();
		updateNavmesh();
		
		quadtree.update(getPathfindingEntities());
	}
	
	//main game loop stuff

	public void update() {
		sectorManager.update();
		player.update();
		ui.update();
	}

	public void render(Graphics g) {
		sectorManager.render(g);
		renderEntities(g);
		ui.render(g);
		if(RENDER_DEBUG) {
			quadtree.dfs(g);
			quadtree.renderNavMesh(g);
		}
	}
	
	//rendering

	private void renderEntities(Graphics g) {
		ArrayList<Entity> entities = new ArrayList<Entity>();
		//adding all entities
		entities.addAll(sectorManager.getRenderEntities());
		entities.add(player);
		//sort
		entities.sort(renderOrder);
		//render
		for(Entity e:entities)
			e.render(g);
	}
	
	//pathfinding
	
	public void updateNavmesh() {
		quadtree.update(getPathfindingEntities());
		quadtree.clearNavMesh();
		quadtree.updateNavMesh();
	}
	
	private ArrayList<Entity> getPathfindingEntities() {
		ArrayList<Entity> entities = new ArrayList<Entity>();
		for(int i = (int)(handler.getCamera().getXoff())/(Sector.SECTOR_PIXEL_WIDTH);
				i < Math.ceil((handler.getCamera().getXoff()+handler.getWidth())/(Sector.SECTOR_PIXEL_WIDTH));i++) {
			for(int j = (int)(handler.getCamera().getYoff())/(Sector.SECTOR_PIXEL_HEIGHT);
					j < Math.ceil((handler.getCamera().getYoff()+handler.getHeight())/(Sector.SECTOR_PIXEL_HEIGHT));j++) {
				if(sectorManager.getSector(i, j) != null) entities.addAll(sectorManager.getSector(i, j).getStaticEntityManager().getStaticEntities());
			}
		}
		return entities;
	}
	
	//world loading
	
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
		//load border walls
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
		long seed = (long)(Math.signum(Math.random()-0.5f)*Math.random()*9223372036854775807l);
		System.out.println("Seed: " + seed);
		OpenSimplexNoise noise = new OpenSimplexNoise(seed); 
		for(int x = 0;x < WORLD_SECTOR_WIDTH*Sector.SECTOR_WIDTH;x++) {
			for(int y = 0;y < WORLD_SECTOR_HEIGHT*Sector.SECTOR_HEIGHT;y++) {
				if(noise.eval(x*0.1f, y*0.1f) < -0.5f) {
					staticEntities.add(new Wall(handler, x*Tile.TILE_WIDTH, y*Tile.TILE_HEIGHT, Tile.TILE_WIDTH, Tile.TILE_HEIGHT, 0));
				}
			}
		}
		save(staticEntities);
		staticEntities = new ArrayList<StaticEntity>();
		System.out.println("Time taken: " + (System.currentTimeMillis()-startTime) + "ms");
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

	//getters and setters

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
