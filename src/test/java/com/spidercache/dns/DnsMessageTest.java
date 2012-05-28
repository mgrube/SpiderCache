package com.spidercache.dns;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Verifies the correct functionality of the {@link DnsMessage} class.
 * The method names correspond to the functions they are testing.
 * 
 * @author Dustin Buckenmeyer
 */
public class DnsMessageTest {

	@Test
	public void parseMessage1() {
		DnsHeader header = new DnsHeader((short) 43, (byte) 2, (byte) 1, (short) 1);
		DnsQuestion[] questions = new DnsQuestion[1];
		questions[0] = new DnsQuestion("www.reddit.com.", (short) 3, (short) 8);
		DnsResourceRecord[] answers = new DnsResourceRecord[1];
		answers[0] = new DnsResourceRecord("www.reddit.com.", (short) 6, (short) 5, "192.168.3.4");
		
		DnsMessage m = new DnsMessage(header, questions, answers);
		
		assertEquals(43, m.getHeader().getId());
		assertEquals(1, m.getHeader().getQr());
		assertEquals(2, m.getHeader().getOpcode());
		assertEquals(1, m.getHeader().getAa());
		assertEquals(0, m.getHeader().getTc());
		assertEquals(1, m.getHeader().getRd());
		assertEquals(0, m.getHeader().getRa());
		assertEquals(0, m.getHeader().getZ());
		assertEquals(0, m.getHeader().getRcode());
		assertEquals(1, m.getHeader().getQdcount());
		assertEquals(1, m.getHeader().getAncount());
		assertEquals(0, m.getHeader().getNscount());
		assertEquals(0, m.getHeader().getArcount());
		
		DnsQuestion[] returnedQuestions = m.getQuestions();
		
		assertEquals(1, returnedQuestions.length);
		assertEquals("www.reddit.com.", returnedQuestions[0].getQname());
		assertEquals(3, returnedQuestions[0].getQtype());
		assertEquals(8, returnedQuestions[0].getQclass());
		
		DnsResourceRecord[] returnedAnswers = m.getAnswers();
		
		assertEquals(1, returnedAnswers.length);
		assertEquals("www.reddit.com.", returnedAnswers[0].getRname());
		assertEquals(6, returnedAnswers[0].getRtype());
		assertEquals(5, returnedAnswers[0].getRclass());
		assertEquals(86400, returnedAnswers[0].getTtl());
		assertEquals(4, returnedAnswers[0].getRdlength());
		assertEquals("192.168.3.4", returnedAnswers[0].getRdata());
		
		assertEquals(0, m.getAuthorities().length);
		assertEquals(0, m.getAdditional().length);
	}
	
	@Test
	public void parseMessage2() {
		DnsHeader header = new DnsHeader();
		header.setId((short) 274);
		header.setQdcount((short) 4);
		DnsQuestion[] questions = new DnsQuestion[4];
		questions[0] = new DnsQuestion();
		questions[1] = new DnsQuestion("blog.msdn.com.", (short) 2, (short) 3);
		questions[2] = new DnsQuestion("www.netflix.com.", (short) 1, (short) 255);
		questions[3] = new DnsQuestion("www.theused.net.", (short) 16, (short) 7);
		
		DnsMessage m = new DnsMessage(header, questions);
		
		assertEquals(274, m.getHeader().getId());
		assertEquals(0, m.getHeader().getQr());
		assertEquals(0, m.getHeader().getOpcode());
		assertEquals(0, m.getHeader().getAa());
		assertEquals(0, m.getHeader().getTc());
		assertEquals(1, m.getHeader().getRd());
		assertEquals(0, m.getHeader().getRa());
		assertEquals(0, m.getHeader().getZ());
		assertEquals(0, m.getHeader().getRcode());
		assertEquals(4, m.getHeader().getQdcount());
		assertEquals(0, m.getHeader().getAncount());
		assertEquals(0, m.getHeader().getNscount());
		assertEquals(0, m.getHeader().getArcount());
		
		DnsQuestion[] returnedQuestions = m.getQuestions();
		
		assertEquals(4, returnedQuestions.length);
		assertEquals(".", returnedQuestions[0].getQname());
		assertEquals(1, returnedQuestions[0].getQtype());
		assertEquals(1, returnedQuestions[0].getQclass());
		assertEquals("blog.msdn.com.", returnedQuestions[1].getQname());
		assertEquals(2, returnedQuestions[1].getQtype());
		assertEquals(3, returnedQuestions[1].getQclass());
		assertEquals("www.netflix.com.", returnedQuestions[2].getQname());
		assertEquals(1, returnedQuestions[2].getQtype());
		assertEquals(255, returnedQuestions[2].getQclass());
		assertEquals("www.theused.net.", returnedQuestions[3].getQname());
		assertEquals(16, returnedQuestions[3].getQtype());
		assertEquals(7, returnedQuestions[3].getQclass());
		
		assertEquals(0, m.getAnswers().length);
		assertEquals(0, m.getAuthorities().length);
		assertEquals(0, m.getAdditional().length);
	}
	
	@Test
	public void assembleMessage1() {
		DnsHeader header = new DnsHeader((short) 43, (byte) 2, (byte) 1, (short) 1);
		DnsQuestion[] questions = new DnsQuestion[1];
		questions[0] = new DnsQuestion("www.reddit.com.", (short) 3, (short) 8);
		DnsResourceRecord[] answers = new DnsResourceRecord[1];
		answers[0] = new DnsResourceRecord("www.reddit.com.", (short) 6, (short) 5, "192.168.3.4");
		
		DnsMessage message = new DnsMessage(header, questions, answers);
		DnsMessage m = new DnsMessage(message.assembleMessage());
		
		assertEquals(43, m.getHeader().getId());
		assertEquals(1, m.getHeader().getQr());
		assertEquals(2, m.getHeader().getOpcode());
		assertEquals(1, m.getHeader().getAa());
		assertEquals(0, m.getHeader().getTc());
		assertEquals(1, m.getHeader().getRd());
		assertEquals(0, m.getHeader().getRa());
		assertEquals(0, m.getHeader().getZ());
		assertEquals(0, m.getHeader().getRcode());
		assertEquals(1, m.getHeader().getQdcount());
		assertEquals(1, m.getHeader().getAncount());
		assertEquals(0, m.getHeader().getNscount());
		assertEquals(0, m.getHeader().getArcount());
		
		DnsQuestion[] returnedQuestions = m.getQuestions();
		
		assertEquals(1, returnedQuestions.length);
		assertEquals("www.reddit.com.", returnedQuestions[0].getQname());
		assertEquals(3, returnedQuestions[0].getQtype());
		assertEquals(8, returnedQuestions[0].getQclass());
		
		DnsResourceRecord[] returnedAnswers = m.getAnswers();
		
		assertEquals(1, returnedAnswers.length);
		assertEquals("www.reddit.com.", returnedAnswers[0].getRname());
		assertEquals(6, returnedAnswers[0].getRtype());
		assertEquals(5, returnedAnswers[0].getRclass());
		assertEquals(86400, returnedAnswers[0].getTtl());
		assertEquals(4, returnedAnswers[0].getRdlength());
		assertEquals("192.168.3.4", returnedAnswers[0].getRdata());
		
		assertEquals(0, m.getAuthorities().length);
		assertEquals(0, m.getAdditional().length);
	}
	
	@Test
	public void assembleMessage2() {
		DnsHeader header = new DnsHeader();
		header.setId((short) 274);
		header.setQdcount((short) 4);
		DnsQuestion[] questions = new DnsQuestion[4];
		questions[0] = new DnsQuestion();
		questions[1] = new DnsQuestion("blog.msdn.com.", (short) 2, (short) 3);
		questions[2] = new DnsQuestion("www.netflix.com.", (short) 1, (short) 255);
		questions[3] = new DnsQuestion("www.theused.net.", (short) 16, (short) 7);
		
		DnsMessage message = new DnsMessage(header, questions);
		DnsMessage m = new DnsMessage(message.assembleMessage());
		
		assertEquals(274, m.getHeader().getId());
		assertEquals(0, m.getHeader().getQr());
		assertEquals(0, m.getHeader().getOpcode());
		assertEquals(0, m.getHeader().getAa());
		assertEquals(0, m.getHeader().getTc());
		assertEquals(1, m.getHeader().getRd());
		assertEquals(0, m.getHeader().getRa());
		assertEquals(0, m.getHeader().getZ());
		assertEquals(0, m.getHeader().getRcode());
		assertEquals(4, m.getHeader().getQdcount());
		assertEquals(0, m.getHeader().getAncount());
		assertEquals(0, m.getHeader().getNscount());
		assertEquals(0, m.getHeader().getArcount());
		
		DnsQuestion[] returnedQuestions = m.getQuestions();
		
		assertEquals(4, returnedQuestions.length);
		assertEquals(".", returnedQuestions[0].getQname());
		assertEquals(1, returnedQuestions[0].getQtype());
		assertEquals(1, returnedQuestions[0].getQclass());
		assertEquals("blog.msdn.com.", returnedQuestions[1].getQname());
		assertEquals(2, returnedQuestions[1].getQtype());
		assertEquals(3, returnedQuestions[1].getQclass());
		assertEquals("www.netflix.com.", returnedQuestions[2].getQname());
		assertEquals(1, returnedQuestions[2].getQtype());
		assertEquals(255, returnedQuestions[2].getQclass());
		assertEquals("www.theused.net.", returnedQuestions[3].getQname());
		assertEquals(16, returnedQuestions[3].getQtype());
		assertEquals(7, returnedQuestions[3].getQclass());
		
		assertEquals(0, m.getAnswers().length);
		assertEquals(0, m.getAuthorities().length);
		assertEquals(0, m.getAdditional().length);
	}

}
