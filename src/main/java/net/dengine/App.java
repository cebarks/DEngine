package net.dengine;

/**
 * Hello world!
 * 
 */
public class App {
	
	public DEngine engine = new DEngine();
	
    public static void main(String[] args) {
    	
        new App();
        
    }
    
    public App() {
    	
    	engine.create();
        
        while(engine.isRunning()) {
        	
        	engine.run();
        	
        }
        
        engine.close(0);
    	
    }
    
}
