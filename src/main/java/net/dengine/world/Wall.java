package net.dengine.world;

import net.dengine.vec.Line;
import net.dengine.vec.Vector;

public class Wall {
	
	public Line line;
	
	public Wall(Vector v1, Vector v2) {
		
		line = new Line(v1, v2);
		
	}
	
	public Wall(Line line) {
		
		this.line = line;
		
	}

}
