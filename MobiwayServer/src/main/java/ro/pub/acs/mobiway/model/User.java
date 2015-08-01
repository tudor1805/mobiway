package ro.pub.acs.mobiway.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "user")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Integer id;

	@Basic(optional = false)
	@NotNull
	@Lob
	@Size(min = 1, max = 65535)
	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@Basic(optional = false)
	@Lob
	@Size(min = 1, max = 65535)
	@Column(name = "firstname")
	private String firstname;

	@Basic(optional = false)
	@Lob
	@Size(min = 1, max = 65535)
	@Column(name = "lastname")
	private String lastname;

	@Column(name = "phone")
	private String phone;

	@Column(name = "facebook_token")
	private String facebook_token;
	
	@Column(name = "facebook_expires_in")
	private Integer facebookExpiresIn;

	@Basic(optional = false)
	@NotNull
	@Lob
	@Size(min = 1, max = 512)
	@Column(name = "auth_token")
	private String auth_token;

	@Column(name = "auth_expires_in")
	private Integer authExpiresIn;

	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 512)
	@Column(name = "uuid")
	private String uuid;

	/*@OneToMany(cascade = CascadeType.ALL, mappedBy = "idFriendUser")
	private Collection<UserContact> userContactCollection;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "idUser")
	private Collection<UserContact> userContactCollection1;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
	private Location location;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "id")
	private Collection<Journey> journeyCollection;*/

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

	/*//@XmlTransient
	public Collection<UserContact> getUserContactCollection() {
		return userContactCollection;
	}

	public void setUserContactCollection(
			Collection<UserContact> userContactCollection) {
		this.userContactCollection = userContactCollection;
	}

	//@XmlTransient
	public Collection<UserContact> getUserContactCollection1() {
		return userContactCollection1;
	}

	public void setUserContactCollection1(
			Collection<UserContact> userContactCollection1) {
		this.userContactCollection1 = userContactCollection1;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	@XmlTransient
	public Collection<Journey> getJourneyCollection() {
		return journeyCollection;
	}

	public void setJourneyCollection(Collection<Journey> journeyCollection) {
		this.journeyCollection = journeyCollection;
	}*/

	@Override
	public String toString() {
		return "ro.pub.acs.mobiway.model.User[ idUser=" + id + " ]";
	}

}
