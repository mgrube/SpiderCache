package com.spidercache.dns;

import java.net.DatagramPacket;

/**
 * Responsible for receiving and tallying the responses from
 * the {@link DnsServer} instance being stress tested.
 * 
 * @author Dustin Buckenmeyer
 */
public class DnsMessageReceiveThread implements Runnable {
	
	private final DatagramPacket packet;
	
	/**
	 * Creates a DnsMessageReceiveThread with the specified DNS response packet.
	 * 
	 * @param packet A datagram packet containing the DNS response.
	 */
	public DnsMessageReceiveThread(DatagramPacket packet) {
		this.packet = packet;
	}
	
	/**
	 * Parses the received response and outputs minimal information
	 * about it to the console.
	 */
	public void run() {
		// parse query
		DnsMessage query = new DnsMessage(packet.getData());
		DnsQuestion[] questions = query.getQuestions();
		for (int i = 0; i < questions.length; i++) {
			System.out.println(query.getHeader().getId() + " : " + questions[i].getQname());
		}
	}

}
