package com.spidercache.dht;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.planx.routing.kademlia.*;
import org.planx.routing.kademlia.operation.*;
import org.planx.routing.messaging.*;

import com.spidercache.dht.DHTMessageFactoryImpl;

public class TestReplyMessage2 extends OriginMessage {
	 String test;

	    protected TestReplyMessage2() {}

	    public TestReplyMessage2(Node origin, String test) {
	        super(origin);
	        this.test = test;
	    }

	    public TestReplyMessage2(DataInput in) throws IOException {
	        fromStream(in);
	    }

	    public void fromStream(DataInput in) throws IOException {
	        super.fromStream(in);
	        test = in.readUTF();
	    }

	    public void toStream(DataOutput out) throws IOException {
	        super.toStream(out);
			out.writeUTF(test);
	    }

	    public byte code() {
	        return DHTMessageFactoryImpl.CODE_TEST_2_REPLY;
	    }

	    public String getTest() {
	        return test;
	    }

	    public String toString() {
	        return "TestReplyMessage[origin="+origin+",test="+test+"]";
	    }
}