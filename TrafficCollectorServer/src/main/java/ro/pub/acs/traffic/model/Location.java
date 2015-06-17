package ro.pub.acs.traffic.model;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import ro.pub.acs.traffic.config.TimestampSerializer;

public class Location {
	private long id_user;
	private String speed;
	private Date timestamp;
	private float latitude;
	private float longitude;
	
	public long getId_user() {
		return id_user;
	}
	public void setId_user(long id_user) {
		this.id_user = id_user;
	}
	public String getSpeed() {
		return speed;
	}
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	@JsonSerialize(using = TimestampSerializer.class)
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
}
