package com.spidercache.web;

import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.Request;
import com.spidercache.node.caching.*;
import java.io.InputStream;
import com.spidercache.node.Node;
import org.apache.commons.io.*;
import java.io.StringWriter;


public class HelloHandler extends AbstractHandler {

	private Node node; 
	
	public HelloHandler(Node n){
		this.node = n;
	}
	
	// This method needs to:
	// Detect whether the traffic is a GET or POST method
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		StringBuffer urlbuffer = request.getRequestURL();
		String url = urlbuffer.toString();
		
		if(request.getMethod().equals("GET")){
			response.setContentType("text/html;charset=utf-8");
			response.setStatus(HttpServletResponse.SC_OK);
			baseRequest.setHandled(true);
			
			InputStream is = node.getCache().getEntry(url).getResource().getInputStream();
			StringWriter writer = new StringWriter();
			IOUtils.copy(is, writer, null);
			String body = writer.toString();
			response.getWriter().println(body);
		}
		else if(request.getMethod().equals("POST")){
			//PostThread PT = new PostThread();
		}
		response.getWriter().println("<h1>The method you used is: " + request.getMethod() + "</h1>");
		
		System.out.println("\nREQUEST");
		System.out.println("URI: " + request.getRequestURI());
		System.out.println("URL: " + request.getRequestURL());
		System.out.println("servlet path: " + request.getServletPath());
		//Enumeration<String> requestHeaderNames = request.getHeaderNames(
//
//		System.out.println("header names:");
//		String element;
//		while (requestHeaderNames.hasMoreElements()) {
//			element = requestHeaderNames.nextElement();
//			System.out.println("\t" + element);
//			System.out.println("\t\t" + request.getHeader(element));
//		}

		System.out.println("\nRESPONSE");
		System.out.println("status: " + response.getStatus());
		Collection<String> responseHeaderNames = response.getHeaderNames();

		System.out.println("header names:");
//		Iterator<String> iter = responseHeaderNames.iterator();
//		while (iter.hasNext()) {
//			element = iter.next();
//			System.out.println("\t" + element);
//			System.out.println("\t\t" + request.getHeader(element));
//		}
	}

}
