package com.spidercache.dns;

import static org.junit.Assert.*;

import org.junit.Test;
import com.spidercache.utils.Convert;

/**
 * Verifies the correct functionality of the {@link DnsQuestion} class.
 * The method names correspond to the functions they are testing.
 * 
 * @author Dustin Buckenmeyer
 */
public class DnsQuestionTest {

	@Test
	public void parseQuestion1() {
		byte[] data = {3, 119, 119, 119, 8, 102, 97, 99, 101, 98, 111, 111, 107, 3, 99, 111, 109, 0, 0, 1, 0, 1};
		DnsQuestion q = new DnsQuestion();
		q.parseQuestion(data, 0);
		
		assertEquals("www.facebook.com.", q.getQname());
		assertEquals(1, q.getQtype());
		assertEquals(1, q.getQclass());
	}
	
	@Test
	public void parseQuestion2() {
		byte[] data = {5, 113, 117, 101, 117, 101, 1, 122, 4, 109, 97, 105, 108, 6, 103, 111, 111, 103, 108, 101, 3, 99, 111, 109, 0, 0, (byte) 255, 0, (byte) 254};
		DnsQuestion q = new DnsQuestion();
		q.parseQuestion(data, 0);
		
		assertEquals("queue.z.mail.google.com.", q.getQname());
		assertEquals(255, q.getQtype());
		assertEquals(254, q.getQclass());
	}
	
	@Test
	public void parseQuestion3() {
		byte[] data = {0, (byte) 250, 97,(byte) 128, 0};
		DnsQuestion q = new DnsQuestion();
		q.parseQuestion(data, 0);
		
		assertEquals(".", q.getQname());
		assertEquals(64097, Convert.toUnsignedInt(q.getQtype()));
		assertEquals(32768, Convert.toUnsignedInt(q.getQclass()));
	}
	
	@Test
	public void constructor() {
		DnsQuestion q = new DnsQuestion("engadget.com.", (short) 1177, (short) 3342);
		
		assertEquals("engadget.com.", q.getQname());
		assertEquals(1177, Convert.toUnsignedInt(q.getQtype()));
		assertEquals(3342, Convert.toUnsignedInt(q.getQclass()));
	}
	
	@Test
	public void assembleQuestion1() {
		byte[] data = {3, 119, 119, 119, 8, 102, 97, 99, 101, 98, 111, 111, 107, 3, 99, 111, 109, 0, 0, 1, 0, 1};
		DnsQuestion q = new DnsQuestion();
		q.parseQuestion(data, 0);
		byte[] assembledData = q.assembleQuestion();
		
		for (int i = 0; i < data.length; i++) {
			assertEquals(data[i], assembledData[i]);
		}
	}
	
	@Test
	public void assembleQuestion2() {
		byte[] data = {5, 113, 117, 101, 117, 101, 1, 122, 4, 109, 97, 105, 108, 6, 103, 111, 111, 103, 108, 101, 3, 99, 111, 109, 0, 0, (byte) 255, 0, (byte) 254};
		DnsQuestion q = new DnsQuestion();
		q.parseQuestion(data, 0);
		byte[] assembledData = q.assembleQuestion();
		
		for (int i = 0; i < data.length; i++) {
			assertEquals(data[i], assembledData[i]);
		}
	}
	
	@Test
	public void assembleQuestion3() {
		byte[] data = {0, (byte) 250, 97,(byte) 128, 0};
		DnsQuestion q = new DnsQuestion();
		q.parseQuestion(data, 0);
		byte[] assembledData = q.assembleQuestion();
		
		for (int i = 0; i < data.length; i++) {
			assertEquals(data[i], assembledData[i]);
		}
	}
	
	@Test
	public void assembleQuestion4() {
		byte[] data = {5, 113, 117, 101, 117, 101, 1, 122, 4, 109, 97, 105, 108, 6, 103, 111, 111, 103, 108, 101, 3, 99, 111, 109, 0, 0, (byte) 255, 0, (byte) 254};
		DnsQuestion q = new DnsQuestion();
		q.parseQuestion(data, 0);
		byte[] assembledData = q.assembleQuestion();

		assertEquals(data.length, assembledData.length);
	}

}
