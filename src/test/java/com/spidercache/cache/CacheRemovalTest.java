package com.spidercache.cache;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;


public class CacheRemovalTest {
	private UnboundedCache<Integer> cache;
	private Integer first;
	private Integer second;
	private Integer third;

	@Before
	public void initCache() throws IOException
	{
		cache = new UnboundedCache<Integer>();
		first = new Integer(1);
		second = new Integer(2);
		third = new Integer(3);
		
		cache.insert("first", first);
		cache.insert("second", second);
		cache.insert("third", third);
	}

	@Test
	public void insertItems() throws IOException, ClassNotFoundException
	{
		cache.remove("first");
		assertEquals(cache.access("first"), null);
		
		assertEquals(cache.access("second"), second);
		assertEquals(cache.access("third"), third);
	}

}
