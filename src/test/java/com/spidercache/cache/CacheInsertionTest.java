package com.spidercache.cache;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;


public class CacheInsertionTest {

	private UnboundedCache<Integer> cache;

	@Before
	public void initCache()
	{
		cache = new UnboundedCache<Integer>();
	}

	@Test
	public void insertItems() throws IOException, ClassNotFoundException
	{
		Integer first = new Integer(1);
		Integer second = new Integer(2);
		Integer third = new Integer(3);
		
		cache.insert("first", first);
		cache.insert("second", second);
		cache.insert("third", third);
		
		assertEquals(cache.access("first"), first);
		assertEquals(cache.access("second"), second);
		assertEquals(cache.access("third"), third);
	}

}
