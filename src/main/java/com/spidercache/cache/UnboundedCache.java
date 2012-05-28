package com.spidercache.cache;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;


public class UnboundedCache <T>{

	protected ConcurrentHashMap<String, LfuItem<T>> byKey;
	protected FrequencyList frequencies;
	protected CacheIO<T> cacheIO; 

	public UnboundedCache()
	{
		byKey = new ConcurrentHashMap<String, LfuItem<T>>();
		frequencies = new FrequencyList();
		cacheIO = new CacheIO<T>();
	}

	public ConcurrentHashMap<String, LfuItem<T>> getByKey()
	{
		return byKey;
	}

	public T access(String key) throws IOException, ClassNotFoundException
	{
		LfuItem<T> tempItem = byKey.get(key);
		if(tempItem == null)
		{
			return null;
		}

		//FrequencyNode tempNode;
		FrequencyNode parent = tempItem.getParent();

		synchronized(frequencies)
		{
			parent.removeItem(key);
			tempItem.setParent(frequencies.insertFrequency(parent));
		}

		if(parent.hasNoItems())
		{
			synchronized(frequencies)
			{
				if(parent.hasNoItems())
				{
					frequencies.deleteFrequency(parent);
				}
			}
		}

		return cacheIO.readFile(key);
	}

	public void insert(String key, T entry) throws IOException
	{
		if(byKey.containsKey(key))
		{
			//throw new IOException("Item has already been cached");
			return;
		}

		FrequencyNode frequency;

		synchronized(frequencies)
		{
			frequency = frequencies.addFirstFrequency();
		}

		//item.setData(key, entry);
		synchronized(key)
		{
			if(byKey.get(key) == null)
			{
				frequency.addItem(key);
				LfuItem<T> item = new LfuItem<T>(frequency);
				byKey.put(key, item);
			}
		}
		cacheIO.writeFile(key, entry);
	}

	public void remove(String key)
	{
		LfuItem<T> tempItem = byKey.remove(key);

		if(tempItem == null)
		{
			return;
		}

		FrequencyNode tempNode = tempItem.getParent();
		tempNode.removeItem(key);


		if(tempNode.hasNoItems())
		{
			synchronized(frequencies)
			{
				if(tempNode.hasNoItems())
				{
					frequencies.deleteFrequency(tempNode);
				}
			}
		}

		cacheIO.removeFile(key);
	}
}
