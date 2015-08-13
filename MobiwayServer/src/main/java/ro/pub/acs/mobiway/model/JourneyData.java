package ro.pub.acs.mobiway.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "journey_data")
public class JourneyData implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Integer id;

	@Column(name = "latitude", nullable = true)
	private Float latitude;

	@Column(name = "longitude", nullable = true)
	private Float longitude;

	@Column(name = "speed", nullable = true)
	private Integer speed;

	@Basic(optional = false)
	@NotNull
	@Column(name = "timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	@Basic(optional = false)
	@NotNull
	@Column(name = "timestamp_unix")
	private Integer timestampUnix;

	@Lob
	@Column(name = "osm_way_id", nullable = true)
	private String osmWayId;

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

		// Extract Unix time (seconds) from timestamp
		this.timestampUnix =
                    new Integer((int) (timestamp.getTime() / 1000L));
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public Integer getSpeed() {
		return speed;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public Integer getTimestampUnix() {
		return timestampUnix;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;

		// Extract Unix time (seconds) from timestamp
		this.timestampUnix =
                    new Integer((int) (timestamp.getTime() / 1000L));

	}

	public String getOsmWayId() {
		return osmWayId;
	}

	public void setOsmWayId(String osmWayId) {
		this.osmWayId = osmWayId;
	}

	public Journey getJourneyId() {
		return journeyId;
	}

	public void setJourneyId(Journey journeyId) {
		this.journeyId = journeyId;
	}

	@Override
	public String toString() {
		return "ro.pub.acs.mobiway.model.JourneyData[ id=" + id
				+ " ]";
	}

}
