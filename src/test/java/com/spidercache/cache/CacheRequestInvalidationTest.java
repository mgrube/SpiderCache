package com.spidercache.cache;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;


public class CacheRequestInvalidationTest {

	private String url1;
	private String url2;
	private String url3;
	private String url4;
	
	@Before
	public void initUrl()
	{
		url1 = "http://www.google.com/" +
				"#hl=en&sugexp=ppwl&cp=20&gs_id=2d&xhr=t&q=java+string+matches+example" +
				"&pf=p&sclient=psy-ab&source=hp&pbx=1&oq=java+string+matches+&aq=0" +
				"&aqi=g4&aql=&gs_sm=&gs_upl=&bav=on.2,or.r_gc.r_pw.,cf.osb" +
				"&fp=24c9ddab306e6d80&biw=1680&bih=938";
		url2 = "https://accounts.google.com/ServiceLogin?service=mail&passive=true" +
				"&rm=false&continue=http://mail.google.com/mail/&scc=1&ltmpl=default&ltmplcache=2";
		url3 = "http://stackoverflow.com/questions/3475076/" +
				"retrieve-the-fragment-hash-from-a-url-and-inject-the-values-into-the-bean";
		url4 = "http://www.reddit.com/?count=25&after=t3_marhi";
	}
	
	@Test
	public void UrlTest()
	{
		assertEquals(true, Cache.isDynamic(url1));
		assertEquals(false, Cache.isHttpsRequest(url1));
		assertEquals(true, Cache.isDynamic(url2));
		assertEquals(true, Cache.isHttpsRequest(url2));
		assertEquals(false, Cache.isDynamic(url3));
		assertEquals(false, Cache.isHttpsRequest(url3));
		assertEquals(true, Cache.isDynamic(url4));
		assertEquals(false, Cache.isHttpsRequest(url4));
	}
}
