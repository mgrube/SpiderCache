package com.spidercache.cache;
import java.util.LinkedHashSet;


public class FrequencyNode {

	private final int value;
	private LinkedHashSet<String> items;
	private FrequencyNode next;
	private FrequencyNode previous;
	
	public FrequencyNode(int value, FrequencyNode next, FrequencyNode previous)
	{
		this.value = value;
		this.next = next;
		this.previous = previous;
		this.items = new LinkedHashSet<String>();
	}
	
	public int getValue()
	{
		return value;
	}
	
	public void removeItem(String key)
	{
		synchronized(items)
		{
			items.remove(key);
		}
	}
	
	public void addItem(String key)
	{
		items.add(key);
	}
	
	public boolean hasNoItems()
	{
		return items.isEmpty();
	}
	
	public LinkedHashSet<String> getItems()
	{
		return items;
	}
	
	public void setItems(LinkedHashSet<String> items)
	{
		this.items = items;
	}
	
	public FrequencyNode getNext()
	{
		return next;
	}
	
	public void setNext(FrequencyNode next)
	{
		this.next = next;
	}
	
	public FrequencyNode getPrevious()
	{
		return previous;
	}
	
	public void setPrevious(FrequencyNode previous)
	{
		this.previous = previous;
	}	
}
