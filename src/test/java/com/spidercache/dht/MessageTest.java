package com.spidercache.dht;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.junit.Test;
import org.planx.routing.messaging.MessageFactory;
import org.planx.routing.messaging.*;
import org.planx.routing.Identifier;
import org.planx.routing.*;

public class MessageTest {

	@Test
	public void testMessage1() {
		DHT node1 = new DHT("Node 5001", 5001, 5101, "127.0.0.1", 5001);
		DHT node2 = new DHT("Node 5002", 5002, 5102, "127.0.0.1", 5001);

		InetSocketAddress address = new InetSocketAddress("127.0.0.1", 5002);
		MessageFactory message = new TestMessage(node1.getNode(), Identifier.randomIdentifier());
		try {
			node1.sendMessage(message, address.getAddress(), 5102, new TestReplyReceiver(node1.getNode()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			node1.closeNode();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			node2.closeNode();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testMessage2() {
		DHT node1 = new DHT("Node 5001", 5001, 5101, "127.0.0.1", 5001);
		DHT node2 = new DHT("Node 5002", 5002, 5102, "127.0.0.1", 5001);

		InetSocketAddress address = new InetSocketAddress("127.0.0.1", 5002);
		Message message2 = new TestMessage2(node1.getNode(), "TEST!!!");
		try {
			node1.sendMessage(message2, address.getAddress(), 5102,
					new TestReplyReceiver2(node1.getNode()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			node1.closeNode();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			node2.closeNode();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}