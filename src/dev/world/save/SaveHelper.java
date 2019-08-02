package dev.world.save;

import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import dev.entity.staticEntity.StaticEntity;
import dev.world.Sector;

public class SaveHelper implements Runnable {


	private Thread thread;
	private boolean running;

	private String name;

	private Sector sector;
	
	private JSONArray sectorList = new JSONArray();

	private boolean done = false;

	public SaveHelper(String name){
		this.name = name;
	}

	public synchronized void start() {
		if (running) {
			return;
		}
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop() {
		try (FileWriter file = new FileWriter("world/sectors/"+name+".json")) {
			file.write(sectorList.toJSONString());
			file.flush();
			System.out.println("["+name+"]\tSuccessfully saved");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("["+name+"]\tFailed to save");
		}
		if (!running) {
			return;
		}
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("["+name+"]\tTerminated");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		while(running) {
			if(sector != null && !done) {
				//save sector
				JSONObject sectorInfo = new JSONObject();
				sectorInfo.put("SectorX", sector.getSectorX());
				sectorInfo.put("SectorY", sector.getSectorY());
				JSONArray staticEntityList = new JSONArray();
				for(StaticEntity e:sector.getStaticEntityManager().getStaticEntities()) {
					JSONObject staticEntityInfo = new JSONObject();
					staticEntityInfo.put("entityX", e.getX());
					staticEntityInfo.put("entityY", e.getY());
					staticEntityInfo.put("entityID", e.getSpriteID());
					staticEntityList.add(staticEntityInfo);
				}
				sectorInfo.put("staticEntities", staticEntityList);
				JSONArray sectorTileInfo = new JSONArray();
				for(int y = 0;y < sector.getTileMap().length;y++) {
					for(int x = 0;x < sector.getTileMap()[y].length;x++) {
						JSONObject tileInfo = new JSONObject();
						tileInfo.put("tileX", x);
						tileInfo.put("tileY", y);
						tileInfo.put("tileID", sector.getTileMap()[y][x]);
						sectorTileInfo.add(tileInfo);
					}
				}
				sectorInfo.put("tiles", sectorTileInfo);
				sectorList.add(sectorInfo);
				System.out.println("["+name+"]\tParsed sector [X:"+sector.getSectorX()+" Y:"+sector.getSectorY()+"]");
				done = true;
			}
			if(sector == null) done = true;
		}
		stop();
	}

	public boolean isDone() {
		return done;
	}

	public void setSector(Sector sector) {
		this.sector = sector;
		this.done = false;
	}

}
