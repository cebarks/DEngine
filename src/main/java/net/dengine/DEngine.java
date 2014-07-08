package net.dengine;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;
import net.dengine.vec.Vector3;
import net.dengine.world.Section;
import net.dengine.world.Wall;
import net.dengine.world.World;
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

	public void run() {

		LOG.info("Attempting to create DEngine...");

		try {

			LOG.info("Loading levels...");
			world = loadWorld("null");
			LOG.info("Creating levels...");
			// for(World w : worlds) w.create();

			LOG.info("Creating display...");
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setTitle(title);
			Display.create();
			Mouse.setGrabbed(true);

			LOG.info("Creating player...");
			world.setLocalPlayer(new EntityPlayer(world, "Tester"));

			LOG.info("Game has been successfully initialized.");

		} catch (Exception e) {
			closeOnError(0, e);
		}

		while (running) {
			update();
			render();
			Display.update();
			Display.sync(fps);
			if (Display.isCloseRequested())
				running = false;
		}

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
		glLoadIdentity();

		EntityPlayer player = (EntityPlayer) world.getEntity(0);
		glRotatef(player.getRotation().y, 0, 1, 0);
		glTranslatef(-player.getPosition().x, -player.getPosition().y, player.getPosition().z);

		world.render3D();

		glPushMatrix();
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, Display.getWidth(), 0, Display.getHeight(), -1, 1);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

		glBegin(GL_POINTS);
		glColor3f(1, 1, 1);
		glVertex2f(player.getPosition().x, player.getPosition().z);
		glEnd();

		world.render2D();

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

	private World loadWorld(String file) {
		World world = new World(this, "foo");
		Section section = new Section(world);
		new Wall(section, new Vector3(0, 50, 0), new Vector3(50, 100, 0), 30);
		world.addSection(section);
		return world;
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
