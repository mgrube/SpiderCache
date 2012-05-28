package com.spidercache.dns;

import java.util.ArrayList;

/**
 * Encapsulates a DNS message according to the specification detailed in
 * <a href="http://tools.ietf.org/html/rfc1035#section-4" target="_blank">RFC 1035</a>.
 * <p>
 * TODO Support message compression as described in 
 * <a href="http://tools.ietf.org/html/rfc1035#section-4.1.4" target="_blank">Section 4.1.4</a>
 * of the aforementioned RFC.
 * 
 * @author Dustin Buckenmeyer
 */
public class DnsMessage {
	private byte[] message;
	
	private DnsHeader header;
	private DnsQuestion[] questions = {};
	private DnsResourceRecord[] answers = {};
	private DnsResourceRecord[] authorities = {};
	private DnsResourceRecord[] additional = {};
	
	/**
	 * Creates an empty DnsMessage.
	 */
	public DnsMessage() {
		header = new DnsHeader();
	}
	
	/**
	 * Creates a DnsMessage suitable for use as a DNS query.
	 * 
	 * @param header The {@link DnsHeader} to use.
	 * @param questions An array of {@link DnsQuestion}s to ask.
	 */
	public DnsMessage(DnsHeader header, DnsQuestion[] questions) {
		this.header = header;
		this.questions = questions;
	}
	
	/**
	 * Creates a DnsMessage suitable for use as a DNS response.
	 * 
	 * @param header The {@link DnsHeader} to use.
	 * @param questions An array of {@link DnsQuestion}s, which should be copied from the associated DNS query.
	 * @param answers An array of {@link DnsResourceRecord}s containing answers to the specified {@link DnsQuestion}s.
	 */
	public DnsMessage(DnsHeader header, DnsQuestion[] questions, DnsResourceRecord[] answers) {
		this.header = header;
		this.questions = questions;
		this.answers = answers;
	}
	
	/**
	 * Creates a DnsMessage from a raw DNS message.
	 * 
	 * @param data A byte array containing the raw DNS message data.
	 */
	public DnsMessage(byte[] data) {
		header = new DnsHeader();
		this.message = data;
		parseMessage();
	}
	
	/**
	 * Extracts all information from a raw DNS message.
	 */
	public void parseMessage() {
		int offset = 0;
		offset = parseHeaderSection(offset);
		offset = parseQuestionSection(offset);
		offset = parseAnswerSection(offset);
		offset = parseAuthoritySection(offset);
		offset = parseAdditionalSection(offset);
	}
	
	/**
	 * Creates a byte array containing the raw message information based
	 * on the values stored in the DnsMessage instance variables.
	 * 
	 * @return The raw DNS message.
	 */
	public byte[] assembleMessage() {
		byte[] data = {};
		ArrayList<byte[]> sections = new ArrayList<byte[]>();
		int length = 0, offset = 0;
		
		sections.add(header.assembleHeader());
		
		for (int i = 0; i < questions.length; i++) {
			sections.add(questions[i].assembleQuestion());
		}
		
		for (int i = 0; i < answers.length; i++) {
			sections.add(answers[i].assembleResourceRecord());
		}
		
		for (int i = 0; i < authorities.length; i++) {
			sections.add(authorities[i].assembleResourceRecord());
		}
		
		for (int i = 0; i < additional.length; i++) {
			sections.add(additional[i].assembleResourceRecord());
		}
		
		int bound = sections.size();
		for (int i = 0; i < bound; i++) {
			length += sections.get(i).length;
		}
		
		data = new byte[length];
		
		for (int i = 0; i < bound; i++) {
			byte[] temp = sections.get(i);
			
			for (int j = 0; j < temp.length; j++) {
				data[offset + j] = temp[j];
			}
			
			offset += temp.length;
		}
		
		return data;
	}
	
	/**
	 * Handles parsing the Header section of the raw DNS message.
	 * 
	 * @param offset The offset into the message where the Header section begins.
	 * @return The offset following the Header section.
	 */
	private int parseHeaderSection(int offset) {
		return header.parseHeader(message, offset);
	}
	
	/**
	 * Handles parsing the Question section of the raw DNS message.
	 * 
	 * @param offset The offset into the message where the Question section begins.
	 * @return The offset following the Question section.
	 */
	private int parseQuestionSection(int offset) {
		int qdcount = header.getQdcount();
		questions = new DnsQuestion[qdcount];
		
		for (int i = 0; i < qdcount; i++) {
			questions[i] = new DnsQuestion();
			offset = questions[i].parseQuestion(message, offset);
		}
		
		return offset;
	}
	
	/**
	 * Handles parsing the Answer section of the raw DNS message.
	 * 
	 * @param offset The offset into the message where the Answer section begins.
	 * @return The offset following the Answer section.
	 */
	private int parseAnswerSection(int offset) {
		int ancount = header.getAncount();
		answers = new DnsResourceRecord[ancount];
		
		for (int i = 0; i < ancount; i++) {
			answers[i] = new DnsResourceRecord();
			offset = answers[i].parseResourceRecord(message, offset); 
		}
		
		return offset;
	}
	
	/**
	 * Handles parsing the Authority section of the raw DNS message.
	 * 
	 * @param offset The offset into the message where the Authority section begins.
	 * @return The offset following the Authority section.
	 */
	private int parseAuthoritySection(int offset) {
		int nscount = header.getNscount();
		authorities = new DnsResourceRecord[nscount];
		
		for (int i = 0; i < nscount; i++) {
			authorities[i] = new DnsResourceRecord();
			offset = authorities[i].parseResourceRecord(message, offset); 
		}
		
		return offset;
	}
	
	/**
	 * Handles parsing the Additional section of the raw DNS message.
	 * 
	 * @param offset The offset into the message where the Additional section begins.
	 * @return The offset following the Additional section.
	 */
	private int parseAdditionalSection(int offset) {
		int arcount = header.getArcount();
		additional = new DnsResourceRecord[arcount];
		
		for (int i = 0; i < arcount; i++) {
			additional[i] = new DnsResourceRecord();
			offset = additional[i].parseResourceRecord(message, offset); 
		}
		
		return offset;
	}
	
	public DnsHeader getHeader() { return header; }
	
	public DnsQuestion[] getQuestions() { return questions; }
	
	public DnsResourceRecord[] getAnswers() { return answers; }
	
	public DnsResourceRecord[] getAuthorities() { return authorities; }
	
	public DnsResourceRecord[] getAdditional() { return additional; }
	
	public void setHeader(DnsHeader header) { this.header = header; }
	
	public void setQuestions(DnsQuestion[] questions) {
		this.questions = new DnsQuestion[questions.length];
		
		for (int i = 0; i < questions.length; i++) {
			//this.questions[i] = new DnsQuestion();
			this.questions[i] = questions[i];
		}
	}
	
	public void setAnswers(DnsResourceRecord[] answers) {
		this.answers = new DnsResourceRecord[answers.length];
		
		for (int i = 0; i < answers.length; i++) {
			//this.answers[i] = new DnsResourceRecord();
			this.answers[i] = answers[i];
		}
	}
	
	public void setAuthorities(DnsResourceRecord[] authorities) {
		this.authorities = new DnsResourceRecord[authorities.length];
		
		for (int i = 0; i < authorities.length; i++) {
			//this.authorities[i] = new DnsResourceRecord();
			this.authorities[i] = authorities[i];
		}
	}
	
	public void setAdditional(DnsResourceRecord[] additional) {
		this.additional = new DnsResourceRecord[additional.length];
		
		for (int i = 0; i < additional.length; i++) {
			//this.additional[i] = new DnsResourceRecord();
			this.additional[i] = additional[i];
		}
	}

	public String toString() {
		String s = "\n****************************************";
		s = s.concat("\n" + header.toString());
		
		if (questions.length == 0) {
			s = s.concat("\n\tnone");
		}
		
		for (int i = 0; i < questions.length; i++) {
			s = s.concat("\n" + questions[i].toString());
		}
		
		s = s.concat("\nANSWERS");
		
		if (answers.length == 0) {
			s = s.concat("\n\tnone");
		}
		
		for (int i = 0; i < answers.length; i++) {
			s = s.concat("\n" + answers[i].toString());
		}
		
		s = s.concat("\nAUTHORITIES");
		
		if (authorities.length == 0) {
			s = s.concat("\n\tnone");
		}
		
		for (int i = 0; i < authorities.length; i++) {
			s = s.concat("\n" + authorities[i].toString());
		}
		
		s = s.concat("\nADDITIONAL");
		
		if (additional.length == 0) {
			s = s.concat("\n\tnone");
		}
		
		for (int i = 0; i < additional.length; i++) {
			s = s.concat("\n" + additional[i].toString());
		}
		
		s = s.concat("\n****************************************");
		
		return s;
	}

}