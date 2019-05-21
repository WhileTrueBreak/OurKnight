package dev.entity.pathfinding;

import java.awt.Rectangle;
import java.util.ArrayList;

public class Node {
	
	private ArrayList<Node> connections = new ArrayList<Node>();
	private Rectangle bound;
	private float x, y;
	
	public Node(float x, float y, Rectangle bound) {
		this.bound = bound;
		this.x = x;
		this.y = y;
	}
	
	public void addConnection(Node node) {
		connections.add(node);
	}
	
	public void removeConnection(Node node) {
		connections.remove(node);
	}

	public ArrayList<Node> getConnections() {
		return connections;
	}
	
	public Rectangle getBound() {
		return bound;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
	
}
