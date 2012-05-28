package com.spidercache.utils;

import static org.junit.Assert.*;

import org.junit.Test;

import com.spidercache.dns.DnsHeader;
import com.spidercache.utils.Convert;

/**
 * Verifies the correct functionality of the {@link Convert} class.
 * The method names correspond to the functions they are testing.
 * 
 * @author Dustin Buckenmeyer
 */
public class ConvertTest {
	
	@Test
	public void testByte0() {
		byte number = Byte.MIN_VALUE;
		assertEquals(128, Convert.toUnsignedByte(number));
	}
	
	@Test
	public void testByte1() {
		byte number = 21;
		assertEquals(21, Convert.toUnsignedByte(number));
	}
	
	@Test
	public void testByte2() {
		byte number = -111;
		assertEquals(145, Convert.toUnsignedByte(number));
	}

	@Test
	public void testShort0() {
		short number = Short.MIN_VALUE;
		assertEquals(32768, Convert.toUnsignedShort(number));
	}
	
	@Test
	public void testShort1() {
		short number = (short) 44401;
		assertEquals(44401, Convert.toUnsignedShort(number));
	}
	
	@Test
	public void testShort2() {
		short number = (short) -14095;
		assertEquals(51441, Convert.toUnsignedShort(number));
	}
	
	@Test
	public void testInt0() {
		int number = Integer.MIN_VALUE;
		assertEquals(2147483648L, Convert.toUnsignedInt(number));
	}
	
	@Test
	public void testInt1() {
		int number = 13137764;
		assertEquals(13137764, Convert.toUnsignedInt(number));
	}
	
	@Test
	public void testInt2() {
		int number = -93614470;
		assertEquals(4201352826L, Convert.toUnsignedInt(number));
	}
	
	@Test
	public void testInt3() {
		int number = -13137764;
		assertEquals(4281829532L, Convert.toUnsignedInt(number));
	}

}
