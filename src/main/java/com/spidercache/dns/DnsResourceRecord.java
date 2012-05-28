package com.spidercache.dns;

import com.spidercache.utils.Convert;

/**
 * Encapsulates a DNS resource record according to the specification detailed in
 * <a href="http://tools.ietf.org/html/rfc1035#section-4.1.3" target="_blank">RFC 1035</a>.
 * <p>
 * TODO Support message compression as described in 
 * <a href="http://tools.ietf.org/html/rfc1035#section-4.1.4" target="_blank">Section 4.1.4</a>
 * of the aforementioned RFC.
 * 
 * @author Dustin Buckenmeyer
 */
public class DnsResourceRecord {
	
	private String rname;
	private int rtype;
	private int rclass;
	private long ttl;
	private int rdlength;
	private String rdata;
	
	/**
	 * Creates a generic DnsResourceRecord for an Internet host address.
	 */
	public DnsResourceRecord() {
		rname = ".";
		rtype = 1;
		rclass = 1;
		ttl = 86400;
		rdlength = 4;
		rdata = "0.0.0.0";
	}
	
	/**
	 * Creates a DnsResourceRecord with control over all important parameters.
	 * 
	 * @param rname The domain name to which this record pertains.
	 * @param rtype The type of resource record.
	 * @param rclass The class of resource record.
	 * @param rdata An IP address associated with the domain name.
	 */
	public DnsResourceRecord(String rname, short rtype, short rclass, String rdata) {
		this.rname = rname;
		this.rtype = rtype;
		this.rclass = rclass;
		ttl = 86400;
		rdlength = 4;
		this.rdata = rdata;
	}
	
	/**
	 * Extracts the resource record information from a raw DNS message.
	 * 
	 * @param message A byte array containing the raw DNS message data.
	 * @param offset The offset into the message where the resource record information begins.
	 * @return The offset following the resource record information.
	 */
	public int parseResourceRecord(byte[] message, int offset) {
		short label = message[offset];
		rname = new String();
		
		if (label == 0) {
			rname = rname.concat(".");
		}
		
		while (label != 0) {
			offset++;
			rname = rname.concat(new String(message, offset, label) + ".");
			offset += label;
			label = message[offset];
		}
		
		rtype = (Convert.toUnsignedByte(message[offset+1]) << 8) | Convert.toUnsignedByte(message[offset+2]);
		rclass = (Convert.toUnsignedByte(message[offset+3]) << 8) | Convert.toUnsignedByte(message[offset+4]);
		ttl = Convert.toUnsignedByte(message[offset+5]);
		ttl = ttl << 8;
		ttl = ttl | Convert.toUnsignedByte(message[offset+6]);
		ttl = ttl << 8;
		ttl = ttl | Convert.toUnsignedByte(message[offset+7]);
		ttl = ttl << 8;
		ttl = ttl | Convert.toUnsignedByte(message[offset+8]);
		
		/*ttl = (((((
				Convert.toUnsignedByte(message[offset+5]) << 8)
				| Convert.toUnsignedByte(message[offset+6])) << 8)
				| Convert.toUnsignedByte(message[offset+7])) << 8)
				| Convert.toUnsignedByte(message[offset+8]);*/
		rdlength = (Convert.toUnsignedByte(message[offset+9]) << 8) | Convert.toUnsignedByte(message[offset+10]);
		
		offset += 11;
		
		// rdata needs processing!!!
		// NS, SOA, CNAME, PTR, A
		rdata = new String();
		for (int i = 0; i < rdlength; i++) {
			if (i != 0) {
				rdata = rdata.concat(".");
			}
			rdata = rdata.concat(Short.toString(Convert.toUnsignedByte(message[offset+i])));
		}
		
		return offset + rdlength;
	}
	
	/**
	 * Creates a byte array containing the raw resource record information based
	 * on the values stored in the DnsResourceRecord instance variables.
	 * 
	 * @return The raw DNS resource record.
	 */
	public byte[] assembleResourceRecord() {
		byte[] data = {};
		int length = 0, offset = 0;
		
		String[] pieces = rname.split("\\.");
		
		for (int i = 0; i < pieces.length; i++) {
			length += pieces[i].length();
		}
		
		// should probably be more dynamic
		length += pieces.length + 15;
		
		data = new byte[length];
		
		for (int i = 0; i < pieces.length; i++) {
			length = pieces[i].length();
			data[offset] = (byte) length;
			
			char[] chars = new char[length];
			pieces[i].getChars(0, length, chars, 0);
			
			for (int j = 0; j < length; j++) {
				data[offset + j + 1] = (byte) chars[j];
			}
			
			offset += length + 1;
		}
		
		data[offset] = (byte) 0;
		data[offset+1] = (byte) (rtype >>> 8);
		data[offset+2] = (byte) (rtype);
		data[offset+3] = (byte) (rclass >>> 8);
		data[offset+4] = (byte) (rclass);
		data[offset+5] = (byte) (ttl >>> 24);
		data[offset+6] = (byte) (ttl >>> 16);
		data[offset+7] = (byte) (ttl >>> 8);
		data[offset+8] = (byte) (ttl);
		data[offset+9] = (byte) (rdlength >>> 8);
		data[offset+10] = (byte) (rdlength);
		
		//if (rtype == 1 && rclass == 1) {
			offset += 11;
			pieces = rdata.split("\\.");
			
			for (int i = 0; i < pieces.length; i++) {
				data[offset+i] = (byte) (Integer.parseInt(pieces[i]));
			}
		//}
		
		return data;
	}
	
	public String getRname() { return rname; }
	
	public int getRtype() { return rtype; }
	
	public int getRclass() { return rclass; }
	
	public long getTtl() { return ttl; }
	
	public int getRdlength() { return rdlength; }
	
	public String getRdata() { return rdata; }
	
	public void setRname(String rname) { this.rname = rname; }
	
	public void setRtype(short rtype) { this.rtype = rtype; }
	
	public void setRclass(short rclass) { this.rclass = rclass; }
	
	public void setTtl(int ttl) { this.ttl = ttl; }
	
	public void setRdlength(short rdlength) { this.rdlength = rdlength; }
	
	public void setRdata(String rdata) { this.rdata = rdata; }
	
	public String toString() {
		String s = "RESOURCE RECORD";
		s = s.concat("\n\trname: " + rname);
		s = s.concat("\n\trtype: " + rtype);
		s = s.concat("\n\trclass: " + rclass);
		s = s.concat("\n\tttl: " + ttl);
		s = s.concat("\n\trdlength: " + rdlength);
		s = s.concat("\n\trdata: " + rdata);
		
		return s;
	}
	
}
