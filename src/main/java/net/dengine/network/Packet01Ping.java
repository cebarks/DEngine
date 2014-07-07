package net.dengine.network;

public class Packet01Ping extends Packet {

	public boolean isReturning;

	public Packet01Ping(boolean bool) {
		super(PacketType.PING);
		this.isReturning = bool;
	}

	public Packet01Ping(byte[] data) {
		super(PacketType.PING);
		isReturning = data[0] == 1 ? true : false;
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { PacketType.PING.ID, isReturning ? (byte) 1 : (byte) 0 };
	}
}
