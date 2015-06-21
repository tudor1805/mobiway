package ro.pub.acs.traffic.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "journey_data")
public class JourneyData implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Integer id;

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

	@Basic(optional = false)
	@NotNull
	@Column(name = "timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	@JoinColumn(name = "journey_id", referencedColumnName = "id")
	@ManyToOne(optional = false)
	private Journey journeyId;

	public JourneyData() {
	}

	public JourneyData(Integer id) {
		this.id = id;
	}

	public JourneyData(Integer id, float latitude, float longitude, int speed,
			Date timestamp) {
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
		this.speed = speed;
		this.timestamp = timestamp;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Journey getJourneyId() {
		return journeyId;
	}

	public void setJourneyId(Journey journeyId) {
		this.journeyId = journeyId;
	}

	@Override
	public String toString() {
		return "ro.pub.acs.traffic.model.JourneyData[ id=" + id
				+ " ]";
	}

}
