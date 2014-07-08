package net.dengine.world.entity;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import net.dengine.world.World;

public class EntityPlayer extends EntityLiving {

	private String name;

	public EntityPlayer(World world, String name) {
		super(world);
		this.name = name;
	}

	public void inputUpdate() {
		rotation.y += Mouse.getDX() / 2;

		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			position.x += Math.sin(rotation.y * Math.PI / 180) * 0.5f;
			position.z += Math.cos(rotation.y * Math.PI / 180) * 0.5f;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			position.x += Math.sin((rotation.y + 180) * Math.PI / 180) * 0.5f;
			position.z += Math.cos((rotation.y + 180) * Math.PI / 180) * 0.5f;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			position.y += .5f;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			position.y -= .5f;
		}
	}
}
