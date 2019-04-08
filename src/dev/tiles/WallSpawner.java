package dev.tiles;

import java.awt.Graphics;

import dev.Handler;
import dev.entity.staticEntity.Wall;

public class WallSpawner extends Tile{

	public WallSpawner(Handler handler, int x, int y, int spriteid) {
		super(handler, x, y, spriteid);
		handler.getWorld().getStaticEntityManager().addStaticEntity(new Wall(handler, x, y, Tile.tile_width, Tile.tile_height, spriteID));;
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
