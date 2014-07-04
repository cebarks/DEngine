package net.dengine;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class DEngine implements Runnable {
	//If the engine is running
	private boolean running = false;
	
	//Window title
	private String title = "DEngine demo";

	//Window dimensions
	private int width = 800, height = 600, fps = 60;

	// Field of view, near clipping distance, far clipping distance
	@SuppressWarnings("unused")
	private float fov = 90, znear = 0.001f, zfar = 1000;

	private Thread controlThread;

	private int exitStatus;

	// private List<Level> levels = new ArrayList<Level>();

	public static final Logger LOG = LogManager.getLogger(DEngine.class);

	public DEngine() {
		LOG.info("Attempting to create DEngine...");

		try {

			LOG.info("Loading levels...");
			// levels = loadLevels("null");
			LOG.info("Creating levels...");
			// for(Level l : levels) l.create();

			LOG.info("Creating display...");
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setTitle(title);
			Display.create();

			LOG.info("Game has been successfully initialized.");

		} catch (Exception e) {
			closeOnError(0, e);
		}

		running = true;
		LOG.info("DEngine is running...");
	}

	/**
	 * The main loop for DEngine to run
	 */
	public void run() {
		while (running) {
			// Display.update();
			Display.sync(fps);

			if (Display.isCloseRequested())
				running = false;
		}

		Display.destroy();
		System.exit(exitStatus);
	}

	/**
	 * Closing method
	 */
	public void close(int status) {
		LOG.info("Closing DEngine under status: " + status);
		running = false;
		// TODO this is where it would go through and destroy all the levels and
		// variables, and other rendering stuff
		exitStatus = status;
	}

	/**
	 * This is for when you are bug testing and you need to end the game and
	 * print the error log
	 */
	public void closeOnError(int status, Exception exception) {
		LOG.info("Closing DEngine with an error under status: " + status);
		running = false;
		exception.printStackTrace();
		// TODO this is where it would go through and destroy all the levels and
		// variables, and other rendering stuff
		exitStatus = status;
	}

	/**
	 * Create a customized window
	 */
	public void createWindow(int width, int height, int fps, float znear, float zfar, float fov) {
		this.width = width;
		this.height = height;
		this.fps = fps;
		this.znear = znear;
		this.zfar = zfar;
		this.fov = fov;
	}

	// TODO THIS IS WHERE YOU COME IN
	private List<Level> loadLevels(String file) {
		// TODO This is where we get levels
		List<Level> levels = new ArrayList<Level>();

		return levels;
	}

	/**
	 * Returns if DEngine is running
	 * 
	 * @return whether or not DEngine is running
	 */
	public boolean isRunning() {
		return running;
	}

	public void start() {
		running = true;
		controlThread = new Thread(this);
		controlThread.start();
	}
}
