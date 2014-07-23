package net.dengine.world.entity;

import net.dengine.vec.Vector3;
import net.dengine.world.World;

public class EntityLiving extends Entity {

	protected Vector3 position;
	protected Vector3 rotation;
	public float speed;
	public boolean walking;

	public EntityLiving(World world) {
		super(world);
		position = new Vector3(0, 15, 0);
		rotation = new Vector3(0, 0, 0);
	}

	public Vector3 getPosition() {
		return position;
	}

	public void setPosition(Vector3 position) {
		this.position = position;
	}

	public Vector3 getRotation() {
		return rotation;
	}

	public void setRotation(Vector3 rotation) {
		this.rotation = rotation;
	}
}
