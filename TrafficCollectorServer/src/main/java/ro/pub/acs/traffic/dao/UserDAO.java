package ro.pub.acs.traffic.dao;

import java.util.List;

import ro.pub.acs.traffic.model.User;

public interface UserDAO {
	public List<User> list();
	public User getUser(long id);
	public User getUser(String email);
	public User getUser(String token, long id);
	public User getUser(String email, String password);
	public long updateUser(User user);
	public long addUser(User user);
	public List<User> getUsersWithPhone();
}