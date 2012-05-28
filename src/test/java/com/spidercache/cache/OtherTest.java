package com.spidercache.cache;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.cache.CacheConfig;
import org.apache.http.impl.client.cache.CachingHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.spidercache.utils.IntermediateHttpClient;

public class OtherTest {
	
	public static void main(String[] args) throws Exception {
        // Create an HttpClient with the ThreadSafeClientConnManager.
        // This connection manager must be used if more than one thread will
        // be using the HttpClient.
		
		IntermediateHttpClient interClient = new IntermediateHttpClient();
		((ThreadSafeClientConnManager)interClient.getConnectionManager()).setMaxTotal(100);
        CacheConfig config = new CacheConfig();
        config.setMaxObjectSizeBytes(2<<24);
        CachingHttpClient httpclient = new CachingHttpClient(new IntermediateHttpClient(), new Cache((long)2<<34, "places"), config);

        try {
            // create an array of URIs to perform GETs on
            String[] urisToGet = {
                "http://www.gutenberg.org/files/76/76-h/76-h/76-h.htm",
                "http://www.gutenberg.org/cache/epub/10947/pg10947.html"
                //"http://hc.apache.org/httpcomponents-client-ga/",
                //"http://svn.apache.org/viewvc/httpcomponents/"
            };

            // create a thread for each URI
            GetThread[] threads = new GetThread[10];
            for (int i = 0; i < threads.length; i++) {
                HttpGet httpget = new HttpGet(urisToGet[i%2]);
                threads[i] = new GetThread(httpclient, httpget, i + 1);
            }

            // start the threads
            for (int j = 0; j < threads.length; j++) {
                threads[j].start();
               // threads[j].join();
            }

            // join the threads
            for (int j = 0; j < threads.length; j++) {
                threads[j].join();
            }

        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }
    }

    /**
     * A thread that performs a GET.
     */
    static class GetThread extends Thread {

        private final HttpClient httpClient;
        private final HttpContext context;
        private final HttpGet httpget;
        private final int id;

        public GetThread(HttpClient httpClient, HttpGet httpget, int id) {
            this.httpClient = httpClient;
            this.context = new BasicHttpContext();
            this.httpget = httpget;
            this.id = id;
        }

        /**
         * Executes the GetMethod and prints some status information.
         */
        @Override
        public void run() {

            //System.out.println(id + " - about to get something from " + httpget.getURI());

            try {

                // execute the method
                HttpResponse response = httpClient.execute(httpget, context);

                //System.out.println(id + " - get executed");
                // get the response body as an array of bytes
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                	EntityUtils.consume(entity);
                    byte[] bytes = EntityUtils.toByteArray(entity);
                    EntityUtils.consume(entity);
                    
                   // System.out.println(id + " - " + bytes.length + " bytes read");
                }

            } catch (Exception e) {
                httpget.abort();
                //System.out.println(id + " - error: " + e);
                e.printStackTrace();
            }
        }

    }
    
    static class PostThread extends Thread {

        private final HttpClient httpClient;
        private final HttpContext context;
        private final HttpPost httppost;
        private final int id;

        public PostThread(HttpClient httpClient, HttpPost httppost, int id, List<NameValuePair> postData) {
            this.httpClient = httpClient;
            this.context = new BasicHttpContext();
            this.httppost = httppost;
            this.id = id;
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

            System.out.println(id + " - about to get something from " + httppost.getURI());

            try {

                // execute the method
                HttpResponse response = httpClient.execute(httppost, context);

                System.out.println(id + " - get executed");
                // get the response body as an array of bytes
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    byte[] bytes = EntityUtils.toByteArray(entity);
                    EntityUtils.consume(entity);
                    System.out.println(id + " - " + bytes.length + " bytes read");
                }

            } catch (Exception e) {
                httppost.abort();
                System.out.println(id + " - error: " + e);
                e.printStackTrace();
            }
        }

    }
}
