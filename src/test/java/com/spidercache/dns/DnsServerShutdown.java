package com.spidercache.dns;

import java.util.TimerTask;

/**
 * Shuts down the client processes. Gets called when the time
 * allotted for the test has expired.
 * 
 * @author Dustin Buckenmeyer
 */
public class DnsServerShutdown extends TimerTask {
	
	DnsMessageReceive receive;
	DnsMessageSend send;
	
	/**
	 * Creates a DnsServerShutdown instance.
	 * 
	 * @param receive A reference to client's DNS server, {@link DnsMessageReceive}.
	 * @param send A reference to the client's messaging server, {@link DnsMessageSend}.
	 */
	public DnsServerShutdown(DnsMessageReceive receive, DnsMessageSend send) {
		this.receive = receive;
		this.send = send;
	}
	
	/**
	 * Initiates the shutdown procedure by calling each of the client's servers'
	 * stop methods.
	 */
	public void run() {
		receive.stop();
		send.stop();
	}
	
}
