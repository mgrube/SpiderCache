package com.spidercache.dht;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.planx.routing.kademlia.*;
import org.planx.routing.kademlia.operation.*;

import com.spidercache.dht.DHTMessageFactoryImpl;

// These are not JUnit tests!

public class TestMessage2 extends OriginMessage  {
	String test;
    protected TestMessage2() {}

    public TestMessage2(Node origin, String test) {
        super(origin);
        this.test = test;
    }

    public TestMessage2(DataInput in) throws IOException {
        fromStream(in);
    }

    public byte code() {
        return DHTMessageFactoryImpl.CODE_TEST_2;
    }
    
    public String getTest(){
		return test;
    }

    public String toString() {
        return "NodeLookupMessage[test="+test+"]";
    }

	@Override
	public void toStream(DataOutput out) throws IOException {
        super.toStream(out);
		out.writeUTF(test);
	}

	@Override
	public void fromStream(DataInput in) throws IOException {
        super.fromStream(in);
        test = in.readUTF();
	}
}
