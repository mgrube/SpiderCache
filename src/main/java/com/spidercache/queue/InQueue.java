package com.spidercache.queue;
 
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

import org.planx.routing.messaging.Message;

import com.spidercache.node.Request;
import com.spidercache.dht.*;
import com.spidercache.queue.Status;
import com.spidercache.queue.Status.nodeStatus;
import org.planx.routing.kademlia.operation.*;


public class InQueue {
	protected PriorityQueue<Request> inQueue;
	protected ArrayList<String> holdList;
	protected HashMap<String, Boolean> destinationList;
	protected Status status;
	protected InetSocketAddress myadr;
	protected boolean logEnable;	
	
	/**
	 * Creates an empty inQueue
	 * 
	 @param hostname The hostname of the server the Queue is on
	 *
	 @param port The port on which the DHT node associated with this queue uses
	 */
	public InQueue(String hostname,int port)
	{
		inQueue = new PriorityQueue<Request>(100);
		holdList = new ArrayList<String>();		
		destinationList = new HashMap<String, Boolean>();
		status = new Status(nodeStatus.AVAILABLE);
		myadr = new InetSocketAddress(hostname,port);
		logEnable = false;
	}
	
	/**
	 * Adds a request to the inQueue, or if there is another request already
	 * going to the same destination node it is added to the holdlist to be
	 * sent off in unison.
	 * 
	 @param r a specific request to be added
	 */
	public void add(Request r)
	{
		status.requestsIn++;
		/*if(r.getDestination==myadr)
		{
			//Send to host node
		}*/
		for(int i = 0;i<r.getURL().size();i++)
		{
			if(isDuplicate(r.getURL().get(i)))
			{			
				if(logEnable)
				{
					//log this
				}
				holdList.add(r.getURL().get(i));
			}
			else
			{
				if(logEnable)
				{
					//log this
				}
				inQueue.add(r);
				destinationList.put(r.getDataHash(), true);			
			}
		}
	}
	
	/**
	 * Sends the current request to the outQueue on the same node.
	 */
	public void sendToOut(DHT myNode)
	{		
		Request current = inQueue.peek();			
		
		if(destinationList.get(current.getDataHash())==true)
		{
		 	for(int i = 0;i<holdList.size();i++)
		 	{		 		
		 		if(holdList.get(i).equals(current.getDataHash()))
		 		{
		 			current.addURL(holdList.get(i));
		 			holdList.remove(i);
		 		}
		 	}
		}	
		Message message = new QueueMessage(myNode.getNode(),current.getDataHash());		 
		
		try 
		{
			myNode.sendMessage(message, myadr.getAddress(), myadr.getPort(),new QueueReplyReceiver(myNode));	
			status.requestsOut++;
		}
		catch (IOException e) 
		{			
			e.printStackTrace();
		}		
		if(logEnable)
		{
			//log this
		}
		destinationList.remove(inQueue.poll().getDataHash());
	}
	public Status getStatus()
	{
		status.efficiencyRatio = status.requestsOut/status.requestsIn;
		if(status.efficiencyRatio <=.35)
			status.status = nodeStatus.UNAVAILABLE;
		else if(status.efficiencyRatio<=.60)
			status.status = nodeStatus.BUSY;
		else
			status.status = nodeStatus.AVAILABLE;
		
		return status;
	}
	/**
	 * Tests to see if there is already a request headed to the destination
	 * 
	 @param destination the address of the node the request is headed to.
	 */
	private boolean isDuplicate(String data)
	{
		if(destinationList.get(data)==true)
			return true;
		else
			return false;
	}	
	public String log(Boolean toggle)
	{
		logEnable = toggle;		
		
		if(logEnable)
			return "Logging enabled";
		else
			return "Logging disabled";
	}
	public boolean isEmpty()
	{
		return inQueue.isEmpty();
	}
}


