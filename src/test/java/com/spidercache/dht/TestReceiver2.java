package com.spidercache.dht;

import java.io.IOException;
import java.util.List;
import org.planx.routing.kademlia.*;
import org.planx.routing.kademlia.operation.*;
import org.planx.routing.messaging.*;

/**
 * 
 **/
public class TestReceiver2 extends OriginReceiver {
    public TestReceiver2(MessageServer server, Node local, Space space) {
        super(server, local, space);
    }

    public void receive(Message incoming, int comm) throws IOException,
                                               UnknownMessageException {
        super.receive(incoming, comm);

        TestMessage2 testMessage = (TestMessage2) incoming;
        Node origin = testMessage.getOrigin();
    	System.out.println("TestReceiver2.java: TestMessage2 recieved at " + local.toString() + " from " + origin.toString());
        String test = testMessage.getTest();
        System.out.println("TestReceiver2.java: Message is '" + test + "'" );
        Message reply = new TestReplyMessage2(local, "Success!");
        server.reply(comm, reply, origin.getInetAddress(), origin.getPort());
        System.out.println("TestReceiver2.java: TestReplyMessage2 sent to " + origin.toString() + " from " + local.toString());
    }
}
