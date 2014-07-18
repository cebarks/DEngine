package net.dengine.world;

import static net.dengine.DEngine.LOG;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.io.IOException;

import net.dengine.vec.Vector3;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Wall {

	public final Vector3 start;
	public final Vector3 end;
	public Vector3 normal;
	public Vector3 center;
	public Vector3 color;
	public Texture texture;
	
	private final int id;

	public final float height;

	private final Section section;

	private String textureLocation;
	
	private float length;
	
	public Wall(Section section, Vector3 start, Vector3 end, Vector3 color, String textureLocation, float height) {
		this.section = section;
		this.start = start;
		this.end = end;
		this.color = color;
		this.height = height;
		this.textureLocation = textureLocation;
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
	
	public void create() {
		try {
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(textureLocation));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void normalize() {
		length = (float) Math.sqrt(((end.x - start.x)*(end.x - start.x)) + ((end.z - start.z)*(end.z - start.z)));
		center = new Vector3((end.x + start.x) / 2, ((start.y + end.y) / 2) + (height / 2), (end.z + start.z) / 2);
		LOG.info("Creating a wall with center: X: " + center.x + ". Y: " + center.y + ". Z:" + center.z);
		Vector3 directing = start.sub(end);
		Vector3 flipped = directing.invert();
		normal = new Vector3(flipped.x / flipped.length, flipped.y / flipped.length, -flipped.z / flipped.length);
		LOG.info("Creating a wall with normal: X: " + normal.x + ". Y: " + normal.y + ". Z:" + normal.z);
	}
	
	public float getLength() {
		return length;
	}
}
