package net.dengine.world;

import net.dengine.vec.Vector3;

public class Wall {

	public final Vector3 v1;
	public final Vector3 v2;
	public final float height;

	public Wall(Vector3 v1, Vector3 v2, float height) {
		this.v1 = v1;
		this.v2 = v2;
		this.height = height;
	}

}
