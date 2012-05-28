package com.spidercache.dht;

import java.io.DataInput;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.planx.routing.Identifier;
import org.planx.routing.kademlia.Node;
import org.planx.routing.kademlia.operation.LookupMessage;
import org.planx.routing.messaging.Message;
import org.planx.routing.messaging.MessageFactory;
import org.planx.routing.messaging.MessageServer;
import org.planx.routing.messaging.Receiver;
import org.planx.routing.messaging.UnknownMessageException;


import com.spidercache.dht.DHTMessageFactoryImpl;

public class TestMessage extends LookupMessage implements MessageFactory {
    protected TestMessage() {}

    public TestMessage(Node origin, Identifier lookup) {
        super(origin, lookup);
    }

    public TestMessage(DataInput in) throws IOException {
        super(in);
    }

    public byte code() {
        return DHTMessageFactoryImpl.CODE_TEST;
    }


    public String toString() {
        return "NodeLookupMessage[origin="+origin+",lookup="+lookup+"]";
    }

	@Override
	public Message createMessage(byte arg0, DataInput arg1) throws IOException,
			UnknownMessageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Receiver createReceiver(byte arg0, MessageServer arg1) {
		// TODO Auto-generated method stub
		return null;
	}
}
