package net.dengine.world;

import static org.lwjgl.opengl.GL11.GL_COLOR_ARRAY;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_COORD_ARRAY;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;
import static org.lwjgl.opengl.GL11.glColorPointer;
import static org.lwjgl.opengl.GL11.glDisableClientState;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnableClientState;
import static org.lwjgl.opengl.GL11.glTexCoordPointer;
import static org.lwjgl.opengl.GL11.glVertexPointer;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;

public class Section {

	private World world;

	private List<Wall> walls;

	private final int id;

	public FloatBuffer vertexBuffer, colorBuffer, textureBuffer;

	public Section(World world) {
		this.world = world;

		walls = new ArrayList<Wall>();

		id = world.getNextEntityID();
	}

	public void create() {
		vertexBuffer = BufferUtils.createFloatBuffer((18 * walls.size()));
		colorBuffer = BufferUtils.createFloatBuffer(18 * walls.size());
		textureBuffer = BufferUtils.createFloatBuffer(12 * walls.size());
		{
			for (Wall w : walls) {

				w.create();

				vertexBuffer.put(w.start.x);
				vertexBuffer.put(w.start.y + w.height);
				vertexBuffer.put(w.start.z); // x y z
				vertexBuffer.put(w.start.x);
				vertexBuffer.put(w.start.y);
				vertexBuffer.put(w.start.z); // x y z
				vertexBuffer.put(w.end.x);
				vertexBuffer.put(w.end.y);
				vertexBuffer.put(w.end.z); // x y z
				vertexBuffer.put(w.end.x);
				vertexBuffer.put(w.end.y);
				vertexBuffer.put(w.end.z); // x y z
				vertexBuffer.put(w.end.x);
				vertexBuffer.put(w.end.y + w.height);
				vertexBuffer.put(w.end.z); // x y z
				vertexBuffer.put(w.start.x);
				vertexBuffer.put(w.start.y + w.height);
				vertexBuffer.put(w.start.z); // x y z

				colorBuffer.put(w.color.x);
				colorBuffer.put(w.color.y);
				colorBuffer.put(w.color.z); // r g b a
				colorBuffer.put(w.color.x);
				colorBuffer.put(w.color.y);
				colorBuffer.put(w.color.z); // r g b a
				colorBuffer.put(w.color.x);
				colorBuffer.put(w.color.y);
				colorBuffer.put(w.color.z); // r g b a
				colorBuffer.put(w.color.x);
				colorBuffer.put(w.color.y);
				colorBuffer.put(w.color.z); // r g b a
				colorBuffer.put(w.color.x);
				colorBuffer.put(w.color.y);
				colorBuffer.put(w.color.z); // r g b a
				colorBuffer.put(w.color.x);
				colorBuffer.put(w.color.y);
				colorBuffer.put(w.color.z); // r g b a

				textureBuffer.put(0);
				textureBuffer.put(0); // u v
				textureBuffer.put(0);
				textureBuffer.put(1 * (w.getHeight() / 16)); // u v
				textureBuffer.put(1 * (w.getLength() / 16));
				textureBuffer.put(1 * (w.getHeight() / 16)); // u v
				textureBuffer.put(1 * (w.getLength() / 16));
				textureBuffer.put(1 * (w.getHeight() / 16)); // u v
				textureBuffer.put(1 * (w.getLength() / 16));
				textureBuffer.put(0); // u v
				textureBuffer.put(0);
				textureBuffer.put(0); // u v
			}

			vertexBuffer.flip();
			colorBuffer.flip();
			textureBuffer.flip();
		}
	}

	public void render3D() {

		IntBuffer buffer = BufferUtils.createIntBuffer(3);
		glGenBuffers(buffer);
		int vertexHandle = buffer.get(0);
		int colorHandle = buffer.get(2);
		int textureHandle = buffer.get(1);

		// vertices
		glBindBuffer(GL_ARRAY_BUFFER, vertexHandle);
		glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);
		glVertexPointer(3, GL_FLOAT, 0, 0L);

		// colors
		glBindBuffer(GL_ARRAY_BUFFER, colorHandle);
		glBufferData(GL_ARRAY_BUFFER, colorBuffer, GL_STATIC_DRAW);
		glColorPointer(3, GL_FLOAT, 0, 0L);

		// texCoords
		glBindBuffer(GL_ARRAY_BUFFER, textureHandle);
		glBufferData(GL_ARRAY_BUFFER, textureBuffer, GL_STATIC_DRAW);
		glTexCoordPointer(2, GL_FLOAT, 0, 0);

		// unbind VBO
		glBindBuffer(GL_ARRAY_BUFFER, 0);

		glEnableClientState(GL_VERTEX_ARRAY);
		glEnableClientState(GL_TEXTURE_COORD_ARRAY);
		glEnableClientState(GL_COLOR_ARRAY);

		glDrawArrays(GL_TRIANGLES, 0, walls.size() * 6);

		glDisableClientState(GL_COLOR_ARRAY);
		glDisableClientState(GL_TEXTURE_COORD_ARRAY);
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
