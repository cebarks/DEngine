package net.dengine.world;

import java.util.ArrayList;
import java.util.List;

import net.dengine.DEngine;
import net.dengine.world.entity.Entity;

public class World {
	private DEngine engine;

	private List<Entity> entities; 
	private List<Wall> walls;
	private int idIndex;

	public World(DEngine engine, String name) {
		this.engine = engine;
		idIndex = 0;
	}
	
	public void render3D() {
		for(Wall w : walls) 
			w.render3D();
	}
	
	public void render2D() {
		for(Wall w : walls) 
			w.render2D();
	}

	public int getNextEntityID() {
		return idIndex++;
	}

	public DEngine getEngine() {
		return engine;
	}

	public void addEntity(Entity entity) {
		if(entities == null)
			entities = new ArrayList<Entity>();
		
		entities.add(entity);
	}
	
	public void addWall(Wall wall) {
		if(walls == null)
			walls = new ArrayList<Wall>();
		
		walls.add(wall);
	}
}
