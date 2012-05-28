package com.spidercache.dht;

import java.io.IOException;
import java.util.Collections;

import org.planx.routing.kademlia.*;
import org.planx.routing.messaging.*;

/**
 * 
 **/
public class TestReplyReceiver implements Receiver {
    private Node node;

    public TestReplyReceiver(Node node) {
        this.node = node;
    }

    public void receive(Message m, int comm) throws IOException {
        TestReplyMessage reply = (TestReplyMessage) m;
    	//System.out.println("TestReplyReceiver.java: TestReplyMessage recieved at " + node.toString() + " from " + reply.getOrigin());
        // Sort nodes so that the order is deterministic
        Collections.sort(reply.getNodes(), new Node.DistanceComparator(node.getId()));
        System.out.println("TestReplyReceiver.java: receive comm="+comm+" message="+m);
    }

    public void timeout(int comm) throws IOException {
    	//System.out.println("TestReplyReceiver.java: timeout comm="+comm);
    	assert (comm != 0);
    }
}