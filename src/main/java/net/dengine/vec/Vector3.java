package net.dengine.vec;

public class Vector3 {

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
	}

	/**
	 * Constructor for an empty vector: x, y, and z are set to 0.
	 */

	public Vector3() {
		x = 0f;
		y = 0f;
		z = 0f;
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
}
