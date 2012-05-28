package com.spidercache.cache;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;


public class CacheEvictionTest {

	private BoundedCache<Integer> cache;
	private Integer one;
	private Integer two;
	private Integer three;
	private Integer four;
	
	@Before
	public void initCache() throws IOException, ClassNotFoundException
	{
		cache = new BoundedCache<Integer>(5,1);
		one = new Integer(1);
		two = new Integer(2);
		three = new Integer(3);
		four = new Integer(4);
		
		cache.insert("first", one, 1);
		cache.insert("second", two, 1);
		cache.insert("third", three, 1);
		cache.insert("fourth", four, 1);
		
		cache.access("first");
		cache.access("first");
		cache.access("second");
	}
	
	@Test
	public void evictionTest() throws IOException, ClassNotFoundException
	{
		Integer five = new Integer(5);
		Integer six = new Integer(6);
		
		cache.insert("fifth", five, 1);

		assertEquals(null, cache.access("third"));
		
		cache.insert("sixth", six, 1);
		assertEquals(null, cache.access("fourth"));
		
		assertEquals(five, cache.access("fifth"));
		assertEquals(six, cache.access("sixth"));
	}
}
