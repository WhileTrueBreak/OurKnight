package dev.entity.pathfinding.quadtree;

import java.awt.Rectangle;
import java.util.ArrayList;

import dev.Handler;
import dev.entity.staticEntity.StaticEntity;
import dev.world.Sector;

public class Quad {

	private Handler handler;

	private Rectangle bound;
	private Quad[] children;
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
				children[0] = new Quad(handler, x    , y    , w/2, h/2, minSize, this);
				children[1] = new Quad(handler, x+w/2, y    , w/2, h/2, minSize, this);
				children[2] = new Quad(handler, x    , y+h/2, w/2, h/2, minSize, this);
				children[3] = new Quad(handler, x+w/2, y+h/2, w/2, h/2, minSize, this);
			}
		}
		this.minSize = minSize;
	}

	public void checkBranches() {
		int x = bound.x, y = bound.y, w = bound.width, h = bound.height;
		if(children == null) {
			if(h/2 < minSize || w/2 < minSize)
				return;
			boolean collided = isCollided();
			if(collided) {
				contains = true;
				if(h > minSize && w > minSize) {
					children[0] = new Quad(handler, x    , y    , w/2, h/2, minSize, this);
					children[1] = new Quad(handler, x+w/2, y    , w/2, h/2, minSize, this);
					children[2] = new Quad(handler, x    , y+h/2, w/2, h/2, minSize, this);
					children[3] = new Quad(handler, x+w/2, y+h/2, w/2, h/2, minSize, this);
				}
			}else {
				contains = false;
			}
		}
	}
	
	private boolean isCollided() {
		int x = bound.x, y = bound.y, w = bound.width, h = bound.height;
		for(int i = (int)x/(Sector.SECTOR_PIXEL_WIDTH);i < Math.ceil((x+w)/(Sector.SECTOR_PIXEL_WIDTH));i++) {
			for(int j = (int)y/(Sector.SECTOR_PIXEL_HEIGHT);j < Math.ceil((y+h)/(Sector.SECTOR_PIXEL_HEIGHT));j++) {
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
		if(children != null) {
			for(Quad q:children) {
				if(q.isContains()) {
					empty = false;
					break;
				}
			}
			if(empty) {
				children = null;
				contains = false;
			}
		}
		parent.mergeEmpty();
	}

	public ArrayList<Quad> getEndQuads(){
		ArrayList<Quad>quads = new ArrayList<Quad>();
		if(children == null) {
			quads.add(this);
			return quads;
		}
		for(Quad q:children) {
			quads.addAll(q.getEndQuads());
		}
		return quads;
	}

	public ArrayList<Rectangle> getBounds(){
		ArrayList<Rectangle>bounds = new ArrayList<Rectangle>();
		if(children != null) {
			for(Quad q:children) {
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

	public Rectangle getBound() {
		return bound;
	}
	
}
