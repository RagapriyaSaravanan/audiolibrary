package org.example.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.example.model.Audio;

import com.google.gson.Gson;

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

		int length = audioDB.size();

		Audio audio1 = new Audio("artist_name_1", "track_title_1", "album_title_1", "track_number_1", "year_1", 11, 12);
		audio1.setId(++length);
		audioDB.put(length, audio1);

		Audio audio2 = new Audio("artist_name_2", "track_title_2", "album_title_2", "track_number_2", "year_2", 15, 25);
		audio2.setId(++length);
		audioDB.put(length, audio2);

		Audio audio3 = new Audio("artist_name_3", "track_title_3", "album_title_3", "track_number_3", "year_3", 40,
				209);
		audio3.setId(++length);
		audioDB.put(length, audio3);

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String key = request.getParameter("key");
			String id = request.getParameter("id");
			System.out.println("id: " + id + " key: " + key);
			String getAll = request.getParameter("getAll");
			HashMap<String, String> map = new HashMap<>();
			Gson gson = new Gson();
			if (getAll != null && getAll.equalsIgnoreCase("true")) {
				response.setStatus(200);

				String jsonString = gson.toJson(audioDB.values());

				PrintWriter out = response.getWriter();
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				out.print(jsonString);
				out.flush();
			} else {
				if (id == null) {
					response.setStatus(400);
//					response.getOutputStream().println("GET request : parameter 'id' to tbe passed");
					map.put("message", "GET request : parameter 'id' to tbe passed");
					String jsonString = gson.toJson(map);
					PrintWriter out = response.getWriter();
					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					out.print(jsonString);
					out.flush();

				} else if (key == null) {
					response.setStatus(400);
//					response.getOutputStream().println("GET request : parameter 'key' to tbe passed");
					map.put("message", "GET request : parameter 'key' to tbe passed");
					String jsonString = gson.toJson(map);
					PrintWriter out = response.getWriter();
					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					out.print(jsonString);
					out.flush();

				} else if (!Arrays.asList(ALLOWED_AUDIO_FIELDS).contains(key)) {
					response.setStatus(400);
//					response.getOutputStream().println(
//							"Invalid KEY provided. Allowed KEY values are: " + String.join(", ", ALLOWED_AUDIO_FIELDS));
					map.put("message", "Invalid KEY provided. Allowed KEY values are: " + String.join(", ", ALLOWED_AUDIO_FIELDS));
					String jsonString = gson.toJson(map);
					PrintWriter out = response.getWriter();
					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					out.print(jsonString);
					out.flush();

				} else {
					if (!audioDB.containsKey(Integer.parseInt(id))) {
						response.setStatus(400);
//						response.getOutputStream().println("Invalid Id provided. ");
						map.put("message", "Invalid Id provided. ");
						String jsonString = gson.toJson(map);
						PrintWriter out = response.getWriter();
						response.setContentType("application/json");
						response.setCharacterEncoding("UTF-8");
						out.print(jsonString);
						out.flush();

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
						response.setStatus(200);
//						response.getOutputStream().println(
//								"GET Audio with id: " + id + " successful!\n key: " + key + " value: " + result);
						map.put("message", "GET Audio with id: " + id + " successful!\n key: " + key + " value: " + result);
						String jsonString = gson.toJson(map);
						PrintWriter out = response.getWriter();
						response.setContentType("application/json");
						response.setCharacterEncoding("UTF-8");
						out.print(jsonString);
						out.flush();
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
		try {
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
		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
		}
	}
}
