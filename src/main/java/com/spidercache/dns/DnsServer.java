package com.spidercache.dns;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Initiates a threaded DNS server to listen for incoming DNS datagrams.
 * <p>
 * TODO Implement a stop function that kills the server appropriately.
 * 
 * @author Dustin Buckenmeyer
 */
public class DnsServer implements Runnable {
	
	private static final int MAX_PACKET_SIZE = 512;
	private DatagramSocket socket;
	private final ExecutorService pool;
	private volatile boolean listening = true;
	
	/**
	 * Configures the server to have the specified number of threads,
	 * and binds it to the designated port.
	 * 
	 * @param port The port on which to listen for incoming DNS messages.
	 * @param threadPoolSize The maximum number of concurrent queries to process.
	 */
	public DnsServer(int port, int threadPoolSize) {
		socket = null;
		
		try {
			socket = new DatagramSocket(port);
		} catch (SocketException e) {
			System.err.println("Socket failed to bind to port: " + port + ".");
		}
		
		pool = Executors.newFixedThreadPool(threadPoolSize);
	}
	
	/**
	 * Starts the server, spawning off a {@link DnsServerThread} instance 
	 * for each query received.
	 */
	public void run() {
		System.out.println("DNS SERVER STARTED");
		
		while (listening) {
			DatagramPacket packet = new DatagramPacket(new byte[MAX_PACKET_SIZE], MAX_PACKET_SIZE);
		
			try {
				socket.receive(packet);
				pool.execute(new DnsServerThread(packet, "192.168.1.2"));
			} catch (IOException e) {
				pool.shutdown();
			}
		}
		
		socket.close();
	}
	
	public static void main(String[] args) {
    	new DnsServer(8053, 10).run();
    }
    
}