package ro.pub.acs.traffic.dao;

import ro.pub.acs.traffic.model.*;

public interface UserContactDAO {
	public UserContact get(int id);

	public int update(UserContact journey);

	public int add(UserContact journey);
}