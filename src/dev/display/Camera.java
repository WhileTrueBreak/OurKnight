package dev.display;

import dev.Handler;
import dev.entity.Entity;

public class Camera {
	
	private Handler handler;
	private float xoff, yoff;
	
	public Camera(Handler handler, float xoff, float yoff) {
		this.handler = handler;
		this.xoff = xoff;
		this.yoff = yoff;
	}
	
	public void focusOnEntity(Entity e) {
		float cameraSpring = 10;
		float setX = e.getX()-handler.getMain().getWidth()/2;
		float setY = e.getY() - handler.getMain().getHeight()/2;
		move((setX-xoff)/cameraSpring, (setY-yoff)/cameraSpring);
	}
	
	public void move(float amtx, float amty) {
		xoff += amtx;
		yoff += amty;
	}

	public float getXoff() {
		return xoff;
	}

	public void setXoff(int xoff) {
		this.xoff = xoff;
	}

	public float getYoff() {
		return yoff;
	}

	public void setYoff(int yoff) {
		this.yoff = yoff;
	}
	
}
