package dev.entity.pathfinding;

import java.util.ArrayList;

import dev.Handler;
import dev.entity.pathfinding.quadtree.Quadtree;
import dev.tiles.Tile;
import dev.utils.Vector;
import dev.world.Sector;
import dev.world.World;

public class Pathfinding {	
	
	private ArrayList<Node>nodes = new ArrayList<Node>();
	private Quadtree root;
	
	private Handler handler;
	
	public Pathfinding(Handler handler) {
		this.handler = handler;
		root = new Quadtree(handler, 0, 0, Sector.SECTOR_PIXEL_WIDTH*World.WORLD_SECTOR_WIDTH, 
				Sector.SECTOR_PIXEL_HEIGHT*World.WORLD_SECTOR_HEIGHT, Tile.TILE_WIDTH);
	}

	public void updateNodes(){
		root.update();
		nodes = root.getNodes();
	}

	public ArrayList<Vector> getPath(float sx, float sy, float dx, float dy) {
		boolean foundStart = false, foundEnd = false;
		//defining the  start and end nodes
		Node startPoint = new Node(sx, sy, null);
		Node endPoint = new Node(dx, dy, null);
		//connecting the two node properly
		for(Node n:nodes) {
			if(!foundStart) {
				if(n.getBound().contains(startPoint.getX(), startPoint.getY())) {
					startPoint.addConnection(n);
					foundStart = true;
				}
			}
			if(!foundEnd) {
				if(n.getBound().contains(endPoint.getX(), endPoint.getY())) {
					endPoint.addConnection(n);
					foundEnd = true;
				}
			}
			if(foundStart && foundEnd) {
				break;
			}
		}
		ArrayList<Vector>path = new ArrayList<Vector>();
		//if starting and end points are in the same node return end point
		if(startPoint.getConnections().get(0)==endPoint.getConnections().get(0)) {
			path.add(new Vector(endPoint.getX(), endPoint.getY()));
			return path;
		}
		
		//TODO pathfinding in node mesh
		
		
		return path;
	}

}
