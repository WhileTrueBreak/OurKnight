package dev.world.pathfinding.quadtree;

import java.util.ArrayList;

class Node{

	ArrayList<Node> adjNodes = new ArrayList<Node>();

	private float x, y, width, height;

	public Node(float x, float y, float w, float h){
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
	}

	public void addAdjNode(Node node){
		if(adjNodes.contains(node))return;
		adjNodes.add(node);
	}

	public ArrayList<Node> getAdjNodes() {
		return adjNodes;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

}