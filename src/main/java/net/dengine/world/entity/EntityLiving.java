package net.dengine.world.entity;

import net.dengine.vec.Vector3;
import net.dengine.world.Section;
import net.dengine.world.Wall;
import net.dengine.world.World;

public class EntityLiving extends Entity {

	public static final float COLLISION_DISTANCE = .2f;
	protected Vector3 position;
	protected Vector3 rotation;
	protected Vector3 previous;
	
	public float speed;
	public boolean walking;

	public EntityLiving(World world) {
		super(world);
		position = new Vector3(0, 15, 0);
		rotation = new Vector3(0, 0, 0);
		previous = position;
	}

	@Override
	public void update() {
		super.update();
		collision();
	}

	public Vector3 getPosition() {
		return position;
	}

	public void setPosition(Vector3 position) {
		this.position = position;
	}

	public Vector3 getRotation() {
		return rotation;
	}

	public void setRotation(Vector3 rotation) {
		this.rotation = rotation;
	}

	protected void collision() {
		for (Section s : world.getSections()) {
			for (Wall w : s.getWalls()) {
				float previousDistance = distanceBetweenPointToLine(new Vector3(-w.start.x, w.start.y, -w.start.z), new Vector3(-w.end.x, w.end.y, -w.end.z), previous);
				float positionDistance = distanceBetweenPointToLine(new Vector3(-w.start.x, w.start.y, -w.start.z), new Vector3(-w.end.x, w.end.y, -w.end.z), position);
				float delta = Math.abs(previousDistance - positionDistance);
				if (distanceBetween(position.negative(), w.start) + distanceBetween(position.negative(), w.end) < w.getLength() + COLLISION_DISTANCE) {
					position = new Vector3(position.x - (delta * w.normal.x), position.y , position.z - (delta * w.normal.z));
				}
			}
		}
	}
}
