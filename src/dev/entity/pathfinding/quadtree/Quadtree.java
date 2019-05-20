package dev.entity.pathfinding.quadtree;

import java.awt.Rectangle;
import java.util.ArrayList;

import dev.Handler;

public class Quadtree {
	
	private Handler handler;
	private Quad child;
	
	public Quadtree(Handler handler, float x, float y, float w, float h, float minSize) {
		this.handler = handler;
		child = new Quad(handler, x, y, w, h, minSize, null);
	}
	
	public void update() {
		child.checkBranches();
		for(Quad q:getEndQuads())
			q.mergeEmpty();
	}
	
	private ArrayList<Quad> getEndQuads(){
		return child.getEndQuads();
	}
	
	public ArrayList<Rectangle> getBounds(){
		return child.getBounds();
	}
	
}
