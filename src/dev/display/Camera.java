package dev.display;

import dev.Handler;
import dev.entity.Entity;
import dev.tiles.Tile;
import dev.world.Sector;
import dev.world.World;

public class Camera {
	
	private Handler handler;
	private float xoff, yoff;
	
	public Camera(Handler handler, float xoff, float yoff) {
		this.handler = handler;
		this.xoff = xoff;
		this.yoff = yoff;
	}
	
	public void focusOnEntity(Entity e, int spring) {
		float cameraSpring = spring;
		float setX = e.getX()-handler.getMain().getWidth()/2;
		float setY = e.getY() - handler.getMain().getHeight()/2;
		if(cameraSpring == 0)
			move((setX-xoff), (setY-yoff));
		else
			move((setX-xoff)/cameraSpring, (setY-yoff)/cameraSpring);
	}
	
	public void move(float amtx, float amty) {
		xoff += amtx;
		yoff += amty;
		if (xoff < 0) xoff=0;
		if (yoff < 0) yoff=0;
		if (xoff > World.WORLD_SECTOR_WIDTH*Sector.SECTOR_WIDTH*Tile.TILE_WIDTH-handler.getWidth()) xoff=World.WORLD_SECTOR_WIDTH*Sector.SECTOR_WIDTH*Tile.TILE_WIDTH-handler.getWidth();
		if (yoff > World.WORLD_SECTOR_HEIGHT*Sector.SECTOR_HEIGHT*Tile.TILE_HEIGHT-handler.getHeight()) yoff=World.WORLD_SECTOR_HEIGHT*Sector.SECTOR_HEIGHT*Tile.TILE_HEIGHT-handler.getHeight();
	}

	public float getXoff() {
		return xoff;
	}

	public void setXoff(int xoff) {
		this.xoff = xoff;
	}

	public float getYoff() {
		return yoff;
	}

	public void setYoff(int yoff) {
		this.yoff = yoff;
	}
	
}
