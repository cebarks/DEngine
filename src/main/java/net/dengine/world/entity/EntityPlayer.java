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
	
	public float speed;
	
	public EntityPlayer(World world, String name) {
		super(world);
		this.name = name;
		previous = position;
	}

	@Override
	public void update() {
		Display.setTitle(String.format("DEngine demo | x:%f y:%f z:%f", position.x, position.y, position.z));
		collision();
	}
	
	private void collision() {
		for(Section s : world.getSections()) {
			for(Wall w : s.getWalls()) {
					if(distanceBetween(position.negative(), w.start) + distanceBetween(position.negative(), w.end) < w.getLength() + 0.5f) {
						position = new Vector3(position.x - (speed * (w.normal.x)), position.y - (speed * (w.normal.y)), position.z - (speed * (w.normal.z)));              
					}
			
			}
		}
	}

	public void inputUpdate() {
		
		previous = new Vector3(position.x, position.y, position.z);
		speed = 0.5f;

		rotation.y += Mouse.getDX() / 2;

		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			position.x -= Math.sin(rotation.y * Math.PI / 180) * speed;
			position.z += Math.cos(rotation.y * Math.PI / 180) * speed;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			position.x -= Math.sin((rotation.y + 180) * Math.PI / 180) * speed;
			position.z += Math.cos((rotation.y + 180) * Math.PI / 180) * speed;
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
	
	private Vector3 normalizeWalkStride(Vector3 start, Vector3 end) {
		Vector3 directing = start.sub(end);
		Vector3 flipped = directing.invert();
		return new Vector3(flipped.z / flipped.length, 0, -flipped.x / flipped.length);
	}
}
