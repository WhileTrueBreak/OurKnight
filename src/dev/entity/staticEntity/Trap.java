package dev.entity.staticEntity;

import java.awt.Color;
import java.awt.Graphics;

import dev.Handler;
import dev.entity.Entity;
public class Trap extends StaticEntity{

    public Trap(Handler handler, int x, int y, int width, int height, int spriteID) {
        super(handler, x, y, width, height, spriteID);
    }

    @Override
    public void update() {
        
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(0,200,0));
        g.fillRect((int)(x-handler.getCamera().getXoff()), (int)(y-handler.getCamera().getYoff()), width, height);
        g.setColor(new Color(0,255,255));
        g.drawRect((int)(x-handler.getCamera().getXoff()), (int)(y-handler.getCamera().getYoff()), width, height);
    }

	@Override
	public void onCollision(Entity e) {

	}
}