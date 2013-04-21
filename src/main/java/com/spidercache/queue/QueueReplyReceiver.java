package com.spidercache.queue;
import java.io.IOException;
import java.util.Collections;

import org.planx.routing.kademlia.Node;
import org.planx.routing.messaging.Message;
import org.planx.routing.messaging.Receiver;
import com.spidercache.dht.*;


/**
 * 
 **/
public class QueueReplyReceiver implements Receiver {
    private DHT dht;

    public QueueReplyReceiver(DHT dht) {
        this.dht = dht;
    }

    public void receive(Message m, int comm) throws IOException {
    	
    	
       //Message reply =  m;
        //dht.replyMessage(m, dht.getNode().getInetAddress(), dht.getNode().getPort(), comm);
        //System.out.println("TestReplyReceiver.java: TestReplyMessage recieved at " + node.toString() + " from " + reply.getOrigin());
        // Sort nodes so that the order is deterministic
       // Collections.sort(reply.getNodes(), new Node.DistanceComparator(node.getId()));
        //System.out.println("TestReplyReceiver.java: receive comm="+comm+" message="+m);
    }

    public void timeout(int comm) throws IOException {
    	System.out.println("TestReplyReceiver.java: timeout comm="+comm);
    }
}