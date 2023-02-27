package org.example.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.util.Fields;
import org.example.model.Audio;
import org.junit.jupiter.api.Test;

class WebServletTest {

	void multipleClientsTest(int noOfClients, int ratioOfTests) throws Exception {

		String getUrl = "http://localhost:9090/library/audio?id=2&key=artistName";

		Audio audioTest = new Audio("artist_name_test", "track_title_test", "album_title_test", "track_number_test",
				"year_test", 100, 100);

		ExecutorService executor = Executors.newFixedThreadPool(noOfClients);
		List<Long> timePeriods = new ArrayList<>();
		long totalStartTime = System.currentTimeMillis();
		HttpClient client = new HttpClient();
		client.start();

		for (int i = 0; i < noOfClients; i++) {
			int id = i + 1;

			executor.execute(() -> {
				for (int j = 0; j < noOfClients - (noOfClients/ratioOfTests); j++) {
					
					  try { long startTime = System.currentTimeMillis(); ContentResponse res =
					  client.GET(getUrl); 
					  assertEquals(res.getStatus(), 200);
					  long finishTime = System.currentTimeMillis() - startTime;
					  timePeriods.add(finishTime);
					  } catch (InterruptedException | ExecutionException | TimeoutException e) {
					  System.out.print(e.getMessage()); }
					 
				}
				for (int j = 0; j < noOfClients/ratioOfTests; j++) {

					try {
						long startTime = System.currentTimeMillis();
						Fields fields = new Fields();
						fields.add("trackTitle", audioTest.getTrackTitle());
						fields.add("artistName", audioTest.getArtistName());
						fields.add("albumTitle", audioTest.getAlbumTitle());
						fields.add("trackNumber", audioTest.getTrackNumber());
						fields.add("year", audioTest.getYear());
						ContentResponse res = client.FORM("http://localhost:9090/library/audio", fields);

						assertEquals(res.getStatus(), 200);
						long finishTime = System.currentTimeMillis() - startTime;
						timePeriods.add(finishTime);

					} catch (InterruptedException | ExecutionException | TimeoutException e) {
						System.out.print(e.getMessage());
					}

				}

			});
		}
		executor.shutdown();
		executor.awaitTermination(10, TimeUnit.MINUTES);
		long totalFinishTime = System.currentTimeMillis() - totalStartTime;
		System.out.println("Round-trip time list: " + timePeriods);
		System.out.println("The number of clients: " + noOfClients + ", ratio: " + ratioOfTests
				+ ":1, total round-trip time: " + totalFinishTime + "ms");

	}

	@Test
	void testAudios10Clients2ratio() throws Exception {
		multipleClientsTest(10, 2);
	}

	@Test
	void testAudios50Clients2ratio() throws Exception {
		multipleClientsTest(50, 2);
	}

	@Test
	void testAudios100Clients2ratio() throws Exception {
		multipleClientsTest(100, 2);
	}

	@Test
	void testAudios10Clients5ratio() throws Exception {
		multipleClientsTest(10, 5);
	}

	@Test
	void testAudios50Clients5ratio() throws Exception {
		multipleClientsTest(50, 5);
	}

	@Test
	void testAudios100Clients5ratio() throws Exception {
		multipleClientsTest(100, 5);
	}

	@Test
	void testAudios10Clients10ratio() throws Exception {
		multipleClientsTest(10, 10);
	}

	@Test
	void testAudios50Clients10ratio() throws Exception {
		multipleClientsTest(50, 10);
	}

	@Test
	void testAudios100Clients10ratio() throws Exception {
		multipleClientsTest(100, 10);
	}

}
