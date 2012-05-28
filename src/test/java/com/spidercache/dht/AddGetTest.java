package com.spidercache.dht;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;
import org.planx.routing.*;

import com.spidercache.dht.DHT;


public class AddGetTest {

	@Test
	public void testAddGet() {
		TestObject test = new TestObject("Testing!");
		DHT node1 = new DHT("Node 5001", 5001, 5101, "127.0.0.1", 5001);
		DHT node2 = new DHT("Node 5002", 5002, 5102, "127.0.0.1", 5001);
		node1.put("test", test);
			try {
				assertEquals(test, (TestObject) node2.get("test"));
			} catch (RoutingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

}
