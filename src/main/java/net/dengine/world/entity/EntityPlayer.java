package net.dengine.world.entity;

import net.dengine.vec.Vector3;
import net.dengine.world.Section;
import net.dengine.world.Wall;
import net.dengine.world.World;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class EntityPlayer extends EntityLiving {

	private String name;
	private Vector3 previous;
	private Vector3 normal;
	
	public float speed, tick, yBob;
	public boolean walking;
	
	public EntityPlayer(World world, String name) {
		super(world);
		this.name = name;
		previous = position;
		normal = new Vector3(0, 0, 1);
	}

	@Override
	public void update() {
		Display.setTitle(String.format("DEngine demo | x:%f y:%f z:%f", position.x, position.y, position.z));
		collision();
	}
	
	private void collision() {
		for(Section s : world.getSections()) {
			for(Wall w : s.getWalls()) {
				float previousDistance = distanceBetweenPointToLine(new Vector3(-w.start.x, w.start.y, -w.start.z), new Vector3(-w.end.x, w.end.y, -w.end.z), previous);
				float positionDistance = distanceBetweenPointToLine(new Vector3(-w.start.x, w.start.y, -w.start.z), new Vector3(-w.end.x, w.end.y, -w.end.z), position);
				float delta = Math.abs(previousDistance - positionDistance);
				if(distanceBetween(position.negative(), w.start) + distanceBetween(position.negative(), w.end) - 0.5f < w.getLength()) {
					position = new Vector3(position.x - (delta * w.normal.x), position.y - (delta * w.normal.y), position.z - (delta * w.normal.z));  
				}
			}
		}
	}

	public void inputUpdate() {
		
		previous = new Vector3(position.x, position.y, position.z);
		speed = 0.5f; 
		
		if(yBob < 0) yBob = 0;
		if(tick < 0) tick = 0;
		
		tick += 0.15f;
		
		yBob = (float) Math.sin(tick) / 8;
		
		if(walking) position.y += yBob;

		rotation.y += Mouse.getDX() / 2;

		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			walking = true;
			position.x -= Math.sin(rotation.y * Math.PI / 180) * speed;
			position.z += Math.cos(rotation.y * Math.PI / 180) * speed;
		}else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			walking = true;
			position.x -= Math.sin((rotation.y + 180) * Math.PI / 180) * speed;
			position.z += Math.cos((rotation.y + 180) * Math.PI / 180) * speed;
		}else {
			walking = false;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			position.y += .5f;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			position.y -= .5f;
		}
	
	}
	
	private float distanceBetween(Vector3 a, Vector3 b) {
		return (float) Math.sqrt(((a.x - b.x)*(a.x - b.x)) + ((a.z - b.z)*(a.z - b.z)));
	}
	
	public float distanceBetweenPointToLine(Vector3 A, Vector3 B, Vector3 P) {
	    float normalLength = (float)Math.sqrt((B.x-A.x)*(B.x-A.x)+(B.z-A.z)*(B.z-A.z));
	    return Math.abs((P.x-A.x)*(B.z-A.z)-(P.z-A.z)*(B.x-A.x))/normalLength;
	}
}
