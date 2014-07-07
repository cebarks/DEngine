package net.dengine;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;

import java.util.ArrayList;
import java.util.List;

import net.dengine.vec.Vector3;
import net.dengine.world.Wall;
import net.dengine.world.World;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class DEngine implements Runnable {

	private boolean running = false;
	
	private String title = "DEngine demo";

	private int width = 800, height = 600, fps = 60;

	private float fov = 90, znear = 0.001f, zfar = 1000;

	private Thread controlThread;

	private int exitStatus;

	private List<World> worlds = new ArrayList<World>();
	
	private Vector3 position, rotation;

	public static final Logger LOG = LogManager.getLogger(DEngine.class);

	public void run() {
		
		LOG.info("Attempting to create DEngine...");

		try {

			LOG.info("Loading levels...");
			worlds = loadWorlds("null");
			LOG.info("Creating levels...");
			//for(World w : worlds) w.create();

			LOG.info("Creating display...");
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setTitle(title);
			Display.create();
			Mouse.setGrabbed(true);
			
			LOG.info("Creating player...");
			position = new Vector3(0, 15, 0);
			rotation = new Vector3(0, 0, 0);

			LOG.info("Game has been successfully initialized.");

		} catch (Exception e) {
			closeOnError(0, e);
		}
		
		while (running) {
			render();
			translate();
			Display.update();
			Display.sync(fps);
			if (Display.isCloseRequested())
				running = false;
		}

		Display.destroy();
		System.exit(exitStatus);
	}
	
	private void translate() {
		
		LOG.info("Player rotation Y: " + rotation.y + ". Player position X: " + position.x + ". Player position Z: " + position.z);
		
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			
			running = false;
			
		}
		
		rotation.y += Mouse.getDX() / 2;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
			
			position.x += Math.sin(rotation.y*Math.PI/180)*0.5f;
			position.z += Math.cos(rotation.y*Math.PI/180)*0.5f;
			
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
			
			position.x += Math.sin((rotation.y + 180)*Math.PI/180)*0.5f;
			position.z += Math.cos((rotation.y + 180)*Math.PI/180)*0.5f;
			
		}
	}

	private void render() {
		
		
		
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glClearColor(0.5f, 0.5f, 1f, 1f);
		glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective(fov, (float) width / (float) height, znear, zfar);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        
        glRotatef(rotation.y, 0, 1, 0);
		glTranslatef(-position.x, -position.y, position.z);		
        
		for(World w : worlds)
			w.render3D();
	
		glPushMatrix();
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, Display.getWidth(), 0, Display.getHeight(), -1, 1);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		
		glBegin(GL_POINTS);
		glColor3f(1, 1, 1);
		glVertex2f(position.x, position.z);
		glEnd();
		
		for(World w : worlds)
			w.render2D();
		
		glPopMatrix();
	}
	
	public void close(int status) {
		LOG.info("Closing DEngine under status: " + status);
		running = false;
		exitStatus = status;
	}

	
	
	public void closeOnError(int status, Exception exception) {
		LOG.info("Closing DEngine with an error under status: " + status);
		running = false;
		exception.printStackTrace();
		exitStatus = status;
	}


	
	public void createWindow(int width, int height, int fps, float znear, float zfar, float fov) {
		this.width = width;
		this.height = height;
		this.fps = fps;
		this.znear = znear;
		this.zfar = zfar;
		this.fov = fov;
	}


	private List<World> loadWorlds(String file) {
		List<World> levels = new ArrayList<World>();
		World world = new World(this, "foo");
		new Wall(world, new Vector3(0, 50, 0), new Vector3(50, 100, 0), 30);
		levels.add(world);
		return levels;
	}


	public boolean isRunning() {
		return running;
	}

	public void start() {
		running = true;
		controlThread = new Thread(this);
		controlThread.start();
	}
}
