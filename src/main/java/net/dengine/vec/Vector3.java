package net.dengine.vec;

import java.io.Serializable;

public class Vector3 implements Serializable {
	private static final long serialVersionUID = -3369221176499069534L;

	/**
	 * The x component of the vector.
	 */
	public float x;
	/**
	 * The y component of the vector.
	 */
	public float y;
	/**
	 * The z component of the vector.
	 */
	public float z;
	/**
	 * The length component of the vector.
	 */
	public float length;

	/**
	 * Constructor for a 3D vector.
	 *
	 * @param x
	 *            the x coordinate.
	 * @param y
	 *            the y coordinate.
	 * @param z_
	 *            the y coordinate.
	 */
	public Vector3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;

		length = length();
	}

	public Vector3 add(Vector3 v) {
		return new Vector3(x + v.x, y + v.y, z + v.z);
	}

	public Vector3 sub(Vector3 v) {
		return new Vector3(x - v.x, y - v.y, z - v.z);
	}

	public Vector3 mult(Vector3 v) {
		return new Vector3(x * v.x, y * v.y, z * v.z);
	}

	public Vector3 div(Vector3 v) {
		return new Vector3(x / v.x, y / v.y, z / v.z);
	}

	public float length() {
		return (float) Math.sqrt((x * x) + (y * y) + (z * z));
	}

	public void normalize() {
		float m = magnitude();

		x /= m;
		y /= m;
		z /= m;
	}

	public float magnitude() {
		return (float) Math.sqrt((x * x) + (y * y) + (z * z));
	}

	public Vector3 cross(Vector3 v) {
		float crossX = y * v.z - v.y * z;
		float crossY = z * v.x - v.z * x;
		float crossZ = x * v.y - v.x * y;
		return new Vector3(crossX, crossY, crossZ);
	}

	public Vector3 invert() {
		return new Vector3(z, -y, x);
	}
}
