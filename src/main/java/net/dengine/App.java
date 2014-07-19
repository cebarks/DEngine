package net.dengine;

/**
 * Hello world!
 * 
 */
public class App {

	public static void main(String[] args) {
		DEngine engine = new DEngine();
		engine.createWindow(800, 600, 90, 0.001f, 10000f, 90);
		engine.start();
	}
}
