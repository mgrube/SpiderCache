package com.spidercache.dns;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Initiates a threaded DNS server that receives and tallies the DNS responses
 * it receives from the {@link DnsServer} instance being stress tested.
 * 
 * @author Dustin Buckenmeyer
 */
public class DnsMessageReceive implements Runnable {

	private static final int MAX_PACKET_SIZE = 512;
	private DatagramSocket socket;
	private final ExecutorService pool;
	private volatile boolean listening;
	private int count;
	
	/**
	 * Configures the server to have the specified number of threads,
	 * and binds it to the designated port.
	 * 
	 * @param port The port on which to listen for incoming DNS messages.
	 * @param threadPoolSize The maximum number of concurrent queries to process.
	 */
	public DnsMessageReceive(int port, int threadPoolSize) {
		socket = null;
		
		try {
			socket = new DatagramSocket(port);
		} catch (SocketException e) {
			System.err.println("Socket failed to bind to port: " + port + ".");
		}
		
		pool = Executors.newFixedThreadPool(threadPoolSize);
		count = 0;
	}
	
	/**
	 * Returns the number of received DNS responses.
	 * 
	 * @return The tally of received DNS responses.
	 */
	public int getCount() { return count; }
	
	/**
	 * Starts the DNS server, which continuously
	 * spawns {@link DnsMessageReceiveThreads}.
	 */
	public void run() {
		System.out.println("DNS SERVER STARTED");
		listening = true;
		
		while (listening) {
			DatagramPacket packet = new DatagramPacket(new byte[MAX_PACKET_SIZE], MAX_PACKET_SIZE);
		
			try {
				socket.receive(packet);
				pool.execute(new DnsMessageReceiveThread(packet));
				count++;
			} catch (IOException e) {
				pool.shutdown();
			}
		}
		
		socket.close();
	}
	
	/**
	 * Shuts down the server. TODO Needs work.
	 */
	public void stop() {
		System.out.println("SHUTTING DOWN THE DNS SERVER.");
		listening = false;
		
		// disable new tasks from being submitted
		System.out.println("Refusing further requests.");
		pool.shutdown();
		
		try {
			// wait a while for existing tasks to terminate
			System.out.println("Waiting for existing request threads to terminate.");
			if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
				// cancel currently executing tasks
				System.out.println("Timeout exceeded. Cancelling remaining request threads.");
				pool.shutdownNow();
				// wait a while for tasks to respond to being cancelled
				if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
					System.err.println("Pool did not terminate");
				}
			}
		} catch (InterruptedException e) {
			// (re-)cancel if current thread also interrupted
			pool.shutdownNow();
			// preserve interrupt status
			Thread.currentThread().interrupt();
		}
	}

}
