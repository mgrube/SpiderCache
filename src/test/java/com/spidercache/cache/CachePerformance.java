package com.spidercache.cache;

import java.io.IOException;

public class CachePerformance{

	private final BoundedCache<byte[]> cache;
	
	public CachePerformance(long capacity, long minimumCapacity)
	{
		cache = new BoundedCache<byte[]>(capacity, minimumCapacity);
	}
	
	public byte[] get(String key) throws IOException, ClassNotFoundException
	{
		return cache.access(CacheUtility.keyToMD5Hash(key));
	}
	
	public void put(String key, byte[] entry) throws IOException
	{
		cache.insert(CacheUtility.keyToMD5Hash(key), entry, entry.length);
	}
	
	public void remove(String key)
	{
		cache.remove(CacheUtility.keyToMD5Hash(key));
	}
	
	public InsertThread getInsertThread(String key, byte[] entry)
	{
		return new InsertThread(key, entry);
	}
	
	public AccessThread getAccessThread(String key, byte[] entry)
	{
		return new AccessThread(key);
	}
	
	public RemoveThread getRemoveThread(String key, byte[] entry)
	{
		return new RemoveThread(key);
	}
	
	class InsertThread extends Thread
	{
		private byte[] entry;
		private String key;
		
		public InsertThread(String key, byte[] entry)
		{
			this.key = key;
			this.entry = entry;
		}
		
		public void run()
		{
			try {
				put(key, entry);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	class AccessThread extends Thread
	{
		private String key;
		
		public AccessThread(String key)
		{
			this.key = key;
		}
		
		public void run()
		{
			try {
				get(key);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	class RemoveThread extends Thread
	{
		private String key;
		
		public RemoveThread(String key)
		{
			this.key = key;
		}
		
		public void run()
		{
			remove(key);
		}
	}
}
