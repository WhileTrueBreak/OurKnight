package dev.entity.creature;

import java.awt.Rectangle;
import java.util.ArrayList;

import dev.Handler;
import dev.entity.Entity;
import dev.entity.staticEntity.StaticEntity;
import dev.world.Sector;
import dev.world.World;

public abstract class Creature extends Entity{
	
	public Creature(Handler handler, int x, int y) {
		super(handler, x, y);
	}
	protected int health = 20;
	protected int speed = 20;
	
	protected int width = 32, height = 32;
	
	protected Rectangle collided() {
		ArrayList<StaticEntity>staticEntities = new ArrayList<StaticEntity>();
		
		int secX = (int)(x/Sector.SECTOR_PIXEL_WIDTH), secY = (int)(y/Sector.SECTOR_PIXEL_HEIGHT);
		
		for(int x = secX-1;x < secX+2;x++) {
			for(int y = secY-1;y < secY+2;y++) {
				if(x < 0 || y < 0 || x >= World.WORLD_SECTOR_WIDTH || y >= World.WORLD_SECTOR_HEIGHT)
					continue;
				staticEntities.addAll(handler.getWorld().getSectorManager().getSector(x, y).getStaticEntityManager().getStaticEntities());
			}
		}
		
		for (StaticEntity e:staticEntities) {
			if(Math.hypot(x-e.getX(), y-e.getY()) > 1000 || !e.isSolid()) {
				continue;
			}
			Rectangle b1 = getHitbox().getBounds(), b2 = e.getHitbox().getBounds();
			b1.x = (int) x;
			b1.y = (int) y;
			b2.x = (int) e.getX();
			b2.y = (int) e.getY();
			if (b1.intersects(b2)) {
				e.onCollision(this);
				return b2;
			}else {
				continue;
			}
		}
		return null;
	}
	
}
