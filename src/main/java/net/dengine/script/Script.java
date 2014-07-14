package net.dengine.script;

import java.util.Map;

public class Script {
	private final Map<String, String> options;
	private final ScriptType type;
	private ScriptLoop loop;

	public Script(ScriptType type, Map<String, String> options, ScriptLoop loop) {
		this.type = type;
		this.options = options;
		this.loop = loop;
	}

	public boolean hasLoop() {
		if (type == ScriptType.WEAPON || loop == null) {
			return false;
		}
		return true;
	}

	public ScriptType getType() {
		return type;
	}

	public void runLoop() {
		loop.run();
	}
}
