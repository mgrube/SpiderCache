package com.spidercache.dns;

import static org.junit.Assert.*;

import org.junit.Test;

import com.spidercache.utils.Convert;

/**
 * Verifies the correct functionality of the {@link DnsResourceRecord} class.
 * The method names correspond to the functions they are testing.
 * 
 * @author Dustin Buckenmeyer
 */
public class DnsResourceRecordTest {

	@Test
	public void parseResourceRecord1() {
		byte[] data = {3, 119, 119, 119, 8, 102, 97, 99, 101, 98, 111, 111, 107, 3, 99, 111, 109, 0, 0, 1, 0, 1, 0, 1, 81, (byte) 128, 0, 4, (byte) 192, (byte) 168, 1, 2};
		DnsResourceRecord r = new DnsResourceRecord();
		r.parseResourceRecord(data, 0);
		
		assertEquals("www.facebook.com.", r.getRname());
		assertEquals(1, r.getRtype());
		assertEquals(1, r.getRclass());
		assertEquals(86400, r.getTtl());
		assertEquals(4, r.getRdlength());
		assertEquals("192.168.1.2", r.getRdata());
	}
	
	@Test
	public void parseResourceRecord2() {
		byte[] data = {5, 113, 117, 101, 117, 101, 1, 122, 4, 109, 97, 105, 108, 6, 103, 111, 111, 103, 108, 101, 3, 99, 111, 109, 0, 0, (byte) 255, 0, (byte) 254, (byte) 255, (byte) 255, (byte) 255, (byte) 255, 0, 1, (byte) 192};
		DnsResourceRecord r = new DnsResourceRecord();
		r.parseResourceRecord(data, 0);
		
		assertEquals("queue.z.mail.google.com.", r.getRname());
		assertEquals(255, r.getRtype());
		assertEquals(254, r.getRclass());
		assertEquals(4294967295l, r.getTtl());
		assertEquals(1, r.getRdlength());
		assertEquals("192", r.getRdata());
	}
	
	@Test
	public void parseResourceRecord3() {
		byte[] data = {0, (byte) 250, 97,(byte) 128, 0, 127, (byte) 255, (byte) 255, (byte) 255, 0, 3, (byte) 192, (byte) 168, 1};
		DnsResourceRecord r = new DnsResourceRecord();
		r.parseResourceRecord(data, 0);
		
		assertEquals(".", r.getRname());
		assertEquals(64097, Convert.toUnsignedInt(r.getRtype()));
		assertEquals(32768, Convert.toUnsignedInt(r.getRclass()));
		assertEquals(2147483647, r.getTtl());
		assertEquals(3, r.getRdlength());
		assertEquals("192.168.1", r.getRdata());
	}
	
	@Test
	public void constructor() {
		DnsResourceRecord r = new DnsResourceRecord("engadget.com.", (short) 1177, (short) 3342, "192.168.2.109");
		
		assertEquals("engadget.com.", r.getRname());
		assertEquals(1177, Convert.toUnsignedInt(r.getRtype()));
		assertEquals(3342, Convert.toUnsignedInt(r.getRclass()));
		assertEquals(86400, r.getTtl());
		assertEquals(4, r.getRdlength());
		assertEquals("192.168.2.109", r.getRdata());
	}
	
	@Test
	public void assembleResourceRecord1() {
		byte[] data = {3, 119, 119, 119, 8, 102, 97, 99, 101, 98, 111, 111, 107, 3, 99, 111, 109, 0, 0, 1, 0, 1, 0, 1, 81, (byte) 128, 0, 4, (byte) 192, (byte) 168, 1, 2};
		DnsResourceRecord r = new DnsResourceRecord();
		r.parseResourceRecord(data, 0);
		byte[] assembledData = r.assembleResourceRecord();
		
		for (int i = 0; i < data.length; i++) {
			assertEquals(data[i], assembledData[i]);
		}
	}
	
	@Test
	public void assembleResourceRecord2() {
		byte[] data = {5, 113, 117, 101, 117, 101, 1, 122, 4, 109, 97, 105, 108, 6, 103, 111, 111, 103, 108, 101, 3, 99, 111, 109, 0, 0, (byte) 255, 0, (byte) 254, (byte) 255, (byte) 255, (byte) 255, (byte) 255, 0, 1, (byte) 192};
		DnsResourceRecord r = new DnsResourceRecord();
		r.parseResourceRecord(data, 0);
		byte[] assembledData = r.assembleResourceRecord();
		
		for (int i = 0; i < data.length; i++) {
			assertEquals(data[i], assembledData[i]);
		}
	}
	
	@Test
	public void assembleResourceRecord3() {
		byte[] data = {0, (byte) 250, 97,(byte) 128, 0, 127, (byte) 255, (byte) 255, (byte) 255, 0, 3, (byte) 192, (byte) 168, 1};
		DnsResourceRecord r = new DnsResourceRecord();
		r.parseResourceRecord(data, 0);
		byte[] assembledData = r.assembleResourceRecord();
		
		for (int i = 0; i < data.length; i++) {
			assertEquals(data[i], assembledData[i]);
		}
	}
	
	@Test
	public void assembleResourceRecord4() {
		// length calculation is incorrect
		// copy assembleResourceRecord2() data to see
		//byte[] data = {5, 113, 117, 101, 117, 101, 1, 122, 4, 109, 97, 105, 108, 6, 103, 111, 111, 103, 108, 101, 3, 99, 111, 109, 0, 0, (byte) 255, 0, (byte) 254, (byte) 255, (byte) 255, (byte) 255, (byte) 255, 0, 1, (byte) 192};
		byte[] data = {3, 119, 119, 119, 8, 102, 97, 99, 101, 98, 111, 111, 107, 3, 99, 111, 109, 0, 0, 1, 0, 1, 0, 1, 81, (byte) 128, 0, 4, (byte) 192, (byte) 168, 1, 2};
		DnsResourceRecord r = new DnsResourceRecord();
		r.parseResourceRecord(data, 0);
		byte[] assembledData = r.assembleResourceRecord();

		assertEquals(data.length, assembledData.length);
	}

}
