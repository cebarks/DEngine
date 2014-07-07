package net.dengine.network;

public enum PacketType {
	INVALID(00), PING(01), LOGIN(02);

	public final byte ID;

	private PacketType(int id) {
		this.ID = (byte) id;
	}

	public static PacketType getTypeByID(byte id) {
		for (PacketType type : values())
			if (type.ID == id)
				return type;

		return INVALID;
	}
}
