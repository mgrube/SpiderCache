package com.spidercache.cache;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.client.cache.HttpCacheEntry;
import org.apache.http.client.cache.HttpCacheStorage;
import org.apache.http.client.cache.HttpCacheUpdateCallback;
import org.apache.http.client.cache.HttpCacheUpdateException;


public class Cache implements HttpCacheStorage{

	private BoundedCache<HttpCacheEntry> cache;

	public Cache(long capacity, String cachedContentPath)
	{
		CacheUtility.initializeDirectory(cachedContentPath);
		/*File directory = new File("cachedContent");
		if(!directory.exists())
		{
			directory.mkdir();
		}
		else
		{
			for(String fileName: directory.list())
			{
				File file = new File("cachedContent/" + fileName);
				file.delete();
			}
		}*/
		cache = new BoundedCache<HttpCacheEntry>(capacity, (long)2<<31);
	}

	public synchronized HttpCacheEntry getEntry(String key) throws IOException {
		// TODO Auto-generated method stub
		try {
			return cache.access(CacheUtility.keyToMD5Hash(key));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			return null;
			//e.printStackTrace();
		}
	}

	public synchronized void putEntry(String key, HttpCacheEntry entry) throws IOException {
		// TODO Auto-generated method stub
		String size = entry.getFirstHeader("Content-Length").getValue();
		if(size == null)
		{
			cache.insert(CacheUtility.keyToMD5Hash(key), entry, 0);
		}
		cache.insert(CacheUtility.keyToMD5Hash(key), entry, Long.parseLong(size));
	}

	public synchronized void removeEntry(String key) throws IOException {
		// TODO Auto-generated method stub
		cache.remove(CacheUtility.keyToMD5Hash(key));
	}

	public synchronized void updateEntry(String key, HttpCacheUpdateCallback callback)
	throws IOException, HttpCacheUpdateException {
		// TODO Auto-generated method stub
		key = CacheUtility.keyToMD5Hash(key);
		HttpCacheEntry existingEntry;
		try {
			existingEntry = cache.access(key);
			cache.insert(key, callback.update(existingEntry));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static boolean isHttpsRequest(String url)
	{
		return url.matches("^[hH][tT][tT][pP][sS].*");
	}

	public static boolean isDynamic(String url)
	{
		URL newUrl;
		try {
			newUrl = new URL(url);
			
			if(newUrl.getQuery() != null)
				return true;

			if(newUrl.getRef() != null)
				return true;

			return false;
		} catch (MalformedURLException e) {
			return false;
		}
	}
}
