package ro.pub.acs.mobiway.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "user_events")
public class UserEvent implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Integer id;

	@JoinColumn(name = "id_user", referencedColumnName = "id")
	@ManyToOne(optional = false)
	private User idUser;

	@JoinColumn(name = "id_traffic_event", referencedColumnName = "id")
	@ManyToOne(optional = false)
	private TrafficEvent idTrafficEvent;

	@Basic(optional = false)
	@NotNull
	@Column(name = "timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	@Column(name = "distance", nullable = true)
	private Float distance;

	@Column(name = "time_since_event", nullable = true)
	private Float timeSinceEvent;

	@Column(name = "space_accuracy", nullable = true)
	private Float spaceAccuracy;

	@Column(name = "time_accuracy", nullable = true)
	private Float timeAccuracy;

	@Column(name = "latitude", nullable = true)
	private Float latitude;

	@Column(name = "longitude", nullable = true)
	private Float longitude;

	@Lob
	@Column(name = "osm_way_id", nullable = true)
	private String osmWayId;

	public UserEvent() {
	}

	public UserEvent(Integer id) {
		this.id = id;
	}

	public UserEvent(Integer id, User user, TrafficEvent trafficEvent, Date timestamp,
		 Float distance, Float timeSinceEvent, Float spaceAccuracy, Float timeAccuracy,
		 Float latitude, Float longitude, String osmWayId) {
		this.id = id;
		this.idUser = user;
		this.idTrafficEvent = trafficEvent;
		this.timestamp = timestamp;
		this.distance = distance;
		this.timeSinceEvent = timeSinceEvent;
		this.spaceAccuracy = spaceAccuracy;
		this.timeAccuracy = timeAccuracy;
		this.latitude = latitude;
		this.longitude = longitude;
		this.osmWayId = osmWayId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getIdUser() {
		return idUser;
	}

	public void setIdUser(User user) {
		this.idUser = user;
	}

	public TrafficEvent getIdTrafficEvent() {
		return idTrafficEvent;
	}

	public void setIdTrafficEvent(TrafficEvent event) {
		this.idTrafficEvent = event;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Float getDistance() {
		return distance;
	}

	public void setDistance(Float distance) {
		this.distance = distance;
	}

	public Float getTimeSinceEvent() {
		return timeSinceEvent;
	}

	public void setTimeSinceEvent(Float timeSinceEvent) {
		this.timeSinceEvent = timeSinceEvent;
	}
 
	public Float getSpaceAccuracy() {
		return spaceAccuracy;
	}

	public void setSpaceAccuracy(Float spaceAccuracy) {
		this.spaceAccuracy = spaceAccuracy;
	}

	public Float getTimeAccuracy() {
		return timeAccuracy;
	}

	public void setTimeAccuracy(Float timeAccuracy) {
		this.timeAccuracy = timeAccuracy;
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

	public String getOsmWayId() {
		return osmWayId;
	}

	public void setOsmWayId(String osmWayId) {
		this.osmWayId = osmWayId;
	}

	@Override
	public String toString() {
		return "ro.pub.acs.mobiway.model.UserEvent[ id=" + id
				+ " ]";
	}

}
