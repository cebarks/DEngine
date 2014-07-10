package net.dengine.world;

import java.util.ArrayList;
import java.util.List;

public class Section {

	private World world;

	private List<Wall> walls;

	private final int id;

	public Section(World world) {
		this.world = world;

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

	public void addWall(Wall wall) {
		walls.add(wall);
	}

	public List<Wall> getWalls() {
		return walls;
	}
}
