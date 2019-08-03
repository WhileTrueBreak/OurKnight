package dev.world.save;

import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import dev.entity.staticEntity.StaticEntity;
import dev.world.Sector;

public class SaveHelper implements Runnable {


	private Thread thread = new Thread(this);
	private boolean running;

	private Sector sector;

	private JSONArray sectorList = new JSONArray();

	private boolean done = false;

	public SaveHelper(String name){
		thread.setName(name);
	}

	public synchronized void start() {
		if (running) {
			return;
		}
		running = true;
		thread.start();
	}

	public synchronized void stop() {
		if (!running) {
			System.out.println("["+thread.getName()+"]\tAlready stopped");
			return;
		}
		try (FileWriter file = new FileWriter("world/sectors/"+thread.getName()+".json")) {
			file.write(sectorList.toJSONString());
			file.flush();
			System.out.println("["+thread.getName()+"]\tSuccessfully saved");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("["+thread.getName()+"]\tFailed to save");
		}
		running = false;
		System.out.println("["+thread.getName()+"]\tTerminated");
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
				System.out.println("["+thread.getName()+"]\tParsed sector [X:"+sector.getSectorX()+" Y:"+sector.getSectorY()+"]");
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
