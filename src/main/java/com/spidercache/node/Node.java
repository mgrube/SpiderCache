package com.spidercache.node;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.cache.CacheConfig;
import org.apache.http.impl.client.cache.CachingHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;

import com.spidercache.cache.Cache;
import com.spidercache.dht.DHT;
import com.spidercache.dht.DiscoveryService;
import com.spidercache.dht.NodeAnnouncer;
import com.spidercache.utils.IntermediateHttpClient;

public class Node {
	
	private HashMap<InetAddress, Integer> peerlist;
	private DiscoveryService DS;
	private NodeAnnouncer NA;
	private DHT D;
	private int DHTPort;
	private int MessagePort;
	private long cachesize;
	private Cache cache;
	private String cachepath;
	private CachingHttpClient CacheClient;
	private DefaultHttpClient NonCacheClient;
	private ThreadSafeClientConnManager NonCacheManager;
	private ThreadSafeClientConnManager CacheCon;
	private CacheConfig config;

	/**
	 * 
	 * This sets the DHT Port as 5500, the message service port at 5501.
	 * 
	 */
	public Node(String cachepath){
		this.cachepath = cachepath;
		DHTPort = 5510;
		MessagePort = 5511;
	}
	
	/**
	 * Method for node startup.
	 * Logic for bootstrapping:
	 * 
	 */
	private void nodeStart(){
		
		D = new DHT("SpiderCache DHT", DHTPort, MessagePort, null, 0);
		DS = new DiscoveryService(D, DHTPort);
		NA = new NodeAnnouncer(DS);
		cachepath = "/home/snark/cache";
		
	}
	
	private void bootstrap(){
		
	}
	
	/**
	 * Is the node directly connected to the internet? (Can it reach Google?)
	 * *NEEDS TO BE TOUCHED UP A BIT. BUGGY.*
	 * @return A boolean value indicating a whether the node has access to the net.
	 * @throws SocketException 
	 * @throws UnknownHostException 
	 */
	public boolean directlyConnected() throws SocketException, UnknownHostException{
		Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces();
		while(ifaces.hasMoreElements()){
			InetAddress google = InetAddress.getByName("www.google.com");
			NetworkInterface iface = ifaces.nextElement();
			try {
				if(google.isReachable(iface, 70, 10000)){
					return true;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	/**
	 * Set the appropriate behavior of the node: 
	 * Service direct requests to the net if we are a gateway node.
	 */
	public void setNodeBehavior(){
	
	}
	
	/**
	 * Starts the DNS Service. 
	 * @return True on success, false on failure.
	 */
	private boolean startDNS(){
		return false;
	}
	
	/**
	 * Starts the web service.
	 * @return True on success, false on failure.
	 */
	private boolean webServiceStart(){
		return false;
	}
	
	
	// When making PostThread, pass NonCacheHttpClient.
	// Remember to make this method private again after testing.
	/**
	 * Initializes the caching service.
	 * @return True on success, false on failure.
	 */
	public boolean cacheServiceStart(){
		cache = new Cache((long)2<<35, cachepath);
		IntermediateHttpClient HTTPClient = new IntermediateHttpClient();
		CacheCon  = new ThreadSafeClientConnManager();
		config = new CacheConfig();
		config.setSharedCache(true);
		config.setMaxObjectSizeBytes(2<<31);
		CacheClient = new CachingHttpClient(HTTPClient, cache, config);
		return false;
	}
	
	public CachingHttpClient getCacheClient(){
		return CacheClient;
	}
	
	public Cache getCache(){
		return cache;
	}
	
	public CacheConfig getCacheConfig(){
		return config;
	}
	
	/**
	 * 
	 * Logic for this process:
	 * 1. Check Cache
	 * 		Cache hit? Then return.
	 * 2. Check DHT.
	 * 		DHT Hit? Request from peer.
	 * 3. Request content from the web.
	 * 		Not on web? Notify peer.
	 * 
	 * @param URL The URL the thin client is requesting.
	 * @return
	 */
	public boolean processRequest(String URL){
		
		return false;
	}
	
	
	/**
	 * Initializes the DHT service.
	 * @return True on success, false on failure.
	 */
	private boolean DHTBootstrap(){
		
		return false;
	}
	
	/**
	 * Starts the logger.
	 * @return
	 */
	private boolean startLogger(){
		return false;
	}
	
	/**
	 * Gracefully take the node offline.
	 * @return
	 */
	private boolean nodeShutdown(){
		NA.setAnnounce(false);
		DS.setListen(false);
		return false;
	}
	
	/**
	 * Stop the DNS Service.
	 * @return
	 */
	private boolean stopDNS(){
		return false;
	}
	
	/**
	 * Stop the web service.
	 * @return True on success, false on failure.
	 */
	private boolean webServiceStop(){
		return false;
	}
	
	/**
	 * Stop the cache service.
	 * @return True on success, false on failure.
	 */
	private boolean cacheServiceStop(){
		return false;
	}
	
	/**
	 * Stop the queue service.
	 * @return True on success, false on failure.
	 */
	private boolean queueServiceStop(){
		return false;
	}
	
	public static void main(String[] args){
		Node n = new Node("/home/snark/cache");
		n.cacheServiceStart();
	}
	
}
