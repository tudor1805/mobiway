package ro.pub.acs.traffic.dao;

import java.util.List;
import ro.pub.acs.traffic.model.Location;

public interface LocationDAO {
	public List<Location> list();
	public Location getLocation(long user_id);
	public long updateLocation(Location location);
	public long addLocation(Location location);
}