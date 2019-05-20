package dev.entity.pathfinding;

import java.awt.Rectangle;
import java.util.ArrayList;

import dev.utils.Vector;

public class Node {
	
	private ArrayList<Node> connections = new ArrayList<Node>();
	private Rectangle bound;
	private Vector pos;
	
	public Node(Vector pos, Rectangle bound) {
		this.pos = pos;
		this.bound = bound;
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

	public Vector getPos() {
		return pos;
	}
	
}
