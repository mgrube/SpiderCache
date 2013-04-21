package com.spidercache.node.caching;

import java.net.CacheResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.cache.CacheResponseStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.cache.CachingHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import com.spidercache.cache.Cache;
import org.apache.http.client.cache.HttpCacheEntry;

public class GetThread extends Thread {

    private final HttpClient httpClient;
    private final HttpContext context;
    private final HttpGet httpget;
    private final Cache cache;
    private final String url;
    
    // Make sure to pass all objects this class will need access to!! (Cache object being one)
    public GetThread(String URL, HttpClient httpClient, Cache c) {
        this.httpClient = httpClient;
        this.context = new BasicHttpContext();
        this.httpget = new HttpGet(URL);
        this.cache = c;
        this.url = URL;
    }

    /**
     * Executes the GetMethod and prints some status information.
     */
    @Override
    public void run() {
    	HttpCacheEntry cacheentry;

        try {
        	if(!cache.isDynamic(url)){        	
            HttpResponse response = httpClient.execute(httpget, context);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
            	switch((CacheResponseStatus) context.getAttribute(CachingHttpClient.CACHE_RESPONSE_STATUS)){
            	// Check message got back to see if didn't travel far enough, and if didn't say cache.remove(URL)
            	case CACHE_HIT:
            			cacheentry = cache.getEntry(url);
            			//cacheentry
            			break;
            	case CACHE_MODULE_RESPONSE:		
            			break;
            	case VALIDATED:
            			break;
            	case CACHE_MISS:
            			break;
            	
            	}
//            	EntityUtils.consume(entity);
//                byte[] bytes = EntityUtils.toByteArray(entity);
//                EntityUtils.consume(entity);
                
               // System.out.println(id + " - " + bytes.length + " bytes read");
            }
        	}
        	else{
        		
        	}
        } catch (Exception e) {
            httpget.abort();
            //System.out.println(id + " - error: " + e);
            e.printStackTrace();
        }
    }

}
