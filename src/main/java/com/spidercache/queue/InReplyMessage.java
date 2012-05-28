package com.spidercache.queue;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
//import java.io.UnsupportedEncodingException;

import org.planx.routing.kademlia.Node;
import org.planx.routing.kademlia.operation.*;

import com.spidercache.dht.*;


public class InReplyMessage extends OriginMessage {
	 String received;
	 String myStatus;

	    protected InReplyMessage() {}

	    public InReplyMessage(Node origin, String receivedMessage,String status) {
	        super((org.planx.routing.kademlia.Node) origin);
	        this.received = receivedMessage;
	        this.myStatus = status;
	    }

	    public InReplyMessage(DataInput in) throws IOException {
	        fromStream(in);
	    }

	    public void fromStream(DataInput in) throws IOException {
	        super.fromStream(in);
	        received = in.readUTF();
	    }

	    public void toStream(DataOutput out) throws IOException {
	        super.toStream(out);
			out.writeUTF(received);
	    }

	    public byte code() {
	        return DHTMessageFactoryImpl.CODE_INREPLY;
	    }

	    public String getReceived() {
	        return received;
	    }

	    public String toString() {
	        return "TestReplyMessage2[origin="+origin+",message="+received+"]";
	    	//return "The secret password is "+message;
	    }
}