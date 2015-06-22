package ro.pub.acs.traffic.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "location")
public class Location implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@Column(name = "id_user")
	private Integer idUser;

	@Basic(optional = false)
	@NotNull
	@Column(name = "latitude")
	private float latitude;

	@Basic(optional = false)
	@NotNull
	@Column(name = "longitude")
	private float longitude;

	@Basic(optional = false)
	@NotNull
	@Column(name = "speed")
	private int speed;

	@Column(name = "timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	@JoinColumn(name = "id_user", referencedColumnName = "id", insertable = false, updatable = false)
	@Transient
	@OneToOne(optional = false)
	private User user;

	public Location() {
	}

	public Location(Integer idUser) {
		this.idUser = idUser;
	}

	public Location(Integer idUser, float latitude, float longitude, int speed,
			Date timestamp) {
		this.idUser = idUser;
		this.latitude = latitude;
		this.longitude = longitude;
		this.speed = speed;
		this.timestamp = timestamp;
	}

	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
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

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "ro.pub.acs.traffic.model.Location[ idUser=" + idUser + " ]";
	}

}
