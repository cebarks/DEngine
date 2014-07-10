package net.dengine.world;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.opengl.GL11.glVertex3f;
import net.dengine.vec.Vector3;

import static net.dengine.DEngine.LOG;

public class Wall {

	public final Vector3 start, end;

	private final int id;

	public Vector3 normal, center;

	public final float height;

	private Section section;

	public Wall(Section section, Vector3 start, Vector3 end, float height) {
		this.section = section;
		this.start = start;
		this.end = end;
		this.height = height;
		this.id = section.getWorld().getNextEntityID();

		section.addWall(this);
		normalize();
	}

	public void render2D() {
		glBegin(GL_LINES);
		glColor3f(1, 1, 1);
		glVertex2f(start.x, start.z);
		glVertex2f(end.x, end.z);
		glVertex2f(center.x, center.z);
		glVertex2f(center.x + (normal.x * 5), center.z + (normal.z * 5));
		glEnd();
	}

	public void render3D() {
		glBegin(GL_QUADS);
		glColor3f(.5f, .5f, .5f);
		glVertex3f(start.x, start.y, start.z);
		glVertex3f(start.x, start.y + height, start.z);
		glVertex3f(end.x, end.y + height, end.z);
		glVertex3f(end.x, end.y, end.z);
		glEnd();

		glBegin(GL_LINES);
		glColor3f(0, 0, 0);
		glVertex3f(start.x, start.y, start.z);
		glVertex3f(end.x, end.y + height, end.z);
		glVertex3f(start.x, start.y + height, start.z);
		glVertex3f(end.x, end.y, end.z);
		glEnd();

		glBegin(GL_LINES);
		glVertex3f(center.x, center.y, center.z);
		glVertex3f(center.x + (normal.x * 5), center.y + (normal.y * 5), center.z + (normal.z * 5));
		glEnd();
	}

	private void normalize() {
		center = new Vector3((end.x + start.x) / 2, ((start.y + end.y) / 2) + (height / 2), (end.z + start.z) / 2);
		LOG.info("Creating a wall with center: X: " + center.x + ". Y: " + center.y + ". Z:" + center.z);
		Vector3 directing = start.sub(end);
		Vector3 flipped = directing.invert();
		normal = new Vector3(flipped.x / flipped.length, flipped.y / flipped.length, flipped.z / flipped.length);
		LOG.info("Creating a wall with normal: X: " + normal.x + ". Y: " + normal.y + ". Z:" + normal.z);
	}
}
