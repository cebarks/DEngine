package net.dengine.world.entity;

import net.dengine.world.World;

public class Entity {

	public float x;
	public float y;
	public float z;

	private World world;

	private final int id;

	public Entity(World world, float x, float y, float z) {
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.id = world.getNextEntityID();

		world.addEntity(this);
	}

	public Entity(World world) {
		this(world, 0F, 0F, 0F);
	}

	public int getID() {
		return id;
	}
}
