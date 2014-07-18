package net.dengine.world;

import java.util.ArrayList;
import java.util.List;

import net.dengine.DEngine;
import net.dengine.world.entity.Entity;
import net.dengine.world.entity.EntityPlayer;

public class World {
	private final DEngine engine;

	private List<Entity> entities;

	private EntityPlayer localPlayer;

	private List<Section> sections;
	private int idIndex;

	private String name;

	public World(DEngine engine, String name) {
		this.engine = engine;
		this.name = name;

		idIndex = 0;

		sections = new ArrayList<Section>();
		entities = new ArrayList<Entity>();
	}

	public void create() {
		for (Section s : sections)
			s.create();
	}

	public void render3D() {
		for (Section s : sections)
			s.render3D();
		for (Entity e : entities)
			e.render3D();
	}

	public void render2D() {
		for (Section s : sections)
			s.render2D();
		for (Entity e : entities)
			e.render2D();
	}

	public int getNextEntityID() {
		return idIndex++;
	}

	public DEngine getEngine() {
		return engine;
	}

	public void addEntity(Entity entity) {
		entities.add(entity);
	}

	public void addSection(Section section) {
		sections.add(section);
	}

	public Entity getEntity(int index) {
		return entities.get(index);
	}

	public Section getSection(int index) {
		return sections.get(index);
	}

	public void update() {
		localPlayer.inputUpdate();
		for (Entity e : entities)
			e.update();
	}

	public void setLocalPlayer(EntityPlayer entityPlayer) {
		this.localPlayer = entityPlayer;
	}

	public EntityPlayer getLocalPlayer() {
		return localPlayer;
	}

	public String getName() {
		return name;
	}

	public List<Section> getSections() {
		return sections;
	}

	public List<Entity> getEntities() {
		return entities;
	}
}
