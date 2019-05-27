package dev.entity.pathfinding;

import java.util.ArrayList;

import dev.Handler;
import dev.entity.pathfinding.quadtree.Quadtree;
import dev.utils.Vector;

public class Pathfinding {	
	
	private Quadtree tree;
	
	private Handler handler;
	
	public Pathfinding(Handler handler) {
		this.handler = handler;
	}

	public void updateNodes(){
	}

	public ArrayList<Vector> getPath(float sx, float sy, float dx, float dy) {
		return null;
	}

}
