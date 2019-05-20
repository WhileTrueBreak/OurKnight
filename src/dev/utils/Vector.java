package dev.utils;

public class Vector {
	
	private float x = 0, y = 0, z = 0;
	
	public Vector(float x, float y) {
		this.x = x;
		this.y = y;
	}
	public Vector(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	
	
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public float getZ() {
		return z;
	}
	public void setZ(float z) {
		this.z = z;
	}
	
}
