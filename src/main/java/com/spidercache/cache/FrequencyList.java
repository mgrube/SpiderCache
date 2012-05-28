package com.spidercache.cache;
public class FrequencyList {

	FrequencyNode head;
	
	public FrequencyList()
	{
		head = new FrequencyNode(0, null, null);
	}
	
	public FrequencyNode addFirstFrequency()
	{
		if(head.getNext() == null)
		{
			FrequencyNode  node = new FrequencyNode(1, null, head);
			head.setNext(node);
			return node;
		}
		else if(head.getNext().getValue() != 1)
		{
			FrequencyNode node = new FrequencyNode(1, head.getNext(), head);
			head.getNext().setPrevious(node);
			head.setNext(node);
			return node;
		}
		
		return head.getNext();
	}
	
	public FrequencyNode insertFrequency(FrequencyNode node)
	{
		if(node.getNext() == null)
		{
			FrequencyNode tail = new FrequencyNode(node.getValue()+1, null, node);
			node.setNext(tail);
		}
		else if(node.getNext().getValue() != node.getNext().getValue() + 1)
		{
			FrequencyNode tail = new FrequencyNode(node.getValue()+1, node.getNext(), node);
			node.getNext().setPrevious(tail);
			node.setNext(tail);			
		}
		
		return node.getNext();
	}
	
	public void deleteFrequency(FrequencyNode node)
	{
		if(node.getNext() != null)
		{
			node.getNext().setPrevious(node.getPrevious());
		}
				
		node.getPrevious().setNext(node.getNext());
	}
	
	public FrequencyNode getLowestFrequency()
	{
		return head.getNext();
	}
}
