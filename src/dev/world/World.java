package dev.world;

import java.awt.Graphics;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import dev.Handler;
import dev.entity.Entity;
import dev.entity.creature.Player;
import dev.entity.creature.enemy.BasicEnemy;
import dev.entity.creature.enemy.Enemy;
import dev.entity.creature.enemy.EnemyManager;
import dev.entity.staticEntity.StaticEntity;
import dev.entity.staticEntity.Wall;
import dev.tiles.Tile;
import dev.utils.noise.OpenSimplexNoise;
import dev.world.pathfinding.NavmeshUpdater;
import dev.world.pathfinding.quadtree.Quadtree;

public class World {

	public static int WORLD_SECTOR_WIDTH = 512, WORLD_SECTOR_HEIGHT = 512;
	public static boolean RENDER_DEBUG = false;

	//managers
	EnemyManager enemyManager;
	SectorManager sectorManager;

	//task flags
	boolean navmeshUpdateRequired = true;

	//camera
	private Entity focusEntity;

	//player
	private Player player;

	//handler
	private Handler handler;

	//pathfinding and navmesh
	private Quadtree quadtree;
	private NavmeshUpdater nmu;

	//Comparators
	private Comparator<Entity> renderOrder = new Comparator<Entity>() {
		@Override
		public int compare(Entity e1, Entity e2) {
			return e1.getY()+e1.getHitbox().y < e2.getY()+e2.getHitbox().y ? -1:e1.getY()+e1.getHitbox().y == e2.getY()+e2.getHitbox().y ? 0:1;
		}	
	};

	//general world info
	private long worldSeed;

	public World(Handler handler, boolean loadSave) {
		this.handler = handler;
		handler.setWorld(this);

		if(RENDER_DEBUG) System.out.println("[World]\t\tRENDER_DEBUG=True");

		sectorManager = new SectorManager(handler);

		enemyManager = new EnemyManager(handler);

		player = new Player(handler, 500, 400);
		focusEntity = player;

		enemyManager.addEnemy(new BasicEnemy(handler, 500, 500));

		System.out.printf("[World]\t\tDimensions:[W:%d H:%d]\n",getWorldWidth(), getWorldHeight());
		quadtree = new Quadtree(handler, getWorldWidth(), getWorldHeight());

		System.out.println("[World]\t\tLoad new world="+loadSave);

		if(loadSave) loadWorld();
		else createWorld();

		saveWorld();

		nmu = new NavmeshUpdater(getPathfindingEntities(handler.getWidth(), handler.getHeight()), (Quadtree) quadtree.clone());
		nmu.start();
	}

	//main game loop stuff

	public void update() {
		sectorManager.update();
		enemyManager.update();
		player.update();

		//update camera
		float mouseXoff = handler.getMouseManager().getMouseX()-handler.getWidth()/2;
		float mouseYoff = handler.getMouseManager().getMouseY()-handler.getHeight()/2;
		handler.getCamera().focusOnPoint((int)(focusEntity.getX()+mouseXoff), (int)(focusEntity.getY()+mouseYoff), 100);
		handler.getCamera().focusOnEntity(focusEntity, 10);

		if(navmeshUpdateRequired && handler.getMain().getTimer()>=1000000000) {
			updateNavmesh();
			navmeshUpdateRequired=false;
		}
		if(nmu.isDone()) {
			quadtree = nmu.getUpdated();
		}
	}

	public void render(Graphics g) {
		sectorManager.render(g);
		renderEntities(g);
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
		entities.addAll(enemyManager.getEntities());
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
		nmu = new NavmeshUpdater(getPathfindingEntities(handler.getWidth(), handler.getHeight()), (Quadtree) quadtree.clone());
		nmu.start();
		quadtree = nmu.getUpdated();
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

	//load world from save
	private void loadWorld() {

	}

	//save world
	@SuppressWarnings({ "unchecked", "unused" })
	private void saveWorld() {
		long timeStart = System.currentTimeMillis();
		System.out.println("[World]\t\tSaving world");
		//save basic info
		JSONObject basicInfo = new JSONObject();
		basicInfo.put("seed", worldSeed);
		basicInfo.put("worldWidth", World.WORLD_SECTOR_WIDTH);
		basicInfo.put("worldHeight", World.WORLD_SECTOR_HEIGHT);
		JSONObject info = new JSONObject();
		info.put("world", basicInfo);
		try (FileWriter file = new FileWriter("world/info.json")) {
			file.write(info.toJSONString());
			file.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//save world info
		JSONObject worldInfo = new JSONObject();
		JSONArray sectorList = new JSONArray();
		for(Sector sector:sectorManager.getSectors()) {
			JSONObject sectorInfo = new JSONObject();
			sectorList.add(sectorInfo);
		}
		worldInfo.put("Sectors", sectorList);
		try (FileWriter file = new FileWriter("world/world.json")) {
			file.write(worldInfo.toJSONString());
			file.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("[World]\t\tSaving world took: "+(System.currentTimeMillis()-timeStart)+"ms");
	}

	//world creation
	private void createWorld() {
		long startTime = System.currentTimeMillis();

		//TODO change seed back to random
		long seed = -8519653203755203584l;//(long)(Math.signum(Math.random()-0.5f)*Math.random()*9223372036854775807l);
		this.worldSeed = seed;
		System.out.println("[World]\t\tSeed: " + seed);
		OpenSimplexNoise noise = new OpenSimplexNoise(seed);
		//loading sectors
		for(int x = 0;x < WORLD_SECTOR_WIDTH;x++) {
			for(int y = 0;y < WORLD_SECTOR_HEIGHT;y++) {
				Sector sector = new Sector(handler, x, y);
				int[][] tileMap = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, }, 
						{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, }, 
						{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, }, 
						{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, }, 
						{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, }, 
						{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, }, 
						{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, }, 
						{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, }, 
						{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, }, 
						{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, }, 
						{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, }, 
						{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, }, 
						{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, }, 
						{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, }, 
						{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, }, 
						{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, }, 
				};
				sector.loadSectorTiles(tileMap);
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
		placeStaticEntities(staticEntities);
		staticEntities = new ArrayList<StaticEntity>();
		//load walls
		for(int x = 0;x < WORLD_SECTOR_WIDTH*Sector.SECTOR_WIDTH;x++) {
			for(int y = 0;y < WORLD_SECTOR_HEIGHT*Sector.SECTOR_HEIGHT;y++) {
				if(noise.eval(x*0.1f, y*0.1f) < -0.5f) {
					staticEntities.add(new Wall(handler, x*Tile.TILE_WIDTH, y*Tile.TILE_HEIGHT, Tile.TILE_WIDTH, Tile.TILE_HEIGHT, 0));
				}
				placeStaticEntities(staticEntities);
				staticEntities = new ArrayList<StaticEntity>();
			}
		}
		System.out.println("[World]\t\tLoad world took: " + (System.currentTimeMillis()-startTime) + "ms");
	}

	private void placeStaticEntities (ArrayList<StaticEntity> entities) {
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

	public long getWorldSeed() {
		return worldSeed;
	}

}
