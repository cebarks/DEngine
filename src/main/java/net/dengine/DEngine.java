package net.dengine;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class DEngine {
	
	//If the engine is running
	private boolean running = false;
	
	//Window title
	private String title = "DEngine demo";

	//Window dimensions
	private int width = 800, height = 600, fps = 60;
	
	//Field of view, near clipping distance, far clipping distance
	@SuppressWarnings("unused")
	private float fov = 90, znear = 0.001f, zfar = 1000;
	
	//private List<Level> levels = new ArrayList<Level>();

	
	/**
	 * Creation method. If you would like a customized window, call createWindow()
	 */
	
	public void create() {
		
		say("Attempting to create DEngine...");
		
		try {
			
			say("Loading levels...");
			//levels = loadLevels("null");
			say("Creating levels...");
			//for(Level l : levels) l.create();	
			
			say("Creating display...");
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setTitle(title);
			Display.create();
			
			say("Game has been successfully initialized.");
			
			
		}catch(Exception e) {
			
			closeOnError(0, e);
			
		}
		
		running = true;
		say("DEngine is running...");
		
	}
	
	
	
	/**
	 * The main loop for DEngine to run
	 */
	public void run() {
		
		Display.update();
		Display.sync(fps);
		
		if(Display.isCloseRequested()) running = false;
		
	}
	
	
	
	/**
	 * Closing method
	 */
	public void close(int status) {
		
		say("Closing DEngine under status: " + status);
		running = false;
		//TODO this is where it would go through and destroy all the levels and variables, and other rendering stuff
		Display.destroy();
		System.exit(status);
		
	}
	
	
	
	/** 
	 * This is for when you are bug testing and you need to end the game and print the error log
	 */
	public void closeOnError(int status, Exception exception) {
			
		say("Closing DEngine with an error under status: " + status);
		running = false;
		exception.printStackTrace();
		//TODO this is where it would go through and destroy all the levels and variables, and other rendering stuff
		Display.destroy();
		System.exit(status);
			
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
	
	
	
	//TODO THIS IS WHERE YOU COME IN
	private List<Level> loadLevels(String file) {
		
		//TODO This is where we get levels
		List<Level> levels = new ArrayList<Level>();
		
		return levels;
		
	}
	
	
	
	/**
	 * Returns if DEngine is running
	 * @return
	 */
	public boolean isRunning() {
		
		return running;
		
	}
	
	
	
	/**
	 * Prints a string
	 */
	public void say(String message) {
		
		System.out.println(message);
		
	}

}
