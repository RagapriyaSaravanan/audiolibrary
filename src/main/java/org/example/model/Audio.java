package org.example.model;

import java.io.Serializable;

public class Audio{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String artistName;
	private String trackTitle;
	private String albumTitle;
	private String trackNumber;
	private String year;
	private Integer reviewCount;
	private Integer salesCount;
	
		
	public Audio(String artistName, String trackTitle, String albumTitle, String trackNumber, String year, int reviewCount, int salesCount) {
		// TODO Auto-generated constructor stub
		this.id = 1;
		this.artistName = artistName;
		this.trackTitle = trackTitle;
		this.albumTitle = albumTitle;
		this.trackNumber = trackNumber;
		this.year = year;
		this.reviewCount = reviewCount;
		this.salesCount = salesCount;
	}
	public String getArtistName() {
		return artistName;
	}
	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}
	public String getTrackTitle() {
		return trackTitle;
	}
	public void setTrackTitle(String trackTitle) {
		this.trackTitle = trackTitle;
	}
	public String getAlbumTitle() {
		return albumTitle;
	}
	public void setAlbumTitle(String albumTitle) {
		this.albumTitle = albumTitle;
	}
	public String getTrackNumber() {
		return trackNumber;
	}
	public void setTrackNumber(String trackNumber) {
		this.trackNumber = trackNumber;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public Integer getReviewCount() {
		return reviewCount;
	}
	public void setReviewCount(Integer reviewCount) {
		this.reviewCount = reviewCount;
	}
	public Integer getSalesCount() {
		return salesCount;
	}
	public void setSalesCount(Integer salesCount) {
		this.salesCount = salesCount;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

}
