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
import dev.world.pathfinding.NavmeshUpdater;
import dev.world.pathfinding.quadtree.Quadtree;

public class World {

	public static int WORLD_SECTOR_WIDTH = 32, WORLD_SECTOR_HEIGHT = 32;
	public static boolean RENDER_DEBUG = false;
	
	//managers
	EnemyManager enemyManager;
	SectorManager sectorManager;
	UIManager ui;

	//task flags
	boolean navmeshUpdateRequired = true;
	
	//player
	private Player player;

	//handler
	private Handler handler;
	
	//pathfinding and navmesh
	private Quadtree quadtree;
	private NavmeshUpdater nu;

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

		player = new Player(handler, 500, 400);
		
		System.out.printf("[World]\t\tDimensions:[W:%d H:%d]\n",getWorldWidth(), getWorldHeight());
		quadtree = new Quadtree(handler, getWorldWidth(), getWorldHeight());
		
		loadWorld();
		
		nu = new NavmeshUpdater(getPathfindingEntities(handler.getWidth(), handler.getHeight()), (Quadtree) quadtree.clone());
		nu.start();
	}
	
	//main game loop stuff

	public void update() {
		sectorManager.update();
		player.update();
		ui.update();
		if(navmeshUpdateRequired) {
			updateNavmesh();
			navmeshUpdateRequired=false;
		}
		if(nu.isDone()) {
			quadtree = nu.getUpdated();
		}
	}

	public void render(Graphics g) {
		sectorManager.render(g);
		renderEntities(g);
		ui.render(g);
		if(RENDER_DEBUG) {
			//System.out.println("[Navmesh]\trendering");
			quadtree.dfs(g);
			quadtree.renderNavMesh(g);
		}
	}
	
	//rendering

	private void renderEntities(Graphics g) {
		ArrayList<Entity> entities = new ArrayList<Entity>();
		//adding all entities
		entities.addAll(sectorManager.getRenderEntities());
		//System.out.println("[Render]\tEntities to render: "+entities.size());
		entities.add(player);
		//sort
		entities.sort(renderOrder);
		//render
		for(Entity e:entities)
			e.render(g);
	}
	
	//pathfinding
	
	private void updateNavmesh() {
		nu = new NavmeshUpdater(getPathfindingEntities(handler.getWidth(), handler.getHeight()), (Quadtree) quadtree.clone());
		nu.start();
		quadtree = nu.getUpdated();
	}
	
	private ArrayList<Entity> getPathfindingEntities(float bufferX, float bufferY) {
		ArrayList<Entity> entities = new ArrayList<Entity>();
		for(int i = (int)(handler.getCamera().getXoff()-bufferX)/(Sector.SECTOR_PIXEL_WIDTH);
				i < Math.ceil((handler.getCamera().getXoff()+handler.getWidth()+bufferX)/(Sector.SECTOR_PIXEL_WIDTH));i++) {
			for(int j = (int)(handler.getCamera().getYoff()-bufferY)/(Sector.SECTOR_PIXEL_HEIGHT);
					j < Math.ceil((handler.getCamera().getYoff()+handler.getHeight()+bufferY)/(Sector.SECTOR_PIXEL_HEIGHT));j++) {
				if(sectorManager.getSector(i, j) != null) entities.addAll(sectorManager.getSector(i, j).getStaticEntityManager().getStaticEntities());
			}
		}
		return entities;
	}
	
	@SuppressWarnings("unused")
	private ArrayList<Entity> getAllStaticEntities() {
		ArrayList<Entity> entities = new ArrayList<Entity>();
		for(Sector s:sectorManager.getSectors()) {
			entities.addAll(s.staticEntityManager.getStaticEntities());
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
		//TODO change seed back to random
		long seed = -8519653203755203584l;//(long)(Math.signum(Math.random()-0.5f)*Math.random()*9223372036854775807l);
		System.out.println("[World]\t\tSeed: " + seed);
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
		System.out.println("[World]\t\tLoad world took: " + (System.currentTimeMillis()-startTime) + "ms");
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

	public Quadtree getQuadtree() {
		return quadtree;
	}
	
	public int getWorldWidth() {
		return WORLD_SECTOR_WIDTH*Sector.SECTOR_WIDTH*Tile.TILE_WIDTH;
	}
	
	public int getWorldHeight() {
		return WORLD_SECTOR_HEIGHT*Sector.SECTOR_HEIGHT*Tile.TILE_HEIGHT;
	}
	
	public void requireNavmeshUpdate() {
		navmeshUpdateRequired = true;
	}

}
