package net.dengine.network;

public enum PacketType {
	INVALID(0xFF), LOGIN(0x00), PING(0x01);

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
