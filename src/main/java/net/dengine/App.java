package net.dengine;

/**
 * Hello world!
 *
 */
public class App {
	
    public static void main( String[] args ) {
    	
        DEngine.create();
        
        while(DEngine.isRunning()) {
        	
        	DEngine.run();
        	
        }
        
        DEngine.close(0);
        
    }
    
}
