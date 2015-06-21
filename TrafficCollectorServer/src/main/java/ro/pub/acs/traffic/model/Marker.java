package ro.pub.acs.traffic.model;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "marker")
public class Marker {

	private String name;
	private String address;
	private String lat;
	private String lng;
	private String speed;
	private String type;

	public String getName() {
		return name;
	}

	@XmlElement
	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	@XmlElement
	public void setAddress(String address) {
		this.address = address;
	}

	public String getLat() {
		return lat;
	}

	@XmlElement
	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	@XmlElement
	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getSpeed() {
		return speed;
	}

	@XmlElement
	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getType() {
		return type;
	}

	@XmlElement
	public void setType(String type) {
		this.type = type;
	}
}
