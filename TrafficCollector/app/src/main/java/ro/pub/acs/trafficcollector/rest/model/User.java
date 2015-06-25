package ro.pub.acs.trafficcollector.rest.model;

public class User {

	private Integer id;
	private String username;
	private String password;
	private String firstname;
	private String lastname;
	private String phone;
	private String facebook_token;
	private Integer facebookExpiresIn;
	private String auth_token;
	private Integer authExpiresIn;
	private String uuid;


	public User() {
	}

	public User(Integer id) {
		this.id = id;
	}

	public User(Integer idUser, String username, String password,
				String firstname, String lastname, String phone,
				String facebook_token, String auth_token, String uuid) {

		this.id = idUser;
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.phone = phone;
		this.facebook_token = facebook_token;
		this.auth_token = auth_token;
		this.uuid = uuid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getFacebookExpiresIn() {
		return facebookExpiresIn;
	}

	public void setFacebookExpiresIn(Integer facebook_expires_in) {
		this.facebookExpiresIn = facebook_expires_in;
	}

	public String getAuth_token() {
		return auth_token;
	}

	public void setAuth_token(String auth_token) {
		this.auth_token = auth_token;
	}

	public Integer getAuthExpiresIn() {
		return authExpiresIn;
	}

	public void setAuthExpiresIn(Integer auth_expires_in) {
		this.authExpiresIn = auth_expires_in;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String toString(){
		return "userName: " + this.getUsername() + "\n" +
				"firstName: " + this.getFirstname() + "\n" +
				"lastName: " + this.getLastname() + "\n" +
				"authToken: " + this.getAuth_token() + "\n" +
				"expiresIn: " + this.getAuthExpiresIn() + "\n" +
				"phone: " + this.getPhone() + "\n";
	}
}
