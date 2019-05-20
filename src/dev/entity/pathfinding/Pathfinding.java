package dev.entity.pathfinding;

import java.util.ArrayList;

import dev.utils.Vector;

public class Pathfinding {

	private ArrayList<Node>nodes = new ArrayList<Node>();

	public Pathfinding() {

	}

	public void updateNodes(){	
		//TODO create node mesh using a quadtree
	}

	public ArrayList<Vector> getPath(float sx, float sy, float dx, float dy) {
		boolean foundStart = false, foundEnd = false;

		Node startPoint = new Node(new Vector(sx, sy), null);
		Node endPoint = new Node(new Vector(dx, dy), null);
		
		for(Node n:nodes) {
			if(!foundStart) {
				if(n.getBound().contains(startPoint.getPos().getX(), startPoint.getPos().getY())) {
					startPoint.addConnection(n);
					foundStart = true;
				}
			}
			if(!foundEnd) {
				if(n.getBound().contains(endPoint.getPos().getX(), endPoint.getPos().getY())) {
					endPoint.addConnection(n);
					foundEnd = true;
				}
			}
			if(foundStart && foundEnd) {
				break;
			}
		}
		//if starting and end points are in the same node return end point
		if(startPoint.getConnections().get(0)==endPoint.getConnections().get(0)) {
			ArrayList<Vector>path = new ArrayList<Vector>();
			path.add(endPoint.getPos());
			return path;
		}
		
		//TODO pathfinding in node mesh
		
		return null;
	}

}
