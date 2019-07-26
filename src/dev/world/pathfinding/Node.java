package dev.world.pathfinding;

import java.util.ArrayList;

public class Node{

	ArrayList<Node> adjNodes = new ArrayList<Node>();

	private float x, y, width, height;
	private float f = 0, g = 0, h = 0;
	
	private Node parent = null;

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
	
	public void setAdjNodes(ArrayList<Node>nodes) {
		adjNodes = nodes;
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

	public float getF() {
		return f;
	}

	public void setF(float f) {
		this.f = f;
	}

	public float getG() {
		return g;
	}

	public void setG(float g) {
		this.g = g;
	}

	public float getH() {
		return h;
	}

	public void setH(float h) {
		this.h = h;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}
}