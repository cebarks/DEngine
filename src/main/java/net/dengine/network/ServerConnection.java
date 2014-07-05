package net.dengine.network;

import java.net.SocketAddress;

public class ServerConnection {
	public final String name;
	public final SocketAddress socketAddress;

	public ServerConnection(String name, SocketAddress socketAddress) {
		this.name = name;
		this.socketAddress = socketAddress;
	}
}
