package ro.pub.acs.trafficcollector.rest.model;

import java.util.Date;

public class Location {

    private Integer idUser;
    private float latitude;
    private float longitude;
    private int speed;
    private Date timestamp;

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

    @Override
    public String toString() {
        return "ro.pub.acs.traffic.model.Location[ idUser=" + idUser + " ]";
    }

}