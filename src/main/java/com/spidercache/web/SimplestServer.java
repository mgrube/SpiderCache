package com.spidercache.web;

import org.eclipse.jetty.server.Server;
import com.spidercache.node.Node;

public class SimplestServer {

	public static void main(String[] args) throws Exception {

		Node n = new Node("/home/michael/cache");
		Server server = new Server(8080);
		server.setHandler(new HelloHandler(n));

		server.start();
		server.join();

	}

}
