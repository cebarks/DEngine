package net.dengine.network;

import static net.dengine.DEngine.LOG;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

import net.dengine.DEngine;

public class NetworkClient implements Runnable {
	private DatagramSocket socket;
	private final Thread controlThread;
	private boolean running;
	private final DEngine engine;

	public NetworkClient(DEngine engine, String hostname, int port) {
		try {
			socket = new DatagramSocket(port, InetAddress.getByName(hostname));
		} catch (Exception e) {
			engine.closeOnError(1, e);
		}

		this.engine = engine;

		running = true;
		(controlThread = new Thread(this)).start();
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
				}
			}

		} catch (Exception e) {
			engine.closeOnError(1, e);
		}
	}

	/**
	 * Sends the provided {@link Packet} to the server.
	 * 
	 * @param packet - the packet to send
	 */
	public void sendPacket(Packet packet) {
		DatagramPacket dataPacket = new DatagramPacket(packet.getBytes(), packet.getBytes().length);
		try {
			socket.send(dataPacket);
		} catch (IOException e) {
			LOG.warn("Cannot send packet: " + packet);
		}

	}
}
