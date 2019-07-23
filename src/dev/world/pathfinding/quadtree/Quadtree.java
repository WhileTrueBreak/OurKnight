package dev.world.pathfinding.quadtree;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import dev.Handler;
import dev.entity.Entity;

public class Quadtree{

	public static int MAX_LEVEL = 5;
	public static int MIN_SIZE = 32;

	private Handler handler;
	
	private Node node;
	private Quadtree[] nodes;
	private float x, y, width, height;
	@SuppressWarnings("unused")
	private boolean blocked, contained;
	private int level;

	public Quadtree(Handler handler, int w, int h){
		this(handler, 0, 0, 0, w, h);
	}

	public Quadtree(Handler handler, int level, float x, float y, float w, float h){
		this.handler = handler;
		node = new Node(x+w/2, y+h/2, w, h);
		this.blocked = false;
		this.level = level;
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
	}

	private void split(){
		//splits the current quadtree in four 
		nodes = new Quadtree[4];
		nodes[0] = new Quadtree(handler, level+1, x    		, y   		, width/2, height/2);
		nodes[1] = new Quadtree(handler, level+1, x+width/2	, y    		, width/2, height/2);
		nodes[2] = new Quadtree(handler, level+1, x    		, y+height/2, width/2, height/2);
		nodes[3] = new Quadtree(handler, level+1, x+width/2	, y+height/2, width/2, height/2);
	}

	public void addBlocks(ArrayList<Entity>entities){
		for(Entity b:entities) add(b);
	}

	private void add(Entity entity){
		//check if the node is completely contain in the block
		if(entity.getHitbox().x < x && entity.getHitbox().y < y && entity.getHitbox().x+entity.getHitbox().width > x+width && entity.getHitbox().y+entity.getHitbox().height > y+height){
			contained = true;
			return;
		}
		//check if node will not be too small
		if(width/2 < MIN_SIZE || height/2 < MIN_SIZE) return;
		//check if split is possible
		if(nodes == null && level < MAX_LEVEL) split();
		if(level >= MAX_LEVEL) return;

		blocked = true;
		//check if block intersects quadrants
		if(entity.getHitbox().x+entity.getX() < nodes[0].x + nodes[0].width && 
				entity.getHitbox().x+entity.getX() + entity.getHitbox().width > nodes[0].x && 
				entity.getHitbox().y+entity.getY() < nodes[0].y + nodes[0].height && 
				entity.getHitbox().y+entity.getY() + entity.getHitbox().height > nodes[0].y){
			nodes[0].blocked = true;
			nodes[0].add(entity);
		}
		if(entity.getHitbox().x+entity.getX() < nodes[1].x + nodes[1].width && 
				entity.getHitbox().x+entity.getX() + entity.getHitbox().width > nodes[1].x && 
				entity.getHitbox().y+entity.getY() < nodes[1].y + nodes[1].height && 
				entity.getHitbox().y+entity.getY() + entity.getHitbox().height > nodes[1].y){
			nodes[1].blocked = true;
			nodes[1].add(entity);
		}
		if(entity.getHitbox().x+entity.getX() < nodes[2].x + nodes[2].width && 
				entity.getHitbox().x+entity.getX() + entity.getHitbox().width > nodes[2].x && 
				entity.getHitbox().y+entity.getY() < nodes[2].y + nodes[2].height && 
				entity.getHitbox().y+entity.getY() + entity.getHitbox().height > nodes[2].y){
			nodes[2].blocked = true;
			nodes[2].add(entity);
		}
		if(entity.getHitbox().x+entity.getX() < nodes[3].x + nodes[3].width && 
				entity.getHitbox().x+entity.getX() + entity.getHitbox().width > nodes[3].x && 
				entity.getHitbox().y+entity.getY() < nodes[3].y + nodes[3].height && 
				entity.getHitbox().y+entity.getY() + entity.getHitbox().height > nodes[3].y){
			nodes[3].blocked = true;
			nodes[3].add(entity);
		}
	}

	public void dfs(Graphics g){
		//print debug
//		System.out.printf("\nLevel = %d [X=%d Y=%d]", level, (int)x, (int)y);
//		System.out.printf(" [W=%d H=%d]", (int)width, (int)height);
//		System.out.printf("\n\tBlocked=" + blocked);
//		System.out.printf("\n\tContained=" + contained);
		if(nodes == null){
//			System.out.print("\n\t[Leaf Node]");
			Rectangle bound = new Rectangle((int)(x-handler.getCamera().getXoff()), (int)(y-handler.getCamera().getYoff()), (int)width, (int)height);
			if(handler.getScreenBound().contains(bound) && !blocked) {
				g.setColor(new Color(255, 0, 0));
				g.drawRect((int)(x-handler.getCamera().getXoff()), (int)(y-handler.getCamera().getYoff()), (int)width, (int)height);
			}
		}else{
			//go deeper
//			System.out.print("\n\t[Branch Node]");
			nodes[0].dfs(g);
			nodes[1].dfs(g);
			nodes[2].dfs(g);
			nodes[3].dfs(g);
		}
	}

	ArrayList<Quadtree> dfsGetLeaf(){
		ArrayList<Quadtree> qtLeaf = new ArrayList<Quadtree>();
		if(nodes == null){
			if(blocked)return qtLeaf;
			qtLeaf.add(this);
		}else{
			//go deeper
			qtLeaf.addAll(nodes[0].dfsGetLeaf());
			qtLeaf.addAll(nodes[1].dfsGetLeaf());
			qtLeaf.addAll(nodes[2].dfsGetLeaf());
			qtLeaf.addAll(nodes[3].dfsGetLeaf());
		}
		return qtLeaf;
	}

	public void createNavMesh(){
		ArrayList<Quadtree> qtLeaf = dfsGetLeaf();
		for(Quadtree quadtreePrimary:qtLeaf){
			for(Quadtree quadtreeSecondary:qtLeaf){
				if(quadtreePrimary==quadtreeSecondary)continue;
				
				//TODO Add setting to change this pathfinding option
				//change the < and > signs to <= or >= to allow diagonals(may cause entities to get stuck a places)
				
				//check top adj nodes
				int highY = (int)quadtreeSecondary.getY()+(int)quadtreeSecondary.getHeight();
				if(highY == (int)quadtreePrimary.getY() && 
						(int)quadtreeSecondary.getX() < (int)quadtreePrimary.getX()+(int)quadtreePrimary.getWidth() && 
						(int)quadtreeSecondary.getX()+(int)quadtreeSecondary.getWidth() > (int)quadtreePrimary.getX())
					quadtreePrimary.getNode().addAdjNode(quadtreeSecondary.getNode());
				//check left adj nodes
				int highX = (int)quadtreeSecondary.getX()+(int)quadtreeSecondary.getWidth();
				if(highX == (int)quadtreePrimary.getX() && 
						(int)quadtreeSecondary.getY() < (int)quadtreePrimary.getY()+(int)quadtreePrimary.getHeight() && 
						(int)quadtreeSecondary.getY()+(int)quadtreeSecondary.getHeight() > (int)quadtreePrimary.getY())
					quadtreePrimary.getNode().addAdjNode(quadtreeSecondary.getNode());
				//check bottom adj nodes
				if((int)quadtreeSecondary.getY() == (int)(quadtreePrimary.getY()+quadtreePrimary.getHeight()) && 
						(int)quadtreeSecondary.getX() < (int)quadtreePrimary.getX()+(int)quadtreePrimary.getWidth() && 
						(int)quadtreeSecondary.getX()+(int)quadtreeSecondary.getWidth() > (int)quadtreePrimary.getX())
						quadtreePrimary.getNode().addAdjNode(quadtreeSecondary.getNode());
				//check right adj nodes
				if((int)quadtreeSecondary.getX() == (int)(quadtreePrimary.getX()+quadtreePrimary.getWidth()) && 
						(int)quadtreeSecondary.getY() < (int)quadtreePrimary.getY()+(int)quadtreePrimary.getHeight() && 
						(int)quadtreeSecondary.getY()+(int)quadtreeSecondary.getHeight() > (int)quadtreePrimary.getY())
						quadtreePrimary.getNode().addAdjNode(quadtreeSecondary.getNode());
			}
		}
	}

	public void clearNavMesh(){
		ArrayList<Quadtree> qtLeaf = dfsGetLeaf();
		for(Quadtree qt:qtLeaf){
			qt.node.adjNodes = new ArrayList<Node>();
		}
	}

	public void renderNavMesh(Graphics g){
		ArrayList<Quadtree> qtLeaf = dfsGetLeaf();
		g.setColor(new Color(0,255,0));
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(1));
		for(Quadtree qt:qtLeaf){
			for(Node n:qt.node.adjNodes){
				Rectangle bound = new Rectangle((int)Math.min(n.getX(),qt.node.getX()), 
						(int)Math.min(n.getY(),qt.node.getY()), 
						(int)Math.abs(qt.node.getX()-n.getX()), 
						(int)Math.abs(qt.node.getY()-n.getY()));
				if((int)Math.abs(qt.node.getX()-n.getX())==0||(int)Math.abs(qt.node.getY()-n.getY())==0) {
					g.drawLine(
							(int)(n.getX()), 
							(int)(n.getY()), 
							(int)(qt.node.getX()), 
							(int)(qt.node.getY()));
				}
				if(handler.getScreenBound().contains(bound)) g.drawLine(
						(int)(n.getX()), 
						(int)(n.getY()), 
						(int)(qt.node.getX()), 
						(int)(qt.node.getY()));
			}
		}
	}

	public ArrayList<Node> getNavMesh(){
		//get leafnodes
		ArrayList<Quadtree> qtLeaf = dfsGetLeaf();
		ArrayList<Node> nodes = new ArrayList<Node>();
		//put all nodes in to an arraylost
		for(Quadtree qt:qtLeaf){
			nodes.add(qt.node);
		}
		return nodes;
	}

	private void mergeQuadrants(){
		//check if leaf node
		if(nodes==null) return;
		//check if merge is required
		if(!nodes[0].blocked&&!nodes[1].blocked&&!nodes[2].blocked&&!nodes[3].blocked){
			nodes=null;
			return;
		}
		//go deeper if merge is not required
		nodes[0].mergeQuadrants();
		nodes[1].mergeQuadrants();
		nodes[2].mergeQuadrants();
		nodes[3].mergeQuadrants();
	}

	private void clearFlags(){
		//remove flags
		blocked = false;
		contained = false;
		if(nodes == null) return;
		//go deeper
		nodes[0].clearFlags();
		nodes[1].clearFlags();
		nodes[2].clearFlags();
		nodes[3].clearFlags();
	}

	public void updateQuadtree(ArrayList<Entity>entities){
		clearFlags();
		//add in the new entities
		for(Entity e:entities) add(e);
		mergeQuadrants();
	}
	
	public void updateNavMesh() {
		clearNavMesh();
		createNavMesh();
	}
	
	public void update(ArrayList<Entity>entities) {
		updateQuadtree(entities);
		updateNavMesh();
	}

	public void clear(){
		nodes = null;
	}

	//getters and setters

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
	
	public Node getNode() {
		return node;
	}
}