package dev.tiles;

import java.awt.Color;
import java.awt.Graphics;

public class Water extends Tile{

	@Override
	public void render(Graphics g, int x, int y) {
		g.setColor(new Color(0, 0,200));
		g.fillRect(x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
		g.setColor(new Color(0,255,255));
		g.drawRect(x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
	}

}
