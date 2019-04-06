package dev.world;

import java.awt.Graphics;
import java.util.ArrayList;

import dev.Handler;
import dev.entity.creature.Enemy;
import dev.entity.creature.Player;
import dev.tiles.Floor;
import dev.tiles.Tile;
import dev.tiles.Trap;
import dev.tiles.Wall;
import dev.utils.Utils;

public class World {
	
	ArrayList<Tile>tiles = new ArrayList<Tile>();
	
	private Player player;
	private Enemy enemytest;
	private Handler handler;
	
	public World(Handler handler, String path) {
		this.handler = handler;
		handler.setWorld(this);
		
		player = new Player(handler, 400, 400);
		enemytest = new Enemy(handler, 500, 500);
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
					tiles.add(new Wall(handler, x*Tile.tile_width, y*Tile.tile_height, 0));
					break;
				case 1:
					tiles.add(new Floor(handler, x*Tile.tile_width, y*Tile.tile_height, 0));
					break;
				case 2:
					tiles.add(new Trap(handler, x*Tile.tile_width, y*Tile.tile_height, 0));
					break;
				}
			}
		}
	}
	
	public void update() {
		for(Tile t:tiles) {
			t.update();
		}
		player.update();
		enemytest.update();
	}
	
	public void render(Graphics g) {
		for(Tile t:tiles) {
			t.render(g);
		}
		player.render(g);
		enemytest.render(g);
	}
	
	public int getPlayerX() {
		return player.getX();
	}
	
	public int getPlayerY() {
		return player.getY();
	}
}
