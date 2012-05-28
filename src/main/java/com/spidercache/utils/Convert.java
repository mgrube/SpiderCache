package com.spidercache.utils;

/**
 * Converts signed primitives to unsigned versions
 * by calculating the unsigned result and placing that in a larger container.
 * 
 * @author Dustin Buckenmeyer
 */
public class Convert {
	
	/**
	 * Converts a signed byte to an unsigned byte contained in a short.
	 * @param b byte to convert.
	 * @return Unsigned result.
	 */
	public static short toUnsignedByte(byte b) {
		short s = 0;
		
		if ((b & 0x80) == 0) {
			return b;
		}
		
		s += (b & 0x01);
		s += (b & 0x02);
		s += (b & 0x04);
		s += (b & 0x08);
		s += (b & 0x10);
		s += (b & 0x20);
		s += (b & 0x40);
		s += (b & 0x80);
		
		return s;
	}
	
	/**
	 * Converts a signed short to an unsigned short contained in an int.
	 * @param s short to convert.
	 * @return Unsigned result.
	 */
	public static int toUnsignedShort(short s) {
		int i = 0;
		
		if ((s & 0x8000) == 0) {
			return s;
		}
		
		i += (s & 0x0001);
		i += (s & 0x0002);
		i += (s & 0x0004);
		i += (s & 0x0008);
		i += (s & 0x0010);
		i += (s & 0x0020);
		i += (s & 0x0040);
		i += (s & 0x0080);
		i += (s & 0x0100);
		i += (s & 0x0200);
		i += (s & 0x0400);
		i += (s & 0x0800);
		i += (s & 0x1000);
		i += (s & 0x2000);
		i += (s & 0x4000);
		i += (s & 0x8000);
		
		return i;
	}
	
	/**
	 * Converts a signed int to an unsigned int contained in a long.
	 * @param i int to convert
	 * @return Unsigned result.
	 */
	public static long toUnsignedInt(int i) {
		long l = 0;
		
		if ((i & 0x80000000) == 0) {
			return i;
		}
		
		l += (i & 0x00000001L);
		l += (i & 0x00000002L);
		l += (i & 0x00000004L);
		l += (i & 0x00000008L);
		l += (i & 0x00000010L);
		l += (i & 0x00000020L);
		l += (i & 0x00000040L);
		l += (i & 0x00000080L);
		l += (i & 0x00000100L);
		l += (i & 0x00000200L);
		l += (i & 0x00000400L);
		l += (i & 0x00000800L);
		l += (i & 0x00001000L);
		l += (i & 0x00002000L);
		l += (i & 0x00004000L);
		l += (i & 0x00008000L);
		l += (i & 0x00010000L);
		l += (i & 0x00020000L);
		l += (i & 0x00040000L);
		l += (i & 0x00080000L);
		l += (i & 0x00100000L);
		l += (i & 0x00200000L);
		l += (i & 0x00400000L);
		l += (i & 0x00800000L);
		l += (i & 0x01000000L);
		l += (i & 0x02000000L);
		l += (i & 0x04000000L);
		l += (i & 0x08000000L);
		l += (i & 0x10000000L);
		l += (i & 0x20000000L);
		l += (i & 0x40000000L);
		l += (i & 0x80000000L);
		
		return l;
	}
	
}
