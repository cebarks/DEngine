package net.dengine.network;

import static net.dengine.DEngine.LOG;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.dengine.DEngine;

public class NetworkServer implements Runnable {
	private DatagramSocket socket;
	private final Thread controlThread;
	private boolean running;
	private final DEngine engine;
	private List<ServerConnection> connections;

	public NetworkServer(DEngine engine, int port) {
		try {
			socket = new DatagramSocket(port);
		} catch (Exception e) {
			engine.exitOnError(1, e);
		}

		this.connections = new ArrayList<ServerConnection>();

		this.engine = engine;

		running = true;
		controlThread = new Thread(this);
		controlThread.start();
	}

	public void run() {
		try {
			byte[] buf = new byte[1024];

			DatagramPacket packet = new DatagramPacket(buf, buf.length);

			while (running) {
				socket.receive(packet);
				byte[] data = Arrays.copyOfRange(packet.getData(), 1, packet.getData().length);

				switch (PacketType.getTypeByID(packet.getData()[0])) {
				default:
					break;
				case INVALID:
					LOG.error("Recieved invalid packet... Discarding.");
					break;
				case PING:
					LOG.info("Got ping packet from: " + packet.getAddress().getHostAddress());
					LOG.info("Sending reply...");
					Packet01Ping pingPacket = new Packet01Ping(true);
					socket.send(new DatagramPacket(pingPacket.getBytes(), pingPacket.getBytes().length, packet.getSocketAddress()));
					break;
				case LOGIN:
					break;
				}
			}

		} catch (Exception e) {
			engine.exitOnError(1, e);
		}
	}

	/**
	 * Sends the given {@link Packet} to all clients connected.
	 * 
	 * @param packet
	 *            - the packet to be sent
	 */
	public void sendPacket(Packet packet, ServerConnection connection) {
		for (ServerConnection con : connections) {
			try {
				socket.send(new DatagramPacket(packet.getBytes(), packet.getBytes().length, con.socketAddress));
			} catch (IOException e) {
				LOG.warn("Cannot send packet: " + packet);
			}
		}
	}

	/**
	 * Lookup a {@link ServerConnection} from the given connection name.
	 * 
	 * @param name
	 *            - name of connection
	 * @return the connection or null if no connections exist with given name
	 */
	public ServerConnection lookupConnectionByName(String name) {
		for (ServerConnection con : connections) {
			if (con.name.equalsIgnoreCase(name)) {
				return con;
			}
		}
		return null;
	}
}
