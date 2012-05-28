package com.spidercache.dht;

import java.io.DataInput;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;


import org.planx.routing.messaging.*;
import org.planx.routing.kademlia.operation.*;
import org.planx.routing.kademlia.*;


/**
 * Modified MessageFactoryImpl.java from plan-x library.
 * 
 * Essentially this class translates numbers into classes. Given a message code
 * it can create a Message object or a Receiver.
 **/
public class DHTMessageFactoryImpl implements MessageFactory {
    public static final byte CODE_TEST   = 0x01;
    public static final byte CODE_TEST_REPLY   = 0x02;
    public static final byte CODE_TEST_2   = 0x03;
    public static final byte CODE_TEST_2_REPLY   = 0x04;    

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
        case CODE_TEST:
            return new TestMessage(in);
        case CODE_TEST_REPLY:
            return new TestReplyMessage(in);  
        case CODE_TEST_2:
            return new TestMessage2(in);  
        case CODE_TEST_2_REPLY:
            return new TestReplyMessage2(in);              
        default:
            throw new UnknownMessageException("Unknown message code: "+code);
        }
    }

    public Receiver createReceiver(byte code, MessageServer server) {
        switch (code) {
        case CODE_TEST:
            return new TestReceiver(server, local, space);     
        case CODE_TEST_2:
            return new TestReceiver2(server, local, space);         
        default:
            return null;
        }
    }
}