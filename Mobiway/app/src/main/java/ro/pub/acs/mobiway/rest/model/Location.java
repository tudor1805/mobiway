package ro.pub.acs.mobiway.rest.model;

import java.util.Date;

public class Location {

    private Integer idUser;
    private Float latitude;
    private Float longitude;
    private Integer speed;
    private Date timestamp;

    public Location() {
    }

    public Location(Integer idUser) {
        this.idUser = idUser;
    }

    public Location(Integer idUser, Float latitude, Float longitude, Integer speed,
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

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ro.pub.acs.mobiway.model.Location[ idUser=" + idUser + " ]";
    }

}