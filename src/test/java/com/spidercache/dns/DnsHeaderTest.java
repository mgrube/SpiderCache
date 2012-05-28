package com.spidercache.dns;

import static org.junit.Assert.*;

import org.junit.Test;

import com.spidercache.utils.Convert;

/**
 * Verifies the correct functionality of the {@link DnsHeader} class.
 * The method names correspond to the functions they are testing.
 * 
 * @author Dustin Buckenmeyer
 */
public class DnsHeaderTest {
	
	@Test
	public void parseHeader1() {
		byte[] data = {1, 2, 3, 4, 5, (byte) 156, 7, 8, 9, 10, 11, 12};
		DnsHeader h = new DnsHeader();
		h.parseHeader(data, 0);
		
		assertEquals(258, h.getId());
		assertEquals(0, Convert.toUnsignedByte(h.getQr()));
		assertEquals(0, Convert.toUnsignedByte(h.getOpcode()));
		assertEquals(0, Convert.toUnsignedByte(h.getAa()));
		assertEquals(1, Convert.toUnsignedByte(h.getTc()));
		assertEquals(1, Convert.toUnsignedByte(h.getRd()));
		assertEquals(0, Convert.toUnsignedByte(h.getRa()));
		assertEquals(0, Convert.toUnsignedByte(h.getZ()));
		assertEquals(4, Convert.toUnsignedByte(h.getRcode()));
		assertEquals(1436, Convert.toUnsignedInt(h.getQdcount()));
		assertEquals(1800, Convert.toUnsignedInt(h.getAncount()));
		assertEquals(2314, Convert.toUnsignedInt(h.getNscount()));
		assertEquals(2828, Convert.toUnsignedInt(h.getArcount()));
	}
	
	@Test
	public void parseHeader2() {
		byte[] data = {(byte) 199, 121, (byte) 231, (byte) 176, 0, 1, 0, 1, 0, 0, 0, 0};
		DnsHeader h = new DnsHeader();
		h.parseHeader(data, 0);
		
		assertEquals(-14471, h.getId());
		assertEquals(1, Convert.toUnsignedByte(h.getQr()));
		assertEquals(12, Convert.toUnsignedByte(h.getOpcode()));
		assertEquals(1, Convert.toUnsignedByte(h.getAa()));
		assertEquals(1, Convert.toUnsignedByte(h.getTc()));
		assertEquals(1, Convert.toUnsignedByte(h.getRd()));
		assertEquals(1, Convert.toUnsignedByte(h.getRa()));
		assertEquals(3, Convert.toUnsignedByte(h.getZ()));
		assertEquals(0, Convert.toUnsignedByte(h.getRcode()));
		assertEquals(1, Convert.toUnsignedInt(h.getQdcount()));
		assertEquals(1, Convert.toUnsignedInt(h.getAncount()));
		assertEquals(0, Convert.toUnsignedInt(h.getNscount()));
		assertEquals(0, Convert.toUnsignedInt(h.getArcount()));
	}
	
	@Test
	public void parseHeader3() {
		byte[] data = {(byte) 143, (byte) 133, (byte) 133, (byte) 128, 0, 2, 0, 3, 0, 2, 0, 1};
		DnsHeader h = new DnsHeader();
		h.parseHeader(data, 0);
		
		assertEquals(-28795, h.getId());
		assertEquals(1, Convert.toUnsignedByte(h.getQr()));
		assertEquals(0, Convert.toUnsignedByte(h.getOpcode()));
		assertEquals(1, Convert.toUnsignedByte(h.getAa()));
		assertEquals(0, Convert.toUnsignedByte(h.getTc()));
		assertEquals(1, Convert.toUnsignedByte(h.getRd()));
		assertEquals(1, Convert.toUnsignedByte(h.getRa()));
		assertEquals(0, Convert.toUnsignedByte(h.getZ()));
		assertEquals(0, Convert.toUnsignedByte(h.getRcode()));
		assertEquals(2, Convert.toUnsignedInt(h.getQdcount()));
		assertEquals(3, Convert.toUnsignedInt(h.getAncount()));
		assertEquals(2, Convert.toUnsignedInt(h.getNscount()));
		assertEquals(1, Convert.toUnsignedInt(h.getArcount()));
	}
	
	@Test
	public void constructor() {
		DnsHeader h = new DnsHeader((short) 389, (byte) 1, (byte) 0, (short) 5);
		
		assertEquals(389, h.getId());
		assertEquals(1, Convert.toUnsignedByte(h.getQr()));
		assertEquals(1, Convert.toUnsignedByte(h.getOpcode()));
		assertEquals(1, Convert.toUnsignedByte(h.getAa()));
		assertEquals(0, Convert.toUnsignedByte(h.getTc()));
		assertEquals(0, Convert.toUnsignedByte(h.getRd()));
		assertEquals(0, Convert.toUnsignedByte(h.getRa()));
		assertEquals(0, Convert.toUnsignedByte(h.getZ()));
		assertEquals(0, Convert.toUnsignedByte(h.getRcode()));
		assertEquals(5, Convert.toUnsignedInt(h.getQdcount()));
		assertEquals(5, Convert.toUnsignedInt(h.getAncount()));
		assertEquals(0, Convert.toUnsignedInt(h.getNscount()));
		assertEquals(0, Convert.toUnsignedInt(h.getArcount()));
	}
	
	@Test
	public void assembleHeader1() {
		byte[] data = {1, 2, 3, 4, 5, (byte) 156, 7, 8, 9, 10, 11, 12};
		DnsHeader h = new DnsHeader();
		h.parseHeader(data, 0);
		byte[] assembledData = h.assembleHeader();
		
		for (int i = 0; i < data.length; i++) {
			assertEquals(data[i], assembledData[i]);
		}
	}
	
	@Test
	public void assembleHeader2() {
		byte[] data = {(byte) 199, 121, (byte) 231, (byte) 176, 0, 1, 0, 1, 0, 0, 0, 0};
		DnsHeader h = new DnsHeader();
		h.parseHeader(data, 0);
		byte[] assembledData = h.assembleHeader();
		
		for (int i = 0; i < data.length; i++) {
			assertEquals(data[i], assembledData[i]);
		}
	}
	
	@Test
	public void assembleHeader3() {
		byte[] data = {(byte) 143, (byte) 133, (byte) 133, (byte) 128, 0, 2, 0, 3, 0, 2, 0, 1};
		DnsHeader h = new DnsHeader();
		h.parseHeader(data, 0);
		byte[] assembledData = h.assembleHeader();
		
		for (int i = 0; i < data.length; i++) {
			assertEquals(data[i], assembledData[i]);
		}
	}
	
	@Test
	public void assembleHeader4() {
		DnsHeader h = new DnsHeader();
		byte[] data = h.assembleHeader();
		assertEquals(12, data.length);
	}

}
