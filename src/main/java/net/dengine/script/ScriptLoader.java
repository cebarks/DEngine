package net.dengine.script;

import static net.dengine.DEngine.LOG;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import net.dengine.DEngine;

public class ScriptLoader {

	private static final File baseScriptDir = new File("scripts");
	private static final File entityDir = new File(baseScriptDir, "entity");
	private static final File wallDir = new File(baseScriptDir, "wall");
	private static final File weaponDir = new File(baseScriptDir, "weapon");

	private final Map<String, Script> weapons;
	private final Map<String, Script> entities;
	private final Map<String, Script> walls;

	public ScriptLoader(DEngine engine) {
		weapons = new HashMap<String, Script>();
		entities = new HashMap<String, Script>();
		walls = new HashMap<String, Script>();

		try {
			loadWeapon();
			loadWall();
			loadEntity();
		} catch (FileNotFoundException e) {
			LOG.error("Couldn't find script: ", e);
		}

	}

	private void loadEntity() throws FileNotFoundException {
		for (File file : entityDir.listFiles()) {
			Scanner in = new Scanner(file);

			List<String> rawLines = new ArrayList<String>();

			while (in.hasNext()) {
				rawLines.add(in.nextLine());
			}

			Map<String, String> options = new HashMap<String, String>();

			List<String> loopLines = new ArrayList<String>();

			for (int i = 0; i < rawLines.size(); i++) {
				String s = rawLines.get(i);
				if (s.equals("{")) {
					for (int j = i + 1; j < rawLines.size(); j++) {
						if (rawLines.get(j).equals("}")) {
							loopLines.add(rawLines.get(j));
						}
					}
				}
				String[] split = s.split(" ");
				options.put(split[0], split[1]);
			}

			entities.put(options.get("name"), new Script(ScriptType.ENTITY, options, new ScriptLoop(loopLines)));

			in.close();
		}
	}

	private void loadWall() throws FileNotFoundException {
		for (File file : wallDir.listFiles()) {
			Scanner in = new Scanner(file);

			List<String> rawLines = new ArrayList<String>();

			while (in.hasNext()) {
				rawLines.add(in.nextLine());
			}

			Map<String, String> options = new HashMap<String, String>();

			List<String> loopLines = new ArrayList<String>();

			for (int i = 0; i < rawLines.size(); i++) {
				String s = rawLines.get(i);

				if (s.equals("{")) {
					for (int j = i + 1; j < rawLines.size(); j++) {
						if (rawLines.get(j).equals("}")) {
							loopLines.add(rawLines.get(j));
						}
					}
				}

				String[] split = s.split(" ");
				options.put(split[0], split[1]);
			}

			walls.put(options.get("name"), new Script(ScriptType.WALL, options, new ScriptLoop(loopLines)));

			in.close();
		}
	}

	private void loadWeapon() throws FileNotFoundException {
		for (File file : weaponDir.listFiles()) {
			Scanner in = new Scanner(file);

			List<String> rawLines = new ArrayList<String>();

			while (in.hasNext()) {
				rawLines.add(in.nextLine());
			}

			Map<String, String> options = new HashMap<String, String>();

			for (String s : rawLines) {
				String[] split = s.split(" ");
				options.put(split[0], split[1]);
			}

			weapons.put(options.get("name"), new Script(ScriptType.WEAPON, options, null));

			in.close();
		}
	}

	public List<Script> getAllScripts() {
		List<Script> all = new ArrayList<Script>();

		all.addAll(weapons.values());
		all.addAll(walls.values());
		all.addAll(entities.values());

		return all;
	}

	public Map<String, Script> getWeapons() {
		return weapons;
	}

	public Map<String, Script> getEntities() {
		return entities;
	}

	public Map<String, Script> getWalls() {
		return walls;
	}
}
