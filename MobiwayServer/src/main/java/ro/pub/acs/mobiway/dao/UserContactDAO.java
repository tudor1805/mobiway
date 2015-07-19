package ro.pub.acs.mobiway.dao;

import java.util.List;
import ro.pub.acs.mobiway.model.*;

public interface UserContactDAO {
	public UserContact get(int id);
	public int update(UserContact userContact);
	public int add(UserContact userContact);
	public List<User> getFriends(User user);
	public List<String> getFriendsEmails(User user);
	public boolean addFriend(UserContact userContact);
}
