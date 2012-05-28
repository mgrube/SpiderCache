package com.spidercache.dns;

import java.util.Timer;

/**
 * Tests the performance of the threaded {@link DnsServer} under varying loads.
 * <p>
 * Since thread profiling is complex, a simple timed test may give a
 * reasonable indication of performance. The test is performed by spamming
 * the server with as many queries as possible from a client for a set time
 * period. These messages are parsed by the server and the corresponding
 * responses are returned to the client. The client has its own DNS server
 * running that listens for those responses and tallies each one as it comes in.
 * The final tally after the time expires is the metric used to measure the
 * server's performance.
 * 
 * @author Dustin Buckenmeyer
 */
public class DnsServerTest {
	
	/**
	 * Spawns two separate processes: one that spams the DNS server with
	 * messages, and one that listens for the returned responses. A timer
	 * initiates a shutdown function that kills these processes. The tally
	 * is reported after the timer has expired.
	 */
	public static void main(String[] args) {
		Timer t = new Timer();
		DnsMessageReceive receive = new DnsMessageReceive(8053, 10);
		DnsMessageSend send = new DnsMessageSend(10);
		DnsServerShutdown d = new DnsServerShutdown(receive, send);

		Thread r = new Thread(receive);
		Thread s = new Thread(send);
		
		r.start();
		s.start();
		
		
		t.schedule(d, 15000);
		
		try {
			r.join();
			s.join();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
    	System.out.println("Number of threads handled: " + receive.getCount());
    }
    
}
