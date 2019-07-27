package dev.world.pathfinding;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;

public class Pathfinding {	

	//start and end x and y is the center of the entity
	public static ArrayList<Node> getPath(float startX, float startY, float endX, float endY, float width, float height, ArrayList<Node>nodes){
		ArrayList<Node>allNodes = nodes;
		ArrayList<Node>openSet = new ArrayList<Node>();
		ArrayList<Node>closedSet = new ArrayList<Node>();
		ArrayList<Node>path = new ArrayList<Node>();
		//add start and end node in to all nodes
		//create start node
		Node startNode = new Node(startX, startY, width, height);
		Rectangle startBound = new Rectangle((int)(startNode.getX()+startNode.getWidth()/2), (int)(startNode.getY()+startNode.getHeight()/2), 
				(int)startNode.getWidth(), (int)startNode.getHeight());
		//create end node
		Node endNode = new Node(endX, endY, width, height);
		Rectangle endBound = new Rectangle((int)(endNode.getX()+endNode.getWidth()/2), (int)(endNode.getY()+endNode.getHeight()/2), 
				(int)endNode.getWidth(), (int)endNode.getHeight());
		//add node connections
		boolean startFound = false;
		boolean endFound = false;
		//check if node is in another node
		for(Node n:allNodes) {
			Rectangle bound = new Rectangle((int)(n.getX()-n.getWidth()/2), (int)(n.getY()-n.getHeight()/2), (int)n.getWidth(), (int)n.getHeight());
			//check start node
			if(bound.intersects(startBound)) {
				startNode.addAdjNode(n);
				n.addAdjNode(startNode);
				startFound = true;
			}
			//check end node
			if(bound.intersects(endBound)) {
				endNode.addAdjNode(n);
				n.addAdjNode(endNode);
				endFound = true;
			}
		}
		//if start or end was not in quadtree return empty path
		if(!startFound || !endFound) return path;
		//if start and end node is in the same area
		for(Node n:startNode.getAdjNodes()) {
			if(endNode.getAdjNodes().contains(n)) {
				path.add(endNode);
				return path;
			}
		}
		//set current node
		Node currentNode = startNode;
		openSet.add(currentNode);
		while(openSet.size()!=0) {
			//move to closed set
			openSet.remove(currentNode);
			closedSet.add(currentNode);
			//add everything to the open set
			//System.out.println("[Pathfinding]\tNeightbor count: "+currentNode.getAdjNodes().size());
			for(Node n:currentNode.getAdjNodes()) {
				if(closedSet.contains(n)) continue;
				if(openSet.contains(n) && Math.hypot(endNode.getX()-n.getX(), endNode.getY()-n.getY()) < openSet.get(openSet.indexOf(n)).getG()) { 
					n.setG(currentNode.getF()+(float) Math.hypot(currentNode.getX()-n.getX(), currentNode.getY()-n.getY())); 
					n.setH((float) Math.hypot(endNode.getX()-n.getX(), endNode.getY()-n.getY())); 
					n.setF(n.getG()+n.getH()); 
					openSet.add(openSet.indexOf(n), n); 
					continue; 
				}
				n.setG(currentNode.getF()+(float) Math.hypot(currentNode.getX()-n.getX(), currentNode.getY()-n.getY()));
				n.setH((float) Math.hypot(endNode.getX()-n.getX(), endNode.getY()-n.getY()));
				n.setF(n.getG()+n.getH());
				openSet.add(n);
				n.setParent(currentNode);
			}
			//get the one with the lowest f value
			//find lowest
			float lowest = (float) Double.POSITIVE_INFINITY;
			Node lowestNode = null;
			for(Node n:openSet) {
				if(n.getF()<lowest) {
					lowest = n.getF();
					lowestNode = n;
				}
			}
			//set lowest to current
			if(lowestNode==null) break;
			currentNode = lowestNode;
			//check if destination is reached
			if(currentNode == endNode) {
				//retrace steps
				Node current = currentNode;
				while(current != null) {
					path.add(current);
					current = current.getParent();
				}
				//reverse arraylist
				Collections.reverse(path);
				path.remove(0);
				return path;
			}
		}
		return path;
	}

}
