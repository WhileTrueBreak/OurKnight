package dev.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import dev.ui.UIManager;

public class MouseManager implements MouseListener, MouseMotionListener{

	private boolean leftPress, middlePress, rightPress;
	
	private int mouseX, mouseY;
	
	private UIManager uiManager;
	
	public void setUIManager(UIManager uiManager) {
		this.uiManager = uiManager;
	}
	
	public MouseManager(){
		
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(uiManager!=null) uiManager.onMouseMove(e);
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1)
			leftPress = true;
		if(e.getButton() == MouseEvent.BUTTON2)
			middlePress = true;
		if(e.getButton() == MouseEvent.BUTTON3)
			rightPress = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(uiManager!=null) uiManager.onMouseRelease(e);
		if(e.getButton() == MouseEvent.BUTTON1)
			leftPress = false;
		if(e.getButton() == MouseEvent.BUTTON2)
			middlePress = false;
		if(e.getButton() == MouseEvent.BUTTON3)
			rightPress = false;
	}

	public boolean isLeftPress() {
		return leftPress;
	}

	public boolean isMiddlePress() {
		return middlePress;
	}

	public boolean isRightPress() {
		return rightPress;
	}

	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}
	
}
