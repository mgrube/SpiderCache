package com.spidercache.cache;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;


public class LfuItem <T>{

	private FrequencyNode parent;
	private long size;
	private Date date;

	public LfuItem(FrequencyNode parent)
	{
		this.parent = parent;
		this.size = 0;
		date = null;
	}

	public T getData(String key)
	{
		ObjectInputStream in = null;
		T data;
		try
		{
			in = new ObjectInputStream(new FileInputStream(key));
			data = (T)(in.readObject());
			return data;
		}
		catch(IOException ex)
		{
			return null;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}

	/*public void setData(HttpCacheEntry data)
	{
		this.data = data;
	}*/

	public void setData(String key, T data)
	{
		ObjectOutputStream out = null;
		try
		{
			File file = new File(key);
			file.deleteOnExit();
			out = new ObjectOutputStream(new FileOutputStream(file));
			out.writeObject(data);
			out.close();			
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}

	public boolean deleteData(String key)
	{
		File data = new File(key);
		return data.delete();
	}

	public FrequencyNode getParent()
	{
		return parent;
	}

	public void setParent(FrequencyNode parent)
	{
		this.parent = parent;
	}

	public long getSize()
	{
		return size;
	}

	public void setSize(long size)
	{
		this.size = size;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		try
		{
			if(this.date.before(date))
			{
				synchronized(this.date)
				{
					this.date = date;
				}
			}
		}
		catch(NullPointerException ex)
		{
			this.date = date;
		}
	}
}
