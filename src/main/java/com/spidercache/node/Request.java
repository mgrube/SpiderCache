package com.spidercache.node;

import org.apache.http.HttpResponse;
import java.util.Calendar;
import java.util.ArrayList;
import java.net.InetAddress;

/**
 * Basic container class to be used for passing requests between nodes.
 * @author Michael Grube
 *
 */

public class Request {

	public enum Priority {EMERGENCY, HIGH, MEDIUM, LOW};
	
	private ArrayList<String> url;
	private Calendar RequestTime;
	private HttpResponse Content;
	private InetAddress Originator;
	private Priority p;
	private String DataHash;
	private int SearchRadius;
	
	public int getHTL() {
		return SearchRadius;
	}
	
	public void setHTL(int searchRadius) {
		SearchRadius = searchRadius;
	}
	
	public String getDataHash() {
		return DataHash;
	}
	
	public void setDataHash(String dataHash) {
		DataHash = dataHash;
	}
	public Priority getPriority() {
		return p;
	}
	
	public void setPriority(Priority p) {
		this.p = p;
	}
	
	public HttpResponse getResponse() {
		return Content;
	}
	public void setResponse(HttpResponse hR) {
		Content = hR;
	}
	
	public InetAddress getOriginator() {
		return Originator;
	}
	
	public void setOriginator(InetAddress originator) {
		Originator = originator;
	}
	
	public Calendar getRequestTime() {
		return RequestTime;
	}
	
	public void setRequestTime(Calendar requestTime) {
		RequestTime = requestTime;
	}
	
	public ArrayList<String> getURL() {
		return url;
	}
	
	public void addURL(String uRL) {
		url.add(uRL);
	}
	
}
