package org.example.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

import org.example.model.Audio;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "audio", value = "audio")
public class AudioServlet extends HttpServlet {

	private ConcurrentHashMap<Integer, Audio> audioDB = new ConcurrentHashMap<>();
	private String[] ALLOWED_AUDIO_FIELDS = { "artistName", "trackTitle", "albumTitle", "trackNumber", "year",
			"reviewCount", "salesCount" };

	@Override
	public void init() throws ServletException {
//		audioDB.put("one", new Audio());
		audioDB.put(1,
				new Audio("artist_name_1", "artist_name_1", "artist_name_1", "artist_name_1", "artist_name_1", 1, 1));
		audioDB.put(2,
				new Audio("artist_name_2", "artist_name_2", "artist_name_1", "artist_name_1", "artist_name_1", 1, 1));
//		audioDB.put("id_2", "artist_name_2");
//		audioDB.put("id_3", "artist_name_3");
//		audioDB.put("id_4", "artist_name_4");
//		audioDB.put("id_5", "artist_name_5");
//		audioDB.put("id_6", "artist_name_6");	
//		artist_name_2, track_title_2, album_title_2, track_number_2, year_2, review_count_1, sales_count_1

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			StringBuilder message = new StringBuilder();
			String key = request.getParameter("key");
			String id = request.getParameter("id");
			System.out.println("id: " + id + " key: " + key);
			String getAll = request.getParameter("getAll");
			if (getAll.equalsIgnoreCase("true")) {
				response.setStatus(200);
				audioDB.values()
						.forEach(audio -> message.append("artistName: " + audio.getArtistName() + ", trackTitle: "
								+ audio.getTrackTitle() + ", albumTitle: " + audio.getAlbumTitle() + ", trackNumber: "
								+ audio.getTrackNumber() + ", year: " + audio.getYear() + ", reviewCount: "
								+ audio.getReviewCount() + ", salesCount:" + audio.getSalesCount() + "\n"));
				response.getOutputStream().println(message.toString());
			} else {
				if (id == null) {
					response.setStatus(400);
					response.getOutputStream().println("GET request parameter 'id' to tbe passed");

				} else if (key == null) {
					response.setStatus(400);
					response.getOutputStream().println("GET request parameter 'key' to tbe passed");

				} else if (!Arrays.asList(ALLOWED_AUDIO_FIELDS).contains(key)) {
					response.setStatus(400);
					response.getOutputStream().println(
							"Invalid KEY provided. Allowed KEY values are: " + String.join(", ", ALLOWED_AUDIO_FIELDS));
				} else {
					if (!audioDB.containsKey(Integer.parseInt(id))) {
						response.setStatus(400);
						response.getOutputStream().println("Invalid Id provided. ");
					} else {
						Audio audio = audioDB.get(Integer.parseInt(id));
						String artistName = audio.getArtistName();
						String trackTitle = audio.getTrackTitle();
						String albumTitle = audio.getAlbumTitle();
						String trackNumber = audio.getTrackNumber();
						String year = audio.getYear();
						Integer reviewCount = audio.getReviewCount();
						Integer salesCount = audio.getSalesCount();
						String result = "";
						if (key.equals("artistName"))
							result = artistName != null ? artistName : "";
						else if (key.equals("trackTitle"))
							result = trackTitle != null ? trackTitle : "";
						else if (key.equals("albumTitle"))
							result = albumTitle != null ? albumTitle : "";
						else if (key.equals("trackNumber"))
							result = trackNumber != null ? trackNumber : "";
						else if (key.equals("year"))
							result = year != null ? year : "";
						else if (key.equals("reviewCount"))
							result = reviewCount != null ? reviewCount.toString() : "0";
						else if (key.equals("salesCount"))
							result = salesCount != null ? salesCount.toString() : "0";

						System.out.println(
								"GET Audio with id: " + id + " successful!\n key: " + key + " value: " + result);
						response.setStatus(200);
						response.getOutputStream().println(
								"GET Audio with id: " + id + " successful!\n key: " + key + " value: " + result);
					}
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			response.setStatus(500);
			response.getOutputStream().println("Error : " + e.getLocalizedMessage());
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String artistName = "";
		String trackTitle = "";
		String albumTitle = "";
		String trackNumber = "";
		String year = "";
		Integer reviewCount = 0;
		Integer salesCount = 0;
		if (request.getParameter("artistName") != null)
			artistName = request.getParameter("artistName");
		if (request.getParameter("trackTitle") != null)
			trackTitle = request.getParameter("trackTitle");
		if (request.getParameter("albumTitle") != null)
			albumTitle = request.getParameter("albumTitle");
		if (request.getParameter("trackNumber") != null)
			trackNumber = request.getParameter("trackNumber");
		if (request.getParameter("year") != null)
			year = request.getParameter("year");
		if (request.getParameter("reviewCount") != null)
			reviewCount = Integer.parseInt(request.getParameter("reviewCount"));
		if (request.getParameter("salesCount") != null)
			salesCount = Integer.parseInt(request.getParameter("salesCount"));
		Audio audio = new Audio(artistName, trackTitle, albumTitle, trackNumber, year, reviewCount, salesCount);
		Integer len = audioDB.size();
		len = len + 1;
		audio.setId(len);
		audioDB.put(len, audio);
		response.setStatus(200);
		response.getOutputStream().println("Audio with Id: " + len + " is added to the database.");
	}
}