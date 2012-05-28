package com.spidercache.queue;

public class Status {
	
	public enum nodeStatus {AVAILABLE,BUSY,UNAVAILABLE};
	protected float efficiencyRatio;
	protected int requestsIn;
	protected int requestsOut;
	protected int time = 60;
	protected nodeStatus status;
	
	public Status(nodeStatus s)
	{
		this.status = s;
		efficiencyRatio = 0;
		requestsIn = 0;
		requestsOut = 0;	
	}
	public Status(nodeStatus s,float er,int ri,int ro)
	{
		status = s;
		efficiencyRatio = er;
		requestsIn = ri;
		requestsOut = ro;		
	}
	public String getStatus()
	{
		return status.toString();
	}
}
