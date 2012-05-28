package com.spidercache.dht;

import java.io.DataInput;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import org.planx.routing.*;
import org.planx.routing.kademlia.operation.*;
import org.planx.routing.kademlia.Node;
import org.planx.routing.messaging.Message;
import org.planx.routing.messaging.MessageFactory;
import org.planx.routing.kademlia.Space;
import org.planx.routing.messaging.MessageServer;
import org.planx.routing.messaging.Receiver;
import org.planx.routing.messaging.UnknownMessageException;

import com.spidercache.queue.QueueMessage;
import com.spidercache.queue.InReplyMessage;
import com.spidercache.queue.QueueReceiver;


/**
 * Modified MessageFactoryImpl.java from plan-x library.
 * 
 * Essentially this class translates numbers into classes. Given a message code
 * it can create a Message object or a Receiver.
 **/
public class DHTMessageFactoryImpl implements MessageFactory {
    public static final byte CODE_OUT   = 0x01;
    public static final byte CODE_INREPLY   = 0x02;
    //public static final byte CODE_OUTIN   = 0x03;
    //public static final byte CODE_TEST_2_REPLY   = 0x04;    

    private Map<String, Serializable> localMap;
    private Node local;
    private Space space;

    public DHTMessageFactoryImpl(Map<String, Serializable> localMap, Node local, Space space) {
        this.localMap = localMap;
        this.local = local;
        this.space = space;
    }

    public Message createMessage(byte code, DataInput in)
                   throws IOException, UnknownMessageException {
        switch (code) {
        case CODE_OUT:
            return new QueueMessage(in);
        case CODE_INREPLY:
            return new InReplyMessage(in);               
        default:
            throw new UnknownMessageException("Unknown message code: "+code);
        }
    }

    public Receiver createReceiver(byte code, MessageServer server) {
        switch (code) {
        case CODE_OUT:
            return new QueueReceiver(server, local, space);     
        case CODE_INREPLY:
            return new QueueReceiver(server, local, space);         
        default:
            return null;
        }
    }
}