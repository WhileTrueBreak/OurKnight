package dev.tiles;

import java.awt.Graphics;

import dev.Handler;
import dev.entity.staticEntity.Wall;

public class WallSpawner extends Tile{

	public WallSpawner(Handler handler, int x, int y, int spriteid) {
		super(handler, x, y, spriteid);
		Wall wall = new Wall(handler, x, y, Tile.tile_width, Tile.tile_height, spriteID);
		handler.getWorld().getStaticEntityManager().addStaticEntity(wall);
		handler.getWorld().getEntityManager().addEntity(wall);
	}

	@Override
	public void update() {
		
	}

	@Override
	public void render(Graphics g) {
		
	}

	@Override
	public boolean isSolid() {
		return true;
	}
	
}
