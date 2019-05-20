package dev.entity.pathfinding.quadtree;

import java.awt.Rectangle;
import java.util.ArrayList;

import dev.Handler;
import dev.entity.staticEntity.StaticEntity;
import dev.world.Sector;

public class Quad {

	private Handler handler;

	private Rectangle bound;
	private Quad[] childs;
	private Quad parent;

	//TODO refactor this variable
	private boolean contains;
	private float minSize;

	public Quad(Handler handler, float x, float y, float w, float h, float minSize, Quad parent) {
		this.handler = handler;
		this.parent = parent;
		bound = new Rectangle();
		bound.x = (int) x;
		bound.y = (int) y;
		bound.height = (int) h;
		bound.width = (int) w;
		boolean collided = isCollided();
		if(collided) {
			contains = true;
			if(h > minSize && w > minSize) {
				childs[0] = new Quad(handler, x    , y    , w/2, h/2, minSize, this);
				childs[1] = new Quad(handler, x+w/2, y    , w/2, h/2, minSize, this);
				childs[2] = new Quad(handler, x    , y+h/2, w/2, h/2, minSize, this);
				childs[3] = new Quad(handler, x+w/2, y+h/2, w/2, h/2, minSize, this);
			}
		}
		this.minSize = minSize;
	}

	public void checkBranches() {
		int x = bound.x, y = bound.y, w = bound.width, h = bound.height;
		if(childs == null) {
			if(h/2 < minSize || w/2 < minSize)
				return;
			boolean collided = isCollided();
			if(collided) {
				contains = true;
				if(h > minSize && w > minSize) {
					childs[0] = new Quad(handler, x    , y    , w/2, h/2, minSize, this);
					childs[1] = new Quad(handler, x+w/2, y    , w/2, h/2, minSize, this);
					childs[2] = new Quad(handler, x    , y+h/2, w/2, h/2, minSize, this);
					childs[3] = new Quad(handler, x+w/2, y+h/2, w/2, h/2, minSize, this);
				}
			}else {
				contains = false;
			}
		}
	}
	
	private boolean isCollided() {
		int x = bound.x, y = bound.y, w = bound.width, h = bound.height;
		for(int i = (int)x/(Sector.SECTOR_PIXEL_WIDTH);i < Math.ceil((x+w)/(Sector.SECTOR_PIXEL_WIDTH))+1;i++) {
			for(int j = (int)y/(Sector.SECTOR_PIXEL_HEIGHT);j < Math.ceil((y+h)/(Sector.SECTOR_PIXEL_HEIGHT))+1;j++) {
				Sector sector = handler.getWorld().getSectorManager().getSector(i, j);
				if(sector == null)continue;
				for(StaticEntity e:sector.getStaticEntityManager().getStaticEntities()) {
					if(e.getHitbox().contains(bound)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public void mergeEmpty() {
		boolean empty = true;
		if(childs != null) {
			for(Quad q:childs) {
				if(q.isContains()) {
					empty = false;
					break;
				}
			}
			if(empty) {
				childs = null;
				contains = false;
			}
		}
		parent.mergeEmpty();
	}

	public ArrayList<Quad> getEndQuads(){
		ArrayList<Quad>quads = new ArrayList<Quad>();
		if(childs == null) {
			quads.add(this);
			return quads;
		}
		for(Quad q:childs) {
			quads.addAll(q.getEndQuads());
		}
		return quads;
	}

	public ArrayList<Rectangle> getBounds(){
		ArrayList<Rectangle>bounds = new ArrayList<Rectangle>();
		if(childs != null) {
			for(Quad q:childs) {
				bounds.addAll(q.getBounds());
			}
			return bounds;
		}
		if(!contains)bounds.add(bound);
		return bounds;
	}

	public boolean isContains() {
		return contains;
	}

}
