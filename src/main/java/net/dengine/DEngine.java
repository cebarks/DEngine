package net.dengine;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;

import java.io.File;

import net.dengine.vec.Vector3;
import net.dengine.world.Section;
import net.dengine.world.Wall;
import net.dengine.world.World;
import net.dengine.world.WorldSaveFile;
import net.dengine.world.entity.EntityPlayer;

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

	private World world;

	private int exitStatus;

	public static final Logger LOG = LogManager.getLogger(DEngine.class);

	public static DEngine INSTANCE;

	public DEngine() {
		INSTANCE = this;
	}

	public void run() {

		LOG.info("Attempting to create DEngine...");

		try {
			
			LOG.info("Creating display...");
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setTitle(title);			
			Display.setVSyncEnabled(true);		
			Display.create();
			Mouse.setGrabbed(true);
			
			LOG.info("Loading levels...");
			//if (new File("foo").exists())
				//world = loadWorld("foo");
			//else
				world = loadDefaultWorld();
			LOG.info("Creating levels...");
			//for(World w : worlds) w.create();
			world.create();

			LOG.info("Creating player...");
			world.setLocalPlayer(new EntityPlayer(world, "Tester"));

			LOG.info("Game has been successfully initialized.");
		} catch (Exception e) {
			exitOnError(0, e);
		}

		while (running) {
			update();
			render();
			Display.update();
			Display.sync(fps);
			if (Display.isCloseRequested())
				running = false;
		}

		LOG.info("Saving world...");
		WorldSaveFile saveFile = new WorldSaveFile(this, world);
		saveFile.saveWorld();
		LOG.info("World saved.");

		Display.destroy();
		System.exit(exitStatus);
	}

	private void update() {
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			running = false;
		}

		world.update();
	}

	private void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glClearColor(0.5f, 0.5f, 1f, 1f);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(fov, (float) width / (float) height, znear, zfar);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_DEPTH_TEST);
		glLoadIdentity();

		EntityPlayer player = world.getLocalPlayer();
		glRotatef(player.getRotation().y, 0, 1, 0);
		glTranslatef(player.getPosition().x, -player.getPosition().y, player.getPosition().z);

		world.render3D();

		//glPushMatrix();
		//glMatrixMode(GL_PROJECTION);
		//glLoadIdentity();
		//glOrtho(0, Display.getWidth(), 0, Display.getHeight(), -1, 1);
		//glMatrixMode(GL_MODELVIEW);
		//glLoadIdentity();

		//glBegin(GL_POINTS);
		//glColor3f(1, 1, 1);
		//glVertex2f(player.getPosition().x, -player.getPosition().z);
		//glEnd();

		//world.render2D();

		//glPopMatrix();
	}

	public void exit(int status) {
		LOG.fatal("Closing DEngine under status: " + status);
		running = false;
		exitStatus = status;
	}

	public void exitOnError(int status, Exception exception) {
		LOG.fatal("Closing DEngine with an error under status:", exception);
		running = false;
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

	public boolean isRunning() {
		return running;
	}

	public void start() {
		running = true;
		controlThread = new Thread(this);
		controlThread.start();
	}

	public World loadWorld(String file) {
		WorldSaveFile saveFile = new WorldSaveFile(this, new File(file));
		saveFile.loadWorld();
		return saveFile.getWorld();
	}

	public World loadDefaultWorld() {
		World world = new World(this, "foo");
		Section section = new Section(world);
		new Wall(section, new Vector3(-10, 0, -10), new Vector3(20, 0, -40), new Vector3(1, 1, 1), "res/textures/test.png", 25);
		new Wall(section, new Vector3(40, 0, -40), new Vector3(10, 0, -10), new Vector3(1, 1, 1), "res/textures/test.png", 25);
		
		new Wall(section, new Vector3(10, 0, -10), new Vector3(40, 0, -10), new Vector3(1, 1, 1), "res/textures/test.png", 25);
		new Wall(section, new Vector3(40, 0, -10), new Vector3(40, 0, 40), new Vector3(1, 1, 1), "res/textures/test.png", 25);
		new Wall(section, new Vector3(-40, 0, -10), new Vector3(-10, 0, -10), new Vector3(1, 1, 1), "res/textures/test.png", 25);
		new Wall(section, new Vector3(-40, 0, 40), new Vector3(-40, 0, -10), new Vector3(1, 1, 1), "res/textures/test.png", 25);
		new Wall(section, new Vector3(40, 0, 40), new Vector3(-40, 0, 40), new Vector3(1, 1, 1), "res/textures/test.png", 25);
		
		
		world.addSection(section);
		return world;
	}

	public World getWorld() {
		return world;
	}
}
