package com.spidercache.node.caching;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.cache.CacheResponseStatus;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.cache.CachingHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.spidercache.cache.Cache;

class PostThread extends Thread {

    private final HttpClient httpClient;
    private final HttpContext context;
    private final HttpPost httppost;
    private final Cache cache;
    
    public PostThread(HttpClient httpClient, HttpPost httppost, List<NameValuePair> postData, Cache cache) {
        this.httpClient = httpClient;
        this.context = new BasicHttpContext();
        this.httppost = httppost;
        this.cache = cache;
        for(int i =0; i<postData.size(); i++)
        {
        	try {
				httppost.setEntity(new UrlEncodedFormEntity(postData));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }

    /**
     * Executes the GetMethod and prints some status information.
     */
    @Override
    public void run() {

        //System.out.println(id + " - about to get something from " + httppost.getURI());

        try {

            // execute the method
            HttpResponse response = httpClient.execute(httppost, context);

          //  System.out.println(id + " - get executed");
            // get the response body as an array of bytes
            HttpEntity entity = response.getEntity();


        } catch (Exception e) {
            httppost.abort();
            //System.out.println(id + " - error: " + e);
            e.printStackTrace();
        }
    }

}