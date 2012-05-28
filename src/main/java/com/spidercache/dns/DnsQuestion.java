package com.spidercache.dns;

import com.spidercache.utils.Convert;

/**
 * Encapsulates a DNS question according to the specification detailed in
 * <a href="http://tools.ietf.org/html/rfc1035#section-4.1.2" target="_blank">RFC 1035</a>.
 * <p>
 * TODO Support message compression as described in 
 * <a href="http://tools.ietf.org/html/rfc1035#section-4.1.4" target="_blank">Section 4.1.4</a>
 * of the aforementioned RFC.
 * 
 * @author Dustin Buckenmeyer
 */
public class DnsQuestion {
	
	private String qname;
	private int qtype;
	private int qclass;
	
	/**
	 * Creates a generic DnsQuestion for an Internet host address.
	 */
	public DnsQuestion() {
		qname = ".";
		qtype = 1;
		qclass = 1;
	}
	
	/**
	 * Creates a DnsQuestion with control over all parameters.
	 * 
	 * @param qname The domain to look up.
	 * @param qtype The type of question.
	 * @param qclass The class of question.
	 */
	public DnsQuestion(String qname, short qtype, short qclass) {
		this.qname = qname;
		this.qtype = qtype;
		this.qclass = qclass;
	}
	
	/**
	 * Extracts the question information from a raw DNS message.
	 * 
	 * @param message A byte array containing the raw DNS message data.
	 * @param offset The offset into the message where the question information begins.
	 * @return The offset following the question information.
	 */
	public int parseQuestion(byte[] message, int offset) {
		short label = message[offset];
		qname = new String();
		
		if (label == 0) {
			qname = qname.concat(".");
		}
		
		while (label != 0) {
			offset++;
			qname = qname.concat(new String(message, offset, label) + ".");
			offset += label;
			label = message[offset];
		}
		
		qtype = (Convert.toUnsignedByte(message[offset+1]) << 8) | Convert.toUnsignedByte(message[offset+2]);
		qclass = (Convert.toUnsignedByte(message[offset+3]) << 8) | Convert.toUnsignedByte(message[offset+4]);
		
		return offset + 5;
	}
	
	
	/**
	 * Creates a byte array containing the raw question information based
	 * on the values stored in the DnsQuestion instance variables.
	 * 
	 * @return The raw DNS question.
	 */
	public byte[] assembleQuestion() {
		byte[] data = {};
		int length = 0, offset = 0;
		
		String[] pieces = qname.split("\\.");
		
		for (int i = 0; i < pieces.length; i++) {
			length += pieces[i].length();
		}
		
		length += pieces.length + 5;
		
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
		data[offset+1] = (byte) (qtype >>> 8);
		data[offset+2] = (byte) (qtype);
		data[offset+3] = (byte) (qclass >>> 8);
		data[offset+4] = (byte) (qclass);
		
		return data;
	}
	
	public String getQname() { return qname; }
	
	public int getQtype() { return qtype; }
	
	public int getQclass() { return qclass; }
	
	public void setQname(String qname) { this.qname = qname; }
	
	public void setQtype(short qtype) { this.qtype = qtype; }
	
	public void setQclass(short qclass) { this.qclass = qclass; }
	
	public String toString() {
		String s = "QUESTION";
		s = s.concat("\n\tqname: " + qname);
		s = s.concat("\n\tqtype: " + qtype);
		
		switch (qtype) {
		case 1:
			s = s.concat(" - A: IPv4 host address");
			break;
		case 2:
			s = s.concat(" - NS: authoritative name server");
			break;
		case 3:
			s = s.concat(" - MD: mail destination (obsolete, use MX)");
			break;
		case 4:
			s = s.concat(" - MF: mail forwarder (obsolete, use MX)");
			break;
		case 5:
			s = s.concat(" - CNAME: canonical name for an alias");
			break;
		case 6:
			s = s.concat(" - SOA: start of a zone of authority");
			break;
		case 7:
			s = s.concat(" - MB: mailox domain name (experimental)");
			break;
		case 8:
			s = s.concat(" - MG: mail group member (experimental)");
			break;
		case 9:
			s = s.concat(" - MR: mail rename domain name (experimental)");
			break;
		case 10:
			s = s.concat(" - NULL: a null resource record (experimental)");
			break;
		case 11:
			s = s.concat(" - WKS: well known service description");
			break;
		case 12:
			s = s.concat(" - PTR: domain name pointer");
			break;
		case 13:
			s = s.concat(" - HINFO: host information");
			break;
		case 14:
			s = s.concat(" - MINFO: mailbox information");
			break;
		case 15:
			s = s.concat(" - MX: mail exchange");
			break;
		case 16:
			s = s.concat(" - TXT: text strings");
			break;
		case 28:
			s = s.concat(" - AAAA: IPv6 host address");
			break;
		case 252:
			s = s.concat(" - AXFR: transfer of an entire zone");
			break;
		case 253:
			s = s.concat(" - MAILB: mailbox-related resource records (MB, MG or MR)");
			break;
		case 254:
			s = s.concat(" - MAILA: mail agent resource records (obsolete, see MX)");
			break;
		case 255:
			s = s.concat(" - *: all resource records");
			break;
		default:
			s = s.concat(" - unused");
		}
		
		s = s.concat("\n\tqclass: " + qclass);
		
		switch (qclass) {
		case 1:
			s = s.concat(" - IN: the Internet");
			break;
		case 2:
			s = s.concat(" - CS: the CSNET class (obsolete)");
			break;
		case 3:
			s = s.concat(" - CH: the CHAOS class");
			break;
		case 4:
			s = s.concat(" - HS: Hesiod");
			break;
		case 255:
			s = s.concat(" - *: any class");
			break;
		default:
			s = s.concat(" - unused");
		}
		
		return s;
	}
	
}
