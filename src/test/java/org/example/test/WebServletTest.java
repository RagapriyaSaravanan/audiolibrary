package org.example.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import org.apache.commons.io.IOUtils;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.HttpResponse;
import org.eclipse.jetty.client.api.ContentProvider;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.http.HttpHeader;
import org.example.model.Audio;
//import org.example.model.Artist;


class WebServletTest {

//	//@Test
//	void testHelloServletGet() throws Exception {
//		
//		HttpClient client = new HttpClient();
//        client.start();
//
//        ContentResponse res = client.GET("http://localhost:9090/coen6317/HelloServlet");
//        
//        System.out.println(res.getContentAsString());
//        
//        client.stop();
//		
//	}
//	
//	
//	//@Test
//	void testBlockingServletGet() throws Exception {
//		
//		HttpClient client = new HttpClient();
//        client.start();
//
//        ContentResponse res = client.GET("http://localhost:9090/coen6317/BlockingServlet");
//        
//        System.out.println(res.getContentAsString());
//        
//        client.stop();
//		
//	}
//	
//	//@Test
//	void testAsyncServletGet() throws Exception {
//		
//		String url = "http://localhost:9090/coen6317/longtask";
//		HttpClient client = new HttpClient();
//        client.start();
//
//        ContentResponse response = client.GET(url);
//
//		assertThat(response.getStatus(), equalTo(200));
//		
//		String responseContent = IOUtils.toString(response.getContent());
//		
//		 System.out.println(responseContent);
//		//assertThat(responseContent, equalTo( "This is some heavy resource that will be served in an async way"));
//		
//	}
//
//	
//	@Test
//	void testArtistsGet() throws Exception {
//		String url = "http://localhost:9090/coen6317/artists";
//		HttpClient client = new HttpClient();
//        client.start();
//
//        Request request = client.newRequest(url);
//        request.param("id","id200");
//        ContentResponse response = request.send();
//   
//
//		assertThat(response.getStatus(), equalTo(200));
//		
//		String responseContent = IOUtils.toString(response.getContent());
//		
//		 System.out.println(responseContent);
//		client.stop();
//		
//	}
//	
//	@SuppressWarnings("deprecation")
//	@Test
//	void testArtistsPost() throws Exception {
//		
//		String url = "http://localhost:9090/coen6317/artists";
//		HttpClient client = new HttpClient();
//        client.start();
//        
//        Request request = client.POST(url);
//        
//        request.param("id","id200");
//        request.param("name","artist200");
//        
//        ContentResponse response = request.send();
//		String res = new String(response.getContent());
//		System.out.println(res);
//		client.stop();
//	}
//	
	void multipleClientsTest(int noOfClients, int ratioOfTests) throws Exception{
		
		String url = "http://localhost:9090/library/audio?getAll=true";
		ExecutorService executor = Executors.newFixedThreadPool(noOfClients);
		List<Long> timePeriods = new ArrayList<>();
		long totalStartTime = System.currentTimeMillis();
		HttpClient client = new HttpClient();
		client.start();
		
		
		for(int i = 0; i < noOfClients; i++) {
			//int clientID = i + 1;
			
			executor.execute(()->{
				for (int j = 0; j < ratioOfTests; j++) {
					try {
						long startTime = System.currentTimeMillis();
						ContentResponse res = client.GET(url);
						assertThat(res.getStatus(), equalTo(200));

						long finishTime = System.currentTimeMillis() - startTime;
						timePeriods.add(finishTime);
						
					} catch (InterruptedException | ExecutionException | TimeoutException e) {
						System.out.print(e.getMessage());
					}
				}
			
				
				/*
				 * ObjectMapper mapper = new ObjectMapper(); Audio audio = new Audio(3 +
				 * clientID, "pefadfe","dafdf","Candy Shop",12,1991,11,10); try { String
				 * jsonString = mapper.writeValueAsString(audio);
				 * 
				 * // calculate round-time long startTime = System.currentTimeMillis();
				 * 
				 * ContentResponse res = client.POST(uri) .content(new
				 * StringContentProvider(jsonString), "application/json") .send();
				 * assertThat(res.getStatus(), equalTo(200)); //
				 * System.out.println(res.getContentAsString());
				 * 
				 * long roundTime = System.currentTimeMillis() - startTime;
				 * roundTimes.add(roundTime);
				 * 
				 * } catch (InterruptedException | TimeoutException | ExecutionException |
				 * JsonProcessingException e) { System.out.print(e.getMessage()); }
				 */
			});
		}
		executor.shutdown();
		executor.awaitTermination(10, TimeUnit.MINUTES);
		long totalFinishTime = System.currentTimeMillis() - totalStartTime;
		System.out.println("Round-trip time list: "+ timePeriods);
		System.out.println("The number of clients: " + noOfClients + ", ratio: "+ ratioOfTests + ":1, total round-trip time: " + totalFinishTime + "ms");
		
	}
	
	@Test
	void testAudios10Clients2ratio() throws Exception {
		multipleClientsTest(10,2);
	}
	@Test
	void testAudios50Clients2ratio() throws Exception {
		multipleClientsTest(50,2);
	}
	@Test
	void testAudios100Clients2ratio() throws Exception {
		multipleClientsTest(100,2);
	}
	@Test
	void testAudios10Clients5ratio() throws Exception {
		multipleClientsTest(10,5);
	}
	@Test
	void testAudios50Clients5ratio() throws Exception {
		multipleClientsTest(50,5);
	}
	@Test
	void testAudios100Clients5ratio() throws Exception {
		multipleClientsTest(100,5);
	}
	@Test
	void testAudios10Clients10ratio() throws Exception {
		multipleClientsTest(10,10);
	}
	
	@Test
	void testAudios100Clients10ratio() throws Exception {
		multipleClientsTest(100,10);
	}
	
}






