package ro.pub.acs.traffic.dao;

import ro.pub.acs.traffic.model.Location;

public interface LocationDAO {
	public Location get(int user_id);

	public long add(Location location);

	public long update(Location location);
}