package com.spidercache.dns;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Initiates a threaded messaging server that generates and sends
 * DNS queries to a {@link DnsServer} instance as quickly as possible
 * in order to stress test it.
 * 
 * @author Dustin Buckenmeyer
 */
public class DnsMessageSend implements Runnable {
	
	private final ExecutorService pool;
	private volatile boolean running;
	
	/**
	 * Creates a DnsMessageSend instance with a thread pool containing
	 * the specified number of threads.
	 * 
	 * @param threadPoolSize The maximum number of concurrent message generators.
	 */
	public DnsMessageSend(int threadPoolSize) {
		pool = Executors.newFixedThreadPool(threadPoolSize);
	}
	
	/**
	 * Starts the messaging server, which continuously
	 * spawns {@link DnsMessageSendThreads}.
	 */
	public void run() {
		System.out.println("MESSAGE CLIENT STARTED");
		running = true;
		
		while (running) {
			pool.execute(new DnsMessageSendThread());
		}
	}
	
	/**
	 * Shuts down the server. TODO Needs work.
	 */
	public void stop() {
		System.out.println("SHUTTING DOWN THE MESSAGE SERVER.");
		running = false;
		
		// disable new tasks from being submitted
		System.out.println("Refusing further messages.");
		pool.shutdown();
		
		try {
			// wait a while for existing tasks to terminate
			System.out.println("Waiting for existing message threads to terminate.");
			if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
				// cancel currently executing tasks
				System.out.println("Timeout exceeded. Cancelling remaining message threads.");
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
