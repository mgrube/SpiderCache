package com.spidercache.dht;

import java.io.IOException;
import java.util.concurrent.*;


public class NodeAnnouncer implements Runnable{
	
	private DiscoveryService AnnouncerDS;
	private boolean announce;
	
	public NodeAnnouncer(DiscoveryService DS){
		AnnouncerDS = DS;
		announce = true;
		(new Thread(this)).start();
	}
	
	public void setAnnounce(boolean announce){
		this.announce = announce;
	}
	
	public void run(){
		while(announce){
		try {
			AnnouncerDS.announce();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		}
	}
	
	

}
