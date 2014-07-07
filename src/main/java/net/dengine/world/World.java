package net.dengine.world;

import java.util.List;

import net.dengine.DEngine;
import net.dengine.world.entity.Entity;

public class World {
	private DEngine engine;

	private List<Entity> entities; 
	private int idIndex;

	public World(DEngine engine, String name) {
		this.engine = engine;
		idIndex = 0;
	}

	public int getNextEntityID() {
		return idIndex++;
	}

	public DEngine getEngine() {
		return engine;
	}

	public void addEntity(Entity entity) {
		entities.add(entity);
	}
}
