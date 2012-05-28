package com.spidercache.queue;

import java.io.IOException;
import org.planx.routing.kademlia.Node;
import org.planx.routing.kademlia.Space;
import org.planx.routing.messaging.MessageServer;
import org.planx.routing.messaging.Message;
import org.planx.routing.messaging.UnknownMessageException;
import org.planx.routing.kademlia.operation.OriginMessage;
import org.planx.routing.kademlia.operation.OriginReceiver;

public class QueueReceiver extends OriginReceiver {
    public QueueReceiver(MessageServer server, Node local, Space space) {
        super(server, local, space);
    }

    public void receive(Message incoming, int comm) throws IOException,
                                               UnknownMessageException {
        super.receive(incoming, comm);

        QueueMessage myMessage = (QueueMessage) incoming;
        OriginMessage mess = (OriginMessage) incoming;
    	System.out.println("TestReceiver.java: TestMessage \""+myMessage.toString()+ "\" recieved at " + local.toString() + " from " + mess.getOrigin());
        Node origin = mess.getOrigin();        
        //List nodes = space.getClosestNodes(mess.getLookupId());
        Message reply = new InReplyMessage(local,"","Test");
        server.reply(comm, reply, origin.getInetAddress(), origin.getPort());
        System.out.println("ReplyMessage sent to " + origin.toString() + " from " + local.toString());
    }
}