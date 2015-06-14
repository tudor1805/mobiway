package ro.pub.acs.traffic.model;

public class User {
	private long id_user;
	private String username;
	private String password;
	private String firstname;
	private String lastname;
	private String phone;
	private String facebook_token;
	private long facebook_expires_in;
	private String auth_token;
	private long auth_expires_in;
	private String uuid;
	
	public long getId_user() {
		return id_user;
	}
	public void setId_user(long id) {
		this.id_user = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFacebook_token() {
		return facebook_token;
	}
	public void setFacebook_token(String facebook_token) {
		this.facebook_token = facebook_token;
	}
	public long getFacebook_expires_in() {
		return facebook_expires_in;
	}
	public void setFacebook_expires_in(long facebook_expires_in) {
		this.facebook_expires_in = facebook_expires_in;
	}
	public String getAuth_token() {
		return auth_token;
	}
	public void setAuth_token(String auth_token) {
		this.auth_token = auth_token;
	}
	public long getAuth_expires_in() {
		return auth_expires_in;
	}
	public void setAuth_expires_in(long auth_expires_in) {
		this.auth_expires_in = auth_expires_in;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
}
