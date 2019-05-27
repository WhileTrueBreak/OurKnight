package dev.entity.pathfinding.quadtree;

import java.awt.Rectangle;

import dev.Handler;
import dev.entity.staticEntity.StaticEntity;
import dev.world.Sector;
import dev.world.World;

public class Node {

	private Node parent;

	private Handler handler;

	private int x, y, width, height;
	private int depth;

	private boolean isBranch;

	public Node(Handler handler, int x, int y, int width, int height, int depth, Node parent) {
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.depth = depth;
		this.parent = parent;
		isBranch = false;
	}

	//TODO refactor method name
	public boolean needsChildren() {
		for(int x = (this.x/Sector.SECTOR_PIXEL_WIDTH)-1;x < (this.x/Sector.SECTOR_PIXEL_WIDTH)+2;x++) {
			for(int y = (this.y/Sector.SECTOR_PIXEL_HEIGHT)-1;y < (this.y/Sector.SECTOR_PIXEL_HEIGHT)+2;y++) {
				if(x < 0 || y < 0 || x >= World.WORLD_SECTOR_WIDTH || y >= World.WORLD_SECTOR_HEIGHT)continue;
				for(StaticEntity e:handler.getWorld().getSectorManager().getSector(x, y).getStaticEntityManager().getStaticEntities()) {
					if(e.getHitbox().contains(new Rectangle(this.x, this.y, width, height))) {
						isBranch = true;
						return true;
					}
				}
			}
		}
		return false;
	}

	public Node getParent() {
		return parent;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getDepth() {
		return depth;
	}

	public boolean isBranch() {
		return isBranch;
	}

	public void setBranch(boolean isBranch) {
		this.isBranch = isBranch;
	}
	
}
