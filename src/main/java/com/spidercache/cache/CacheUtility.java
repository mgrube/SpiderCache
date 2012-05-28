package com.spidercache.cache;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CacheUtility {

	public static void initializeDirectory(String filePath)
	{
		File directory = new File(filePath);
		if(!directory.exists())
		{
			directory.mkdir();
		}
		else
		{
			for(String fileName: directory.list())
			{
				if(filePath.endsWith("/"))
				{
					File file = new File(filePath + fileName);
					file.delete();
				}
				else
				{
					File file = new File(filePath + "/" + fileName);
					file.delete();
				}
			}
		}
	}
	
	private static String hashToHexString(byte[] bytes)
	{
		 StringBuffer sb = new StringBuffer(bytes.length * 2);
		    for (int i = 0; i < bytes.length; i++) {
		      int v = bytes[i] & 0xff;
		      if (v < 16) {
		        sb.append('0');
		      }
		      sb.append(Integer.toHexString(v));
		    }
		    return sb.toString();
	}
	
	public static String keyToMD5Hash(String key)
	{
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] digest = md.digest(key.getBytes("UTF-8"));
			return hashToHexString(digest);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			return "";
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			return "";
		}
	}
}
