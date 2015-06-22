package ro.pub.acs.traffic.dao;

import java.util.List;

import ro.pub.acs.traffic.model.Location;
import ro.pub.acs.traffic.model.User;

public interface LocationDAO {
	public List<Location> list();
	public Location getLocation(User user);
	public Integer updateLocation(Location location);
	public Integer addLocation(Location location);
}