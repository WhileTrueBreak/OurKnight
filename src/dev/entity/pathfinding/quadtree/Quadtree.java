package dev.entity.pathfinding.quadtree;

import java.util.ArrayList;

import dev.Handler;

public class Quadtree {
	
	ArrayList<Node>nodes = new ArrayList<Node>();
	
	private Handler handler;
	
	private int x, y, width, height;
	private int lowerBound;
	
	public Quadtree(Handler handler, int x, int y, int width, int height, int LBound) {
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.lowerBound = LBound;
		createTree();
	}
	
	private void createTree() {
		nodes.add(new Node(handler, x, y, width, height, 0, null));//create root node
		boolean allLeafs = false;
		while(!allLeafs) {
			allLeafs = true;
			ArrayList<Node>toAdd = new ArrayList<Node>();
			ArrayList<Node>toRemove = new ArrayList<Node>();
			for(Node n:nodes) {
				if(n.getWidth() <= lowerBound || n.getHeight() <= lowerBound) n.setBranch(false);
				if(n.needsChildren()) {
					toAdd.add(new Node(handler, x		 , y		 , width/2, height/2, n.getDepth()+1, n));
					toAdd.add(new Node(handler, x+width/2, y		 , width/2, height/2, n.getDepth()+1, n));
					toAdd.add(new Node(handler, x		 , y+height/2, width/2, height/2, n.getDepth()+1, n));
					toAdd.add(new Node(handler, x+width/2, y+height/2, width/2, height/2, n.getDepth()+1, n));
					toRemove.add(n);
					allLeafs = false;
				}
			}
			nodes.addAll(toAdd);
			nodes.removeAll(toRemove);
		}
	}
	
}
