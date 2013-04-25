package com.spidercache.utils;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.cache.CacheResponseStatus;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

public class IntermediateHttpClient implements HttpClient{

	private final DefaultHttpClient client;
	private final ThreadSafeClientConnManager connManager;
	
	public IntermediateHttpClient()
	{
		connManager = new ThreadSafeClientConnManager();
		client = new DefaultHttpClient(connManager);
	}

	public HttpResponse execute(HttpHost arg0, HttpRequest arg1,
			HttpContext arg2) throws IOException, ClientProtocolException {
		// TODO Auto-generated method stub
		HttpResponse response = null;
//		switch ((CacheResponseStatus)arg2.getAttribute("spider.cache.response"))
//		{
		//This will cause the request to be sent to the origin server. 
//		case CACHE_ISSUE_TO_HOST:
//			response =  client.execute(arg0, arg1, arg2);
//		case CACHE_ISSUE_TO_REMOTE_NODE:
			//You will program the logic for requesting the content from other node's hear.
			//The logic will need to return an HttpResposne.
///			response = client.execute(arg0, arg1, arg2);
//		}
		
		return response;
	}

	public <T> T execute(HttpUriRequest arg0,
			ResponseHandler<? extends T> arg1, HttpContext arg2)
			throws IOException, ClientProtocolException {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> T execute(HttpHost arg0, HttpRequest arg1,
			ResponseHandler<? extends T> arg2) throws IOException,
			ClientProtocolException {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> T execute(HttpHost arg0, HttpRequest arg1,
			ResponseHandler<? extends T> arg2, HttpContext arg3)
			throws IOException, ClientProtocolException {
		// TODO Auto-generated method stub
		return null;
	}

	public ClientConnectionManager getConnectionManager() {
		// TODO Auto-generated method stub
		return connManager;
	}

	public HttpParams getParams() {
		// TODO Auto-generated method stub
		return client.getParams();
	}

	public HttpResponse execute(HttpUriRequest arg0) throws IOException,
			ClientProtocolException {
		// TODO Auto-generated method stub
		return null;
	}

	public HttpResponse execute(HttpUriRequest arg0, HttpContext arg1)
			throws IOException, ClientProtocolException {
		// TODO Auto-generated method stub
		return null;
	}

	public HttpResponse execute(HttpHost arg0, HttpRequest arg1)
			throws IOException, ClientProtocolException {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> T execute(HttpUriRequest arg0, ResponseHandler<? extends T> arg1)
			throws IOException, ClientProtocolException {
		// TODO Auto-generated method stub
		return null;
	}

}
