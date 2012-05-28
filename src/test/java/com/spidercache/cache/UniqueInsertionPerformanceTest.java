package com.spidercache.cache;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

public class UniqueInsertionPerformanceTest {

	public static final CachePerformance cache = new CachePerformance((long)2<<34, (long)2<<31);
	
	public static void main(String args[]) throws IOException
	{
		//CachePerformanceTest test = new CachePerformanceTest();
		byte[] entry = getRawData("/home/preying_mantis/1k_file");
		String[] keys = getKeys("/home/preying_mantis/webpages");
		int numOfKeys = keys.length;
		//Random r = new Random(System.currentTimeMillis());
		
		CachePerformance.InsertThread[] threads = new CachePerformance.InsertThread[numOfKeys];
		
		for(int i = 0; i<threads.length; i++)
		{
			threads[i] = cache.getInsertThread(keys[i], entry);
			threads[i].start();
		}
		
		for(int i = 0; i<threads.length; i++)
		{
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static byte[] getRawData(String filePath) throws IOException
	{
		File file = new File(filePath);
		byte[] rawData = new byte[(int)file.length()];
		FileInputStream in = new FileInputStream(file);
		for(int i=0; i<rawData.length; i++)
		{
			rawData[i] = (byte)in.read();
		}
		return rawData;
	}
	
	public static String[] getKeys(String filePath) throws IOException
	{
		File file = new File(filePath);
		Vector<String> keys = new Vector<String>();
		BufferedReader reader = new BufferedReader(new InputStreamReader
				(new DataInputStream(new FileInputStream(file))));
		String key;
		while((key = reader.readLine()) != null)
		{
			keys.add(key);
		}
		return (String[])keys.toArray(new String[0]);
	}
}
