package com.spidercache.dns;

import com.spidercache.utils.Convert;

/**
 * Encapsulates a DNS header according to the specification detailed in
 * <a href="http://tools.ietf.org/html/rfc1035#section-4.1.1" target="_blank">RFC 1035</a>.
 * <p>
 * TODO Support message compression as described in 
 * <a href="http://tools.ietf.org/html/rfc1035#section-4.1.4" target="_blank">Section 4.1.4</a>
 * of the aforementioned RFC.
 * 
 * @author Dustin Buckenmeyer
 */
public class DnsHeader {
	
	private int id;
	private byte qr;
	private byte opcode;
	private byte aa;
	private byte tc;
	private byte rd;
	private byte ra;
	private byte z;
	private byte rcode;
	private int qdcount;
	private int ancount;
	private int nscount;
	private int arcount;
	
	/**
	 * Creates a DnsHeader suitable for use in a DNS query.
	 */
	public DnsHeader() {
		id = 0;
		qr = 0;
		opcode = 0;
		aa = 0;
		tc = 0;
		rd = 1;
		ra = 0;
		z = 0;
		rcode = 0;
		qdcount = 0;
		ancount = 0;
		nscount = 0;
		arcount = 0;
	}
	
	/**
	 * Creates a DnsHeader suitable for use in a DNS response.
	 * <p>
	 * The requested parameters should be copied from the associated DNS query.
	 * 
	 * @param id The message identifier.
	 * @param opcode Designates the type of message.
	 * @param rd Specifies whether recursion was desired by the query.
	 * @param qdcount The number of {@link DnsQuestion}s present in the query.
	 */
	public DnsHeader(short id, byte opcode, byte rd, short qdcount) {
		this.id = id;
		qr = 1;
		this.opcode = opcode;
		aa = 1;
		tc = 0;
		this.rd = rd;
		ra = 0;
		z = 0;
		rcode = 0;
		this.qdcount = qdcount;
		this.ancount = qdcount;
		nscount = 0;
		arcount = 0;
	}
	
	/**
	 * Extracts the header information from a raw DNS message.
	 * 
	 * @param message A byte array containing the raw DNS message data.
	 * @param offset The offset into the message where the header information begins.
	 * @return The offset following the header information.
	 */
	public int parseHeader(byte[] message, int offset) {
		id = (message[offset] << 8) | Convert.toUnsignedByte(message[offset+1]);
		qr = 		(byte) ((message[offset+2] & 0x80) >>> 7);
		opcode = 	(byte) ((message[offset+2] & 0x78) >>> 3);
		aa = 		(byte) ((message[offset+2] & 0x04) >>> 2);
		tc = 		(byte) ((message[offset+2] & 0x02) >>> 1);
		rd = 		(byte) (message[offset+2] & 0x01);
		ra = 		(byte) ((message[offset+3] & 0x80) >>> 7);
		z = 		(byte) ((message[offset+3] & 0x70) >>> 4);
		rcode = 	(byte) (message[offset+3] & 0x0F);
		qdcount = (Convert.toUnsignedByte(message[offset+4]) << 8) | Convert.toUnsignedByte(message[offset+5]);
		ancount = (Convert.toUnsignedByte(message[offset+6]) << 8) | Convert.toUnsignedByte(message[offset+7]);
		nscount = (Convert.toUnsignedByte(message[offset+8]) << 8) | Convert.toUnsignedByte(message[offset+9]);
		arcount = (Convert.toUnsignedByte(message[offset+10]) << 8) | Convert.toUnsignedByte(message[offset+11]);
		return offset + 12;
	}
	
	/**
	 * Creates a byte array containing the raw header information based
	 * on the values stored in the DnsHeader instance variables.
	 * 
	 * @return The raw DNS header.
	 */
	public byte[] assembleHeader() {
		byte[] data = new byte[12];
		data[0] = (byte) (id >>> 8);
		data[1] = (byte) id;
		data[2] = (byte) ((((((((qr << 4) | opcode) << 1) | aa) << 1) | tc) << 1) | rd);
		data[3] = (byte) ((((ra << 3) | z) << 4) | rcode);
		data[4] = (byte) (qdcount >>> 8);
		data[5] = (byte) qdcount;
		data[6] = (byte) (ancount >>> 8);
		data[7] = (byte) ancount;
		data[8] = (byte) (nscount >>> 8);
		data[9] = (byte) nscount;
		data[10] = (byte) (arcount >>> 8);
		data[11] = (byte) arcount;
		return data;
	}
	
	public int getId() { return id; }
	
	public byte getQr() { return qr; }
	
	public byte getOpcode() { return opcode; }
	
	public byte getAa() { return aa; }
	
	public byte getTc() { return tc; }
	
	public byte getRd() { return rd; }
	
	public byte getRa() { return ra; }
	
	public byte getZ() { return z; }
	
	public byte getRcode() { return rcode; }
	
	public int getQdcount() { return qdcount; }
	
	public int getAncount() { return ancount; }
	
	public int getNscount() { return nscount; }
	
	public int getArcount() { return arcount; }
	
	public void setId(short id) { this.id = id; }
	
	public void setQr(byte qr) { this.qr = qr; }
	
	public void setOpcode(byte opcode) { this.opcode = opcode; }
	
	public void setAa(byte aa) { this.aa = aa; }
	
	public void setTc(byte tc) { this.tc = tc; }
	
	public void setRd(byte rd) { this.rd = rd; }
	
	public void setRa(byte ra) { this.ra = ra; }
	
	public void setZ(byte z) { this.z = z; }
	
	public void setRcode(byte rcode) { this.rcode = rcode; }
	
	public void setQdcount(short qdcount) { this.qdcount = qdcount; }
	
	public void setAncount(short ancount) { this.ancount = ancount; }
	
	public void setNscount(short nscount) { this.nscount = nscount; }
	
	public void setArcount(short arcount) { this.arcount = arcount; }
	
	public String toString() {
		String s = "HEADER";
		s = s.concat("\n\tid: " + id);
		s = s.concat("\n\tqr: " + qr);
		
		switch(qr) {
		case 0:
			s = s.concat(" - request");
			break;
		case 1:
			s = s.concat(" - response");
			break;
		default:
			s = s.concat(" - should be 0 or 1");
		}
		
		s = s.concat("\n\topcode: " + opcode);
		
		switch(opcode) {
		case 0:
			s = s.concat(" - standard query");
			break;
		case 1:
			s = s.concat(" - inverse query");
			break;
		case 2:
			s = s.concat(" - server status query");
			break;
		default:
			s = s.concat(" - unused");
		}
		
		s = s.concat("\n\taa: " + aa);
		s = s.concat("\n\ttc: " + tc);
		
		if (tc == 1) {
			s = s.concat(" (message has been truncated)");
		}
		
		s = s.concat("\n\trd: " + rd);
		s = s.concat("\n\tra: " + ra);
		s = s.concat("\n\tz: " + z + " <-- must be zero!");
		s = s.concat("\n\trcode: " + rcode);
		
		switch (rcode) {
		case 0:
			s = s.concat(" - no error");
			break;
		case 1:
			s = s.concat(" - format error");
			break;
		case 2:
			s = s.concat(" - server failure");
			break;
		case 3:
			s = s.concat(" - name error");
			break;
		case 4:
			s = s.concat(" - not implemented");
			break;
		case 5:
			s = s.concat(" - refused");
			break;
		default:
			s = s.concat(" - unused");
		}
		
		s = s.concat("\n\tqdcount: " + qdcount);
		s = s.concat("\n\tancount: " + ancount);
		s = s.concat("\n\tnscount: " + nscount);
		s = s.concat("\n\tarcount: " + arcount);
		
		return s;
	}
	
}