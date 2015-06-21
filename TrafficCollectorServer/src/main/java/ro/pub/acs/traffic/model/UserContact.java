package ro.pub.acs.traffic.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "user_contact")
public class UserContact implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Integer id;

	@JoinColumn(name = "id_friend_user", referencedColumnName = "id")
	@ManyToOne(optional = false)
	private User idFriendUser;

	@JoinColumn(name = "id_user", referencedColumnName = "id")
	@ManyToOne(optional = false)
	private User idUser;

	public UserContact() {
	}

	public UserContact(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getIdFriendUser() {
		return idFriendUser;
	}

	public void setIdFriendUser(User idFriendUser) {
		this.idFriendUser = idFriendUser;
	}

	public User getIdUser() {
		return idUser;
	}

	public void setIdUser(User idUser) {
		this.idUser = idUser;
	}

	@Override
	public String toString() {
		return "ro.pub.acs.traffic.model.UserContact[ id=" + id + " ]";
	}

}
