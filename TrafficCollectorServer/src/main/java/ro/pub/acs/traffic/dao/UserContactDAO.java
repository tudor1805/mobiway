package ro.pub.acs.traffic.dao;

import java.util.List;

import ro.pub.acs.traffic.model.User;
import ro.pub.acs.traffic.model.UserContact;

public interface UserContactDAO {
	public List<User> getFriends(long id_user);
	public boolean addFriend(UserContact userContact);
}