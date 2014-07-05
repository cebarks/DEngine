package net.dengine.network;

public abstract class Packet {

	public final PacketType type;

	public Packet(PacketType type) {
		this.type = type;
	}

	public abstract byte[] getBytes();
}
