package com.spidercache.queue;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.planx.xmlstore.*;
import org.planx.routing.kademlia.operation.*;
import com.spidercache.dht.*;


public class QueueMessage extends OriginMessage  {
	String message;
    protected QueueMessage() {}

    public QueueMessage(Node origin, String content) {
        super((org.planx.routing.kademlia.Node) origin);
        this.message = content;
    }

    public QueueMessage(DataInput in) throws IOException {
        fromStream(in);
    }

    public byte code() {
        return DHTMessageFactoryImpl.CODE_OUT;
    }
    
    public String getMessage(){
		return message;
    }

    public String toString() {
        return message;
    }

	@Override
	public void toStream(DataOutput out) throws IOException {
        super.toStream(out);
		out.writeUTF(message);
	}

	@Override
	public void fromStream(DataInput in) throws IOException {
        super.fromStream(in);
        message = in.readUTF();
	}
}
