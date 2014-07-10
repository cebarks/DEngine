package net.dengine.world;

import java.util.ArrayList;
import java.util.List;

import net.dengine.world.entity.Entity;

public class Section {

	private World world;

	private List<Entity> entities;
	private List<Wall> walls;

	private final int id;

	public Section(World world) {
		this.world = world;

		entities = new ArrayList<Entity>();
		walls = new ArrayList<Wall>();

		id = world.getNextEntityID();
	}

	public void create() {
		// TODO Generate section
	}

	public void render3D() {
		for (Wall w : walls)
			w.render3D();
	}

	public void render2D() {
		for (Wall w : walls)
			w.render2D();
	}

	public World getWorld() {
		return world;
	}

	public void addEntity(Entity entity) {
		entities.add(entity);
	}

	public void addWall(Wall wall) {
		walls.add(wall);
	}
}
