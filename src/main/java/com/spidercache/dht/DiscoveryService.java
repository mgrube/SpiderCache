package com.spidercache.dht;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * TODO:
 * Make node attempt to reconnect if connection to DHT is lost.
 * 
 * @author Michael Grube
 */

public class DiscoveryService implements Runnable{

	private MulticastSocket multisock;
	private InetAddress multigroup;
	private int DHTPort;
	private boolean listen;
	private HashMap<InetAddress, Integer> peerlist;
	private DHT D;
	private NetworkInterface iface;
	private ArrayList<InetAddress> localaddresses;
	
	/**
	 * Creates a new DiscoveryService to announce a node's availabilty on
	 * the port you specify. Joins the multicast group 224.111.111.111.
	 * @param DHTPort
	 * @throws IOException
	 */
	public DiscoveryService(DHT Dist, int DHTPort){
		try{
		this.D = Dist;
		listen = true;;
		peerlist = new HashMap<InetAddress, Integer>();
		localaddresses = new ArrayList<InetAddress>();
		this.DHTPort = DHTPort;
		multigroup = InetAddress.getByName("224.111.111.111");
		multisock = new MulticastSocket(7777);
		multisock.joinGroup(multigroup);
		
		// Sloppy code (TWSS?)
		Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces();
		while(ifaces.hasMoreElements()){
			NetworkInterface iface = ifaces.nextElement();
			Enumeration<InetAddress> addresses = iface.getInetAddresses();
			while(addresses.hasMoreElements()){
				localaddresses.add(addresses.nextElement());
			}
		}
		
		System.out.println("Local Addresses:");
		for(InetAddress i : localaddresses){
			System.out.println(i.getHostAddress());
		}
		
		(new Thread(this)).start();
		}
		catch(UnknownHostException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public boolean isLocal(InetAddress i){
		for(InetAddress inet : localaddresses){
			if(i.getHostAddress().equals(inet.getHostAddress())){
				return true;
			}
		}
		return false;
	}
	
	public HashMap<InetAddress, Integer> getPeerList(){
		return peerlist;
	}
	
	/**
	 * Very crude way to shut down the listening service.
	 */
	public void shutdownService(){
		listen = false;
	}
	
	/**
	 * Takes the data from a received packet and uses it to add a peer to our peerlist.
	 * Very poor checking to see if the packet is actually sent from a SC node.
	 * 
	 * Need to...
	 * Send announcement on network until connected. 
	 * @param recvd
	 * @return
	 */ 
	private int parseAnnouncement(DatagramPacket recvd){
		String announcement = new String(recvd.getData());
		String[] data = announcement.split(":");
		InetAddress announcer;
		if(data[0].equals("SpiderCache")){
			try {
				announcer = InetAddress.getByName(new String(recvd.getAddress().getHostAddress()));
				if(!isLocal(announcer)){
					listen = !(D.connectNode(announcer.getHostAddress(), Integer.parseInt(data[1].trim())));
					System.out.println("Can we put information into the DHT? " + D.put("Handy", new Double("64")));
				}
				//peerlist.put(InetAddress.getByName(new String(recvd.getAddress().getHostAddress())), Integer.parseInt(data[1].trim()));
			} catch (NumberFormatException e) {
				// Add better way to handle this later...
				e.printStackTrace();
				return -1;
			} catch (UnknownHostException e) {
				// Same...
				e.printStackTrace();
				return -1;
			}
			return 1;
		}
		else{
		return -1;
		}
	}
	
	public void announce() throws IOException{
		String announcement = "SpiderCache:" + DHTPort + "";
		DatagramPacket p = new DatagramPacket(announcement.getBytes(), announcement.length(), multigroup, 7777);
		multisock.send(p);
	}
	
	public void listen() throws IOException{
		 byte[] buf = new byte[1000];
		 DatagramPacket recv = new DatagramPacket(buf, buf.length);
		 multisock.receive(recv);
		 parseAnnouncement(recv);
	}
	
	public int getDHTPort(){
		return DHTPort;
	}
	
	public void setListen(boolean listen){
		this.listen = listen;
	}
	
	public static void main(String args[]) throws IOException, InterruptedException{
		TestObject test = new TestObject("Testing!");
		DHT node1 = new DHT("Node 5001", 5001, 5101, "192.168.2.15", 5001);
//		DHT node2 = new DHT("Node 5001", 5002, 5102, "192.168.2.7", 5001);

	}

	public void run() {
		while(listen){;
		 try{
			 listen();
		 }
		 catch(IOException e){
			 e.printStackTrace();
		 }
		}
		 
	}
	
	
}
