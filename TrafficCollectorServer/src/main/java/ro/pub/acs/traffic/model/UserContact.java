package ro.pub.acs.traffic.model;

public class UserContact {
	private long id;
	private long id_user;
	private long id_friend_user;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getId_user() {
		return id_user;
	}
	public void setId_user(long id_user) {
		this.id_user = id_user;
	}
	public long getId_friend_user() {
		return id_friend_user;
	}
	public void setId_friend_user(long id_friend_user) {
		this.id_friend_user = id_friend_user;
	}
}