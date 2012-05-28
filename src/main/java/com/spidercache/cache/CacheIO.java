package com.spidercache.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CacheIO <T> {

	private final ConcurrentHashMap<String, ReentrantReadWriteLock> cacheIO = new ConcurrentHashMap<String, ReentrantReadWriteLock>();
	private final ReentrantReadWriteLock removeLock = new ReentrantReadWriteLock(true);

	public T readFile(String key) throws ClassNotFoundException, IOException
	{
		try
		{
			//removeLock.readLock().lock();
			cacheIO.get(key).readLock().lock();
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(key));
			T data = (T)(in.readObject());
			in.close();
			cacheIO.get(key).readLock().unlock();
			return data;
		}
		catch(NullPointerException ex)
		{
			return null;
		}
	}

	public void writeFile(String key, T entry) throws IOException
	{
		//removeLock.readLock().lock();
		if(cacheIO.get(key) == null)
		{
			cacheIO.put(key, new ReentrantReadWriteLock());
		}

		cacheIO.get(key).writeLock().lock();
		File file = new File(key);
		file.deleteOnExit();
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
		out.writeObject(entry);
		out.close();		
		cacheIO.get(key).writeLock().unlock();
	}

	public void removeFile(String key)
	{
		try
		{
			//removeLock.writeLock().lock();
			cacheIO.get(key).writeLock().lock();
			new File(key).delete();
			if(!cacheIO.get(key).hasQueuedThreads())
			{
				cacheIO.remove(key).writeLock().unlock();
			}
			else
			{
				cacheIO.get(key).writeLock().unlock();
			}
		}
		catch(NullPointerException ex)
		{
			return;
		}
	}
}
