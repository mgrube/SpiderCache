package com.spidercache.dht;

import java.io.IOException;
import java.util.List;
import org.planx.routing.kademlia.*;
import org.planx.routing.kademlia.operation.*;
import org.planx.routing.messaging.*;

/**
 * 
 **/
public class TestReceiver extends OriginReceiver {
    public TestReceiver(MessageServer server, Node local, Space space) {
        super(server, local, space);
    }

    public void receive(Message incoming, int comm) throws IOException,
                                               UnknownMessageException {
        super.receive(incoming, comm);

        TestMessage testMessage = (TestMessage) incoming;
        LookupMessage mess = (LookupMessage) incoming;
    	System.out.println("TestReceiver.java: TestMessage recieved at " + local.toString() + " from " + mess.getOrigin());
        Node origin = mess.getOrigin();
        List nodes = space.getClosestNodes(mess.getLookupId());
        Message reply = new TestReplyMessage(local, nodes);
        server.reply(comm, reply, origin.getInetAddress(), origin.getPort());
        System.out.println("TestReceiver.java: TestReplyMessage sent to " + origin.toString() + " from " + local.toString());
    }
}
