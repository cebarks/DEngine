package net.dengine.world;

import static net.dengine.DEngine.LOG;

import java.io.Externalizable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import net.dengine.DEngine;
import net.dengine.vec.Vector3;
import net.dengine.world.entity.Entity;
import net.dengine.world.entity.EntityPlayer;

public class WorldSaveFile implements Externalizable {

	private World world;

	private File file;

	private DEngine engine;

	public WorldSaveFile() {

	}

	public WorldSaveFile(DEngine engine, World world) {
		this.engine = engine;
		this.world = world;
		this.file = new File(world.getName());
	}

	public WorldSaveFile(DEngine engine, File file) {
		this.engine = engine;
		this.file = file;
		this.world = new World(engine, file.getName());
	}

	public boolean saveWorld() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
			out.writeObject(this);
			out.flush();
			out.close();
		} catch (IOException e) {
			LOG.error("Couldn't save world \"" + world.getName() + "\"", e);
			return false;
		}

		return true;
	}

	public boolean loadWorld() {
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
			world = ((WorldSaveFile) in.readObject()).getWorld();
			in.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			LOG.error("Couldn't load world from file \"" + file.getName() + "\"", e);
			engine.loadDefaultWorld();
			return false;
		}

		return true;
	}

	public World getWorld() {
		return world;
	}

	public File getFile() {
		return file;
	}

	public DEngine getEngine() {
		return engine;
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(world.getName());

		Entity player = world.getLocalPlayer();

		out.writeInt(world.getSections().size());
		for (Section s : world.getSections()) {
			out.writeInt(s.getWalls().size());

			for (Wall w : s.getWalls()) {
				out.writeObject(w.start);
				out.writeObject(w.end);
				out.writeFloat(w.height);
			}

			/*out.writeInt(s.getEntites().size());
			for (Entity e : s.getEntites()) {
				if (e.equals(player))
					out.writeBoolean(true);
				else
					out.writeBoolean(false);
				e.save(out);
			}*/
		}

	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		this.engine = DEngine.INSTANCE;

		world = new World(engine, (String) in.readObject());

		int sectionCount = in.readInt();
		for (int i = 0; i < sectionCount; i++) {
			int wallCount = in.readInt();
			Section section = new Section(world);

			for (int j = 0; j < wallCount; j++) {
				new Wall(section, (Vector3) in.readObject(), (Vector3) in.readObject(), in.readFloat());
			}

			/*int entityCount = in.readInt();

			for (int j = 0; j < entityCount; j++) {
				Entity e = new Entity(section);
				if (in.readBoolean())
					world.setLocalPlayer((EntityPlayer) e);
				e.load(in);
			}*/

			world.addSection(section);
		}
	}
}
