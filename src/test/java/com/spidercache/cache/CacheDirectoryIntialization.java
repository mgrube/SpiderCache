package com.spidercache.cache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;


public class CacheDirectoryIntialization {

	private File cacheDirectory;
	private String[] cachedFiles = {"a", "b", "c"};

	@Test
	public void testContentDirectoryInit1()
	{
		cacheDirectory = new File("cachedContent1/");
		if(cacheDirectory.exists())
		{
			cacheDirectory.delete();
			CacheUtility.initializeDirectory("cachedContent1/");
			//new Cache(1, "cachedContent/");
			assertTrue(cacheDirectory.exists());
			String[] files = cacheDirectory.list();

			assertEquals(0, files.length);
		}

		for(String fileName: cachedFiles)
		{
			try {
				File file = new File("cachedContent1/" + fileName);
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//new Cache(1, "cachedContent/");
		CacheUtility.initializeDirectory("cachedContent1/");
		for(String fileName: cachedFiles)
		{
			File file = new File("cachedConten1/" + fileName);
			assertFalse(file.exists());
		}
	}
	
	@Test
	public void testContentDirectoryInit2()
	{
		cacheDirectory = new File("cachedContent2");
		if(cacheDirectory.exists())
		{
			cacheDirectory.delete();
			CacheUtility.initializeDirectory("cachedContent2");
			//new Cache(1, "cachedContent/");
			assertTrue(cacheDirectory.exists());
			String[] files = cacheDirectory.list();

			assertEquals(0, files.length);
		}

		for(String fileName: cachedFiles)
		{
			try {
				File file = new File("cachedContent2/" + fileName);
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//new Cache(1, "cachedContent/");
		CacheUtility.initializeDirectory("cachedContent2");
		for(String fileName: cachedFiles)
		{
			File file = new File("cachedContent2/" + fileName);
			assertFalse(file.exists());
		}
	}
	
	
}
