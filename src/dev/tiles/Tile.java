package dev.tiles;

import java.awt.Graphics;

public abstract class Tile {
	
	public static int TILE_WIDTH = 32, TILE_HEIGHT = 32;
	
	public static Tile[] tiles = new Tile[64];
	
	
	public Tile() {
		
	}
	
	public static void init() {
		tiles[0] = new Water();
		tiles[1] = new Floor();
	}
	
	public abstract void render(Graphics g, int x, int y);
}
