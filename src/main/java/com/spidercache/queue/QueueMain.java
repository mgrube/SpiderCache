package com.spidercache.queue;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.planx.routing.RoutingException;
import org.planx.routing.messaging.Message;
import org.planx.routing.Identifier;

//import Queue.TestMessage;
//import Queue.TestMessage2;

import com.spidercache.dht.*;

public class QueueMain {

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//InQueue myIn = new InQueue("127.0.0.1",5001);
		//TestObject testing = new TestObject("Testing!!!");
		DHT node1 = new DHT("Node Port 5001", 5001, 5101, "127.0.0.1", 5001);
		DHT node2 = new DHT("Node Port 5002", 5002, 5102, "127.0.0.1", 5001);
		DHT node3 = new DHT("Node Port 5003", 5003, 5103, "127.0.0.1", 5001);
		//try {
				//node1.put("test", testing);
				//System.out.println("Key Found: " + node2.canFind("test"));
				//Serializable test = node2.get("test");
				//TestObject myObject = (TestObject) test;
				//System.out.println(myObject.get());
				InetSocketAddress node1adr = new InetSocketAddress("127.0.0.1", 5001);
				InetSocketAddress node2adr = new InetSocketAddress("127.0.0.1", 5002);
				InetSocketAddress node3adr = new InetSocketAddress("127.0.0.1", 5003);
				//Message message = new QueueMessage(node1.getNode(),"Bananas");
				//System.out.println("DHT.java: Sending TestMessage from " + node1.toString() + " to " + node2.toString());				
				//node1.sendMessage(message, node2adr.getAddress(), 5102, new QueueReplyReceiver(node1));
				//Message message2 = new TestMessage2(node2.getNode(),"TEST!");
				//System.out.println("DHT.java: Sending TestMessage2 from " + node1.toString() + " to " + node2.toString());	
				//node2.sendMessage(message2, node3adr.getAddress(), 5102, new TestReplyReceiver2(node2.getNode()));
				
				System.exit(0);
        //}
//			} catch (RoutingException ex) {
//				Logger.getLogger(DHT.class.getName()).log(Level.SEVERE, null, ex);
//			} catch (IOException ex) {
//				Logger.getLogger(DHT.class.getName()).log(Level.SEVERE, null, ex);
//			}
		}
	

}
