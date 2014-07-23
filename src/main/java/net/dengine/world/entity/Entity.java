package net.dengine.world.entity;

import net.dengine.vec.Vector3;
import net.dengine.world.World;

public class Entity {

	public float x;
	public float y;
	public float z;

	protected Vector3 previous;
	protected Vector3 normal;

	protected final World world;

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

	public void update() {

	}

	public int getID() {
		return id;
	}

	public void render3D() {

	}

	public void render2D() {

	}

	public World getWorld() {
		return world;
	}

	protected float distanceBetween(Vector3 a, Vector3 b) {
		return (float) Math.sqrt(((a.x - b.x) * (a.x - b.x)) + ((a.z - b.z) * (a.z - b.z)));
	}

	public static float distanceBetweenPointToLine(Vector3 A, Vector3 B, Vector3 P) {
		float normalLength = (float) Math.sqrt((B.x - A.x) * (B.x - A.x) + (B.z - A.z) * (B.z - A.z));
		return Math.abs((P.x - A.x) * (B.z - A.z) - (P.z - A.z) * (B.x - A.x)) / normalLength;
	}
}
