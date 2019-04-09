package dev.world;

import java.awt.Graphics;
import java.util.ArrayList;

import dev.Handler;
import dev.entity.Entity;
import dev.entity.EntityManager;
import dev.entity.creature.Player;
import dev.entity.creature.enemy.BasicEnemy;
import dev.entity.creature.enemy.EnemyManager;
import dev.entity.staticEntity.StaticEntity;
import dev.entity.staticEntity.StaticEntityManager;
import dev.entity.staticEntity.Trap;
import dev.entity.staticEntity.Wall;
import dev.tiles.Floor;
import dev.tiles.Tile;
import dev.tiles.WallSpawner;
import dev.utils.Utils;

public class World {
	
	//managers
	EnemyManager enemyManager;
	StaticEntityManager staticEntityManager;
	EntityManager entities;
	
	//tiles
	ArrayList<Tile>tiles = new ArrayList<Tile>();
	
	//player
	private Player player;
	
	//handler
	private Handler handler;
	
	public World(Handler handler, String path) {
		this.handler = handler;
		handler.setWorld(this);
		enemyManager = new EnemyManager(handler);
		staticEntityManager = new StaticEntityManager(handler);
		entities = new EntityManager();
		
		player = new Player(handler, 400, 400);
//		new WallSpawner(handler, 2*Tile.tile_width, 45, 0);
		BasicEnemy enemy = new BasicEnemy(handler,500,500);
		enemyManager.addEnemy(enemy);
		entities.addEntity(player);
		
		loadWorld(path);
	}
	
	private void loadWorld(String path) {
        String file = Utils.loadFile(path);
        String[] tokens = file.split("\\s+");
        int width = Utils.parseInt(tokens[0]);
        int height = Utils.parseInt(tokens[1]);
        for(int y = 0;y < height;y++) {
            for(int x = 0;x < width;x++) {
                switch(Utils.parseInt(tokens[x+y*width+2])) {
                case 0:
                    StaticEntity wall = new Wall(handler, x*Tile.tile_width, y*Tile.tile_height, Tile.tile_width, Tile.tile_height, 0);
                    handler.getWorld().getStaticEntityManager().addStaticEntity(wall);
                    handler.getWorld().getEntityManager().addEntity(wall);
                    break;
                case 1:
                    tiles.add(new Floor(handler, x*Tile.tile_width, y*Tile.tile_height, 0));
                    break;
                case 2:
                    StaticEntity trap = new Trap(handler, x*Tile.tile_width, y*Tile.tile_height, Tile.tile_width, Tile.tile_height, 0);
                    handler.getWorld().getStaticEntityManager().addStaticEntity(trap);
                    handler.getWorld().getEntityManager().addEntity(trap);
                    break;
                }
            }
        }
    }
	
	public void update() {
		for(Tile t:tiles) {
			t.update();
		}
		staticEntityManager.update();
		player.update();
		enemyManager.update();
	}
	
	public void render(Graphics g) {
		for(Tile t:tiles) {
			t.render(g);
		}
		entities.render(g);
//		staticEntityManager.render(g);
//		player.render(g);
//		enemyManager.render(g);
	}
	
	public int getPlayerX() {
		return player.getX();
	}
	
	public int getPlayerY() {
		return player.getY();
	}

	public EnemyManager getEnemyManager() {
		return enemyManager;
	}

	public StaticEntityManager getStaticEntityManager() {
		return staticEntityManager;
	}

	public EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return entities;
	}
	
}
