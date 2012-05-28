package com.spidercache.dns;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Handles each DNS query received by a {@link DnsServer} instance.
 * 
 * @author Dustin Buckenmeyer
 */
public class DnsServerThread implements Runnable {

	private final DatagramPacket packet;
	private final String address;
	
	/**
	 * Creates a DnsServerThread.
	 * 
	 * @param packet The received datagram packet.
	 * @param address The IP address of the web server to embed in all responses.
	 */
	public DnsServerThread(DatagramPacket packet, String address) {
		this.packet = packet;
		this.address = address;
	}
	
	/**
	 * Formulates a response to the query and returns it to the sender.
	 */
	public void run() {
		DatagramSocket socket = null;
		
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			System.err.println("Socket could not bind to any available port.");
		}
		
		// parse query
		DnsMessage query = new DnsMessage(packet.getData());
		System.out.println(query.toString());
		
		// generate response header based on the query header
		DnsHeader queryHeader = query.getHeader();
		DnsHeader responseHeader = new DnsHeader(
				(short) queryHeader.getId(),
				(byte) queryHeader.getOpcode(),
				(byte) queryHeader.getRd(),
				(short) queryHeader.getQdcount()
		);
		
		// generate response answers based on the query questions
		DnsQuestion[] questions = query.getQuestions();
		DnsResourceRecord[] answers = new DnsResourceRecord[questions.length];
		
		for (int i = 0; i < answers.length; i++) {
			answers[i] = new DnsResourceRecord(
					questions[i].getQname(),
					(short) questions[i].getQtype(),
					(short) questions[i].getQclass(),
					address
			);
		}
		
		// compile the response
		DnsMessage response = new DnsMessage(responseHeader, questions, answers);
		System.out.println(response.toString());
		
		// convert response to a byte array
		byte[] data = response.assembleMessage();
		
		// generate the datagram and send it
		DatagramPacket responsePacket = new DatagramPacket(data, data.length);
		responsePacket.setAddress(packet.getAddress());
		responsePacket.setPort(8053);
		
		try {
			socket.send(responsePacket);
		} catch (IOException e) {
			System.err.println("Packet failed to send.");
		}

		socket.close();
	}

}
