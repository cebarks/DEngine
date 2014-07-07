package net.dengine.world;

import org.newdawn.slick.util.Log;

import net.dengine.vec.Vector3;
import static org.lwjgl.opengl.GL11.*;

public class Wall {

	public final Vector3 start, end;
	
	private final int id;
	
	public Vector3 normal, center;
	
	public float height;
	
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
		glVertex2f(start.x, start.y);
		glVertex2f(end.x, end.y);
		glVertex2f(center.x, center.y);
		glVertex2f(center.x + (normal.x * 5), center.y + (normal.y * 5));
		glEnd();	
	}
	
	public void render3D() {
		glBegin(GL_QUADS);
		glColor3f(1, 0, 0);
		glVertex3f(start.x, 0, -start.y);
		glColor3f(1, 1, 0);
		glVertex3f(start.x, height, -start.y);
		glVertex3f(end.x, height, -end.y);
		glVertex3f(end.x, 0, -end.y);
		glEnd();	
		
		glBegin(GL_LINES);
		glVertex3f(center.x, height / 2, -center.y);
		glVertex3f(center.x + (normal.x * 5), height / 2, -(center.y + (normal.y * 5)));
		glEnd();
	}
	
	private void normalize() {
		center = new Vector3((end.x + start.x) / 2,(end.y + start.y) / 2, 0);
		Vector3 directing = start.sub(end);
		Vector3 flipped = new Vector3(-directing.y, directing.x, directing.z);
		Vector3 unit = new Vector3(flipped.x / flipped.length, flipped.y / flipped.length, 0);
		normal = unit;
		Log.info(" Creating a wall with normal: X: " + normal.x + ". Y: " + normal.y + ". Z:" + normal.z );
	}
}
