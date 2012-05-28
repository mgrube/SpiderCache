package com.spidercache.dht;

import java.io.IOException;
import java.util.Collections;

import org.planx.routing.messaging.Message;
import org.planx.routing.messaging.Receiver;
import org.planx.routing.messaging.UnknownMessageException;
import org.planx.routing.*;
import org.planx.routing.kademlia.*;

/**
 * 
 **/
public class TestReplyReceiver2 implements Receiver {
    private Node node;

    public TestReplyReceiver2(Node node) {
        this.node = node;
    }

    public void receive(Message m, int comm) throws IOException {
        TestReplyMessage2 reply = (TestReplyMessage2) m;
    	//System.out.println("TestReplyReceiver2.java: TestReplyMessage2 recieved at " + node.toString() + " from " + reply.getOrigin());
        // Sort nodes so that the order is deterministic
        String test = reply.getTest();
        //System.out.println("TestReplyReceiver2.java: Response message is '" + test + "'");
        //System.out.println("TestReplyReceiver2.java: receive comm="+comm+" message="+m);
    }

    public void timeout(int comm) throws IOException {
    	//System.out.println("TestReplyReceiver2.java: timeout comm="+comm);
    	assert (comm != 0);
    }


}