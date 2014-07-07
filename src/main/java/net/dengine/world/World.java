package net.dengine.world;

import java.util.ArrayList;
import java.util.List;

import net.dengine.DEngine;
import net.dengine.world.entity.Entity;

public class World {
	private final DEngine engine;

	private List<Entity> entities;
	private List<Wall> walls;
	private int idIndex;

	public World(DEngine engine, String name) {
		this.engine = engine;

		idIndex = 0;

		walls = new ArrayList<Wall>();
		entities = new ArrayList<Entity>();
	}

	public void render3D() {
		for (Wall w : walls)
			w.render3D();
		for (Entity e : entities)
			e.render3D();
	}

	public void render2D() {
		for (Wall w : walls)
			w.render2D();
		for (Entity e : entities)
			e.render2D();
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

	public void addWall(Wall wall) {
		walls.add(wall);
	}

	public Entity getEntity(int index) {
		return entities.get(index);
	}
	
	public Wall getWall(int index) {
		return walls.get(index);
	}

	public void update() {
		for (Entity e : entities)
			e.update();
	}
}
