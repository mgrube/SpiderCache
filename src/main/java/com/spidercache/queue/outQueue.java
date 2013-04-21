package com.spidercache.queue;
import java.util.Queue;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

//import Queue.TestMessage;

import com.spidercache.node.Request;
import org.planx.routing.messaging.Message;

import com.spidercache.dht.*;
public class outQueue {
	
	protected Queue<Request> outQueue;	
	protected InetSocketAddress myAdr;
	protected HashMap<InetAddress, String> neighborStatus;	
	
	/**
	 * Constructor method for outQueue
	 * 
	 @param hostname hostname of server that the node that stores the outQueue is stored on
	 @param port to use for communication
	 */
	public outQueue(String hostname,int port)
	{
		outQueue = new LinkedList<Request>();
		myAdr = new InetSocketAddress(hostname,port);
	}
	
	/**
	 * Queries neighboring queues to retrieve their statuses
	 * 
	 @param myNode a reference to the DHT Node in which this queue resides
	 @param nodeaddress an array with the locations of all neighbors to query
	 */
	public void queryNeighbors(DHT myNode,ArrayList<InetSocketAddress> nodeaddress)
	{
		int comm = 0;
		QueueReplyReceiver receiver = new QueueReplyReceiver(myNode);
		//Message message = new QueueMessage(myNode.getNode(),"Requesting Status");
		for(int i = 0;i<nodeaddress.size();i++)
		{
			try 
			{				
				//comm = myNode.sendMessage(message, nodeaddress.get(i).getAddress(), nodeaddress.get(i).getPort(),receiver);
				neighborStatus.put(nodeaddress.get(i).getAddress(), receiver.toString());				
			} 
			catch (Exception e)
			{				
				e.printStackTrace();
			}			
		}		
	}
	public void add(Request r)
	{
		outQueue.add(r);
	}
	public void Send(Request r,DHT destNode,DHT myNode)
	{
		//Message message = new QueueMessage(destNode.getNode(),r.toString());
		QueueReplyReceiver test = new QueueReplyReceiver(destNode);
		InetAddress temp = null;
		try {
			temp = InetAddress.getByName("127.0.0.1");
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		
		try 
		{			
			//destNode.sendMessage(message, temp, 5001, test);			
			//destNode.sendMessage(message,temp,5001, new QueueReplyReceiver(myNode));
		} 		
		catch (Exception e)
		{			
			e.printStackTrace();
		}
	}
}
