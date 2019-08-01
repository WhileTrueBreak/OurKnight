package dev.entity.staticEntity;

import java.awt.Color;
import java.awt.Graphics;

import dev.Handler;
import dev.entity.Entity;

public class Wall extends StaticEntity{

    public Wall(Handler handler, int x, int y, int width, int height, int spriteID) {
        super(handler, x, y, width, height, spriteID);
        entityType = "wall";
        isSolid = true;
    }
    
    @Override
    public void update() {
    	
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(0,0,200));
        g.fillRect((int)(x-handler.getCamera().getXoff()), (int)(y-handler.getCamera().getYoff()), width, height);
//      g.setColor(new Color(0,255,255));
//      g.drawRect((int)(x-handler.getCamera().getXoff()), (int)(y-handler.getCamera().getYoff()), width, height);
//      g.setColor(new Color(0, 0, 255));
//		g.drawRect((int)(hitbox.x-handler.getCamera().getXoff()), (int)(hitbox.y-handler.getCamera().getYoff()), hitbox.width, hitbox.height);
    }

	@Override
	public void onCollision(Entity e) {
		
	}

}