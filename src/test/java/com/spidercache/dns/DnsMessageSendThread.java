package com.spidercache.dns;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Responsible for generating the DNS queries used in the {@link DnsServer}
 * stress test.
 * 
 * @author Dustin Buckenmeyer
 */
public class DnsMessageSendThread implements Runnable {
	
	/**
	 * Generates a DNS query with a random identifier and sends it to the
	 * {@link DnsServer} instance.
	 */
	public void run() {
		
		DatagramSocket socket = null;
		
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			System.err.println("Socket could not bind to any available port.");
		}
		
		// generate message header
		DnsHeader header = new DnsHeader(
				(short) (Math.round(Math.random() * Short.MAX_VALUE)), (byte) 0, (byte) 1, (short) 1);
		header.setQr((byte) 0);
		header.setAa((byte) 0);
		
		// generate questions
		DnsQuestion[] questions = new DnsQuestion[1];
		
		for (int i = 0; i < questions.length; i++) {
			questions[i] = new DnsQuestion("www.dubu.me", (short) 1, (short) 1);
		}
		
		// compile the message
		DnsMessage message = new DnsMessage(header, questions);
		
		// convert response to a byte array
		byte[] data = message.assembleMessage();
		
		// generate the datagram and send it
		DatagramPacket packet = new DatagramPacket(data, data.length);
		
		InetAddress address = null;
		
		try {
			address = InetAddress.getByName("192.168.1.2");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		packet.setAddress(address);
		packet.setPort(53);
		
		try {
			socket.send(packet);
		} catch (IOException e) {
			System.err.println("Packet failed to send.");
		}
		
		socket.close();
	}

}
