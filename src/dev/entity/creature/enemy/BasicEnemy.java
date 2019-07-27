//TEMPERARY CODE TO TEST ENEMY

package dev.entity.creature.enemy;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Date;

import dev.Handler;
import dev.entity.Entity;
import dev.world.World;
import dev.world.pathfinding.Node;
import dev.world.pathfinding.Pathfinding;

public class BasicEnemy extends Enemy{

	public int width = 32, height = 32;

	private ArrayList<Node>path = new ArrayList<Node>();
	private boolean onRoute = false;

	public BasicEnemy(Handler handler, int x, int y) {
		super(handler, x, y);
		health = 10;
		speed = 2;
	}

	@Override
	public void update() {
		//update target positions
		targetX = target.getX();
		targetY = target.getY();
		//System.out.println("[BasicEnemy]\tPath length: " + path.size());
		if(path.size()!=0) {
			//move to planned location
			move(path.get(0).getX()-width/2, path.get(0).getY()-width/2, speed);
			//remove waypoint from waypoint list if reached
			float threshold = 0.01f;
			if(Math.hypot((path.get(0).getX()-width/2)-x, (path.get(0).getY()-width/2)-y)<threshold) {
				path.remove(0);
				if(path.size()!=0) System.out.println("[BasicEnemy]\tGoing to: [X:" + (path.get(0).getX()) + " Y:" + (path.get(0).getY()) + "]");
			}
		}else {
			//check if not at location
			float threshold = 0;
			if(Math.hypot((x+width/2)-(targetX+target.getWidth()/2),(y+height/2)-(targetY+target.getHeight()/2))>threshold) {
				onRoute = false;
			}
		}
		//per second clock
		if(handler.getMain().getTimer()>=1000000000) {
			//check if end point is still at target
			if(path.size() != 0) {
				float threshold = 0;
				if(Math.hypot(path.get(path.size()-1).getX()-(targetX+target.getWidth()/2),
						path.get(path.size()-1).getY()-(targetY+target.getHeight()/2))>threshold) {
					onRoute = false;
				}
			}
			if(!onRoute) {
				System.out.println("[BasicEnemy]\tRecalculating path");
				//updates path
				path = Pathfinding.getPath(x+width/2, y+height/2, 
						targetX+target.getWidth()/2, 
						targetY+target.getHeight()/2, width, height, 
						handler.getWorld().getQuadtree().getNavMesh());
				//check if path is empty
				if(path.size()==0) {
					move((float)(Math.random()+x), (float)(Math.random()+y), speed);
				}
				//check if path splice is possible
				if(path.size() >= 2) {
					Node a = path.get(0);
					Node b = path.get(1);
					float a1 = a.getX()-x;
					float a2 = b.getX()-a.getX();
					float b1 = a.getY()-y;
					float b2 = b.getY()-a.getY();
					float lamba = -(a1*a2+b1*b2)/((float)Math.pow(a2, 2)+(float)Math.pow(b2, 2));
					if(lamba<=1&&lamba>=0) {
						float spliceX = a.getX()+lamba*(b.getX()-a.getX()), spliceY = a.getY()+lamba*(b.getY()-a.getY());
						path.remove(0);
						path.add(0, new Node(spliceX, spliceY, 0, 0));
					}
				}
				//print path
				System.out.print("[BasicEnemy]\tPath: [");
				for(Node n:path) {
					System.out.printf("[X:%d Y:%d]",(int)n.getX(), (int)n.getY());
					if(path.indexOf(n)!=path.size()-1) System.out.print(",");
				}
				System.out.println("]");
				onRoute = true;
			}
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(new Color(0, 255, 0));
		g.fillRect((int)(x-handler.getCamera().getXoff()), (int)(y-handler.getCamera().getYoff()), width, height);
		//render path
		if(World.RENDER_DEBUG) {
			g.setColor(new Color(255, 0, 255));
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(2));
			if(path.size()!=0) {
				g.drawLine((int)(x+width/2-handler.getCamera().getXoff()), 
						(int)(y+height/2-handler.getCamera().getYoff()), 
						(int)(path.get(0).getX()-handler.getCamera().getXoff()), 
						(int)(path.get(0).getY()-handler.getCamera().getYoff()));
				for(int i = 0;i < path.size()-1;i++) {
					g.drawLine((int)(path.get(i).getX()-handler.getCamera().getXoff()), 
							(int)(path.get(i).getY()-handler.getCamera().getYoff()), 
							(int)(path.get(i+1).getX()-handler.getCamera().getXoff()), 
							(int)(path.get(i+1).getY()-handler.getCamera().getYoff()));
				}
			}
		}
	}

	@Override
	public void onCollision(Entity e) {
		if (inTimer) {
			startTimer(2);
		}else {
			System.out.println("hit");
			handler.getPlayer().setHealth(handler.getWorld().getPlayer().getHealth()-1);
			startTimer(2);
		}
	}

	private boolean startTimer(int s) {
		if (!inTimer) {
			startTime = System.currentTimeMillis();
			inTimer = true;
		}
		if (ticks < s*1000) {
			ticks = (new Date()).getTime() - startTime;
			return false;
		}
		inTimer = false;
		ticks = 0;
		return true;
	}
}

