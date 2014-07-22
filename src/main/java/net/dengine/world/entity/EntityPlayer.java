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

	public EntityPlayer(World world, String name) {
		super(world);
		this.name = name;
		previous = position;
		normal = new Vector3(0, 0, 1);
	}

	@Override
	public void update() {
		super.update();
		Display.setTitle(String.format("DEngine demo | x:%f y:%f z:%f", position.x, position.y, position.z));
		collision();
	}

	private void collision() {
		for (Section s : world.getSections()) {
			for (Wall w : s.getWalls()) {
				float previousDistance = distanceBetweenPointToLine(new Vector3(-w.start.x, w.start.y, -w.start.z), new Vector3(-w.end.x, w.end.y, -w.end.z), previous);
				float positionDistance = distanceBetweenPointToLine(new Vector3(-w.start.x, w.start.y, -w.start.z), new Vector3(-w.end.x, w.end.y, -w.end.z), position);
				float delta = Math.abs(previousDistance - positionDistance);
				if (distanceBetween(position.negative(), w.start) + distanceBetween(position.negative(), w.end) < w.getLength() + 0.5f) {
					position = new Vector3(position.x - (delta * w.normal.x), position.y - (delta * w.normal.y), position.z - (delta * w.normal.z));
				}
			}
		}
	}

	public void inputUpdate() {

		previous = new Vector3(position.x, position.y, position.z);
		speed = 0.5f;

		rotation.y += Mouse.getDX() / 2;

		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			walking = true;
			position.x -= Math.sin(rotation.y * Math.PI / 180) * speed;
			position.z += Math.cos(rotation.y * Math.PI / 180) * speed;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			walking = true;
			position.x -= Math.sin((rotation.y + 180) * Math.PI / 180) * speed;
			position.z += Math.cos((rotation.y + 180) * Math.PI / 180) * speed;
		} else {
			walking = false;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			position.y += .5f;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			position.y -= .5f;
		}

	}

}
