package com.spidercache.dht;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.planx.routing.kademlia.Node;
import org.apache.log4j.*;
import org.planx.routing.kademlia.*;
import org.planx.routing.messaging.*;
import org.planx.routing.*;

/**
 * Class for creating a DHT.
 * 
 * @author Michael Neu
 * 
 */
public class DHT {

	/**
	 * Kademlia DHT object inside this DHT object
	 */
	private Kademlia dht;
	/**
	 * Kademlia node representation used in this DHT object; used with Message
	 * Server
	 */
	private Node node;
	/**
	 * Kademlia MessageServer object used in this DHT object
	 */
	private MessageServer messageServer;
	
	/**
	 * Log4j Log.
	 */
	private Logger log;

	/**
	 * Constructor for creating a DHT object.
	 * 
	 * @param name
	 *            Name of node. Used as the filename base if given. If null then persistence is not used.
	 * @param dhtUdpPort
	 *            UDP Port for the DHT to route messages on
	 * @param messageServerUdpPort
	 *            UDP Port for the MessageServer to listen on
	 * @param bootStrapNodeHostName
	 *            Hostname of node to bootstrap to
	 * @param bootStrapNodePort
	 *            UDP Port of node to bootstrap to
	 */
	public DHT(String name, int dhtUdpPort, int messageServerUdpPort,
			String bootStrapNodeHostName, int bootStrapNodePort) {
		Kademlia dhtMain = null;
		Node dhtNode = null;
		MessageServer msgServer = null;
		Configuration dhtConfiguration = configure();
		try {
			dhtMain = new Kademlia(Identifier.randomIdentifier(), dhtUdpPort);
			dhtNode = new Node(InetAddress.getLocalHost(),
					messageServerUdpPort, Identifier.randomIdentifier());
			Space space1 = new Space(dhtNode, dhtConfiguration);
			MessageFactory factory1 = new DHTMessageFactoryImpl(null, dhtNode, space1);
			msgServer = new MessageServer(messageServerUdpPort, factory1, 500);
			if (bootStrapNodeHostName != null) { // bootstrap to given node
				dhtMain.connect(new InetSocketAddress(bootStrapNodeHostName,
						bootStrapNodePort));
			} else{
				//currently does nothing
			}
		} catch (RoutingException ex) {
			Logger.getLogger(DHT.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(DHT.class.getName()).log(Level.SEVERE, null, ex);
		}
		dht = dhtMain;
		node = dhtNode;
		messageServer = msgServer;
	}

	/**
	 * Connects to the DHT via the given bootstrap node's hostname and port.
	 * 
	 * @param bootStrapNodeHostName
	 *            Hostname of the DHT Node to connect to
	 * @param bootStrapNodePort
	 *            Port of the DHT Node to connect to
	 * @throws RoutingException
	 *             If the bootstrap node could not be contacted
	 * @throws IOException
	 *             If a network error occurred
	 */
	public boolean connectNode(String bootStrapNodeHostName, int bootStrapNodePort){

		InetSocketAddress socketAddress = new InetSocketAddress(
				bootStrapNodeHostName, bootStrapNodePort);
		try{
		dht.connect(socketAddress);
		return true;
		}
		catch(RoutingException e){
			return false;
		}
		catch(IOException e){
			return false;
		}
	}

	/**
	 * Closes the DHT. Once closed the DHT will not accept any new messages or
	 * DHT operation requests.
	 * 
	 * @throws IOException
	 *             If an error occurred while writing data to disk while closing
	 *             the DHT
	 */
	public boolean closeNode(){
		try{
		dht.close();
		messageServer.close();
		return true;
		}
		catch(IOException e){
			return false;
		}
	}

	/**
	 * Constructs a Kademlia Configuration object. Currently only returns a
	 * default Configuration object.
	 * 
	 * @return Constructed Kademlia Configuration object.
	 */
	private Configuration configure() {
		Configuration dhtConfiguration = new Configuration();
		return dhtConfiguration;
	}

	/**
	 * Adds an object to the DHT with the given key.
	 * 
	 * @param key
	 *            Key of the Object being added to the DHT
	 * @param object
	 *            Object to add to the DHT
	 * @throws RoutingException
	 *             If the operation timed out
	 * @throws IOException
	 *             If a network error occurred
	 */
	public boolean put(String key, Serializable object){
		Identifier id = toIdentifier(key);
		try{
		dht.put(id, object);
		return true;
		}
		catch(RoutingException e){
			return false;
		}
		catch(IOException e){
			return false;
		}
	}

	/**
	 * Checks the DHT for the given key. Returns true if the key is in the DHT
	 * and false if it is not.
	 * 
	 * @param key
	 *            The key to search the DHT for
	 * @return True if the key is found and false if it is not
	 * @throws RoutingException
	 *             If the lookup operation timed out
	 * @throws IOException
	 *             If a network error occurred
	 */
	public boolean canFind(String key) throws RoutingException, IOException {
		Identifier id = toIdentifier(key);
		boolean exists = dht.contains(id);
		return exists;
	}

	/**
	 * Returns the object which is mapped to the given key. If the value is
	 * stored locally no DHT lookup is performed.
	 * 
	 * @param key
	 *            The key of the object to search the DHT for
	 * @return The value mapped to the key or null if no value is mapped to the
	 *         key.
	 * @throws RoutingException
	 *             If the lookup operation timed out
	 * @throws IOException
	 *             If a network error occurred
	 */
	public Serializable get(String key) throws RoutingException, IOException {
		Identifier id = toIdentifier(key);
		Serializable data = dht.get(id);
		return data;
	}

	/**
	 * Removes the object mapped to the given key from the DHT
	 * 
	 * @param key
	 *            The key to the object that is to be removed.
	 * @throws IOException
	 *             If a network error occurred
	 */
	public boolean remove(String key){
		Identifier id = toIdentifier(key);
		try{
		dht.remove(id);
		return true;
		}
		catch(RoutingException e){
			return false;
		}
		catch(IOException e){
			return false;
		}
	}

	/**
	 * Sends the given Message and calls the given Receiver when a reply for the
	 * message is received. If the given receiver is null then replies are
	 * ignored. Returns a unique communication id which can be used to identify
	 * a reply.
	 * 
	 * @param message
	 *            Message to send
	 * @param toIp
	 *            InetAddress of the Message Server of the Node that the Message
	 *            is to be sent to
	 * @param toPort
	 *            UDP Port of the Messaging Server of the Node that the Message
	 *            is to be sent to
	 * @return Unique message ID for the reply. ID is an int.
	 * @throws IOException
	 *             If a network error occurred
	 */
	public int sendMessage(Message message, InetAddress toIp, int toPort,
			Receiver receiver) throws IOException {
		int replyID = messageServer.send(message, toIp, toPort, receiver);
		return replyID;
	}

	/**
	 * Sends a reply Message with the specified message ID.
	 * 
	 * @param message
	 * @param toIp
	 *            InetAddress of the Message Server of the Node that the Message
	 *            is being sent to
	 * @param toPort
	 *            UDP Port of the Messaging Server of the Node that the reply
	 *            Message is to be sent to
	 * @param messageID
	 *            Unique message ID that this message is responding to.
	 * @throws IOException
	 *             If a network error occurred
	 */
	public void replyMessage(Message message, InetAddress toIp, int toPort,
			int messageID) throws IOException {
		messageServer.reply(messageID, message, toIp, toPort);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DHT [dht=" + dht.toString() + ", node=" + node.toString()
				+ ", messageServer=" + messageServer.toString() + "]";
	}

	/**
	 * Utility method to convert a String key into an Identifier object which
	 * can be used as a key within the Kadmelia DHT.
	 * 
	 * @param key
	 *            Key to turn into an Identifier which can be used within the
	 *            DHT
	 * @return The Identifier representation of the given String key
	 */
	private static Identifier toIdentifier(String key) {
		MessageDigest md5;
		Identifier id = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
			md5.update(key.getBytes());
			byte[] md5Bytes = md5.digest();
			md5.reset();
			BigInteger bi = new BigInteger(1, md5Bytes);
			id = new Identifier(bi);
		} catch (NoSuchAlgorithmException ex) {
			Logger.getLogger(DHT.class.getName()).log(Level.SEVERE, null, ex);
		}
		return id;
	}

	/**
	 * Utility method to return the Node object of the DHT.
	 * 
	 * @return the Node object of the DHT
	 */
	public Node getNode() {
		return node;
	}
	
	public static void main(String args[]) throws RoutingException, IOException{
		DHT D = new DHT("SpiderCache", 6661, 6662, "172.31.219.26", 6661);
		String s = (String) D.get("hello");
		System.out.println(s);
	}

}