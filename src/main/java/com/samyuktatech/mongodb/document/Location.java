package com.samyuktatech.mongodb.document;

import java.util.Date;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Document(collection="locations")
public class Location {

	@Id 
	private String id;
	private double latitude;
	private double longitude;
	
	@DateTimeFormat(pattern="yyyy/mm/dd h:mm:ss")
	private Date time;
	
	private Long userId;
	
	public Location() {}
	
	public Location(double latitude, double longitude, Long userId) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.userId = userId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
	
}
