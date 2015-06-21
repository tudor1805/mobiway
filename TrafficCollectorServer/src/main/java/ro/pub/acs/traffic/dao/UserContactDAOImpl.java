package ro.pub.acs.traffic.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import ro.pub.acs.traffic.model.User;
import ro.pub.acs.traffic.model.UserContact;

public class UserContactDAOImpl implements UserContactDAO {
	private SessionFactory sessionFactory;

	public UserContactDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<User> getFriends(long id_user) {
		Criteria criteria = sessionFactory.getCurrentSession()
				.createCriteria(UserContact.class)
				.add(Restrictions.eq("id_user", id_user));

		List<Object> result = criteria.list();
		List<User> listUser = new ArrayList<User>();

		for (Object user : result) {
			UserContact userContact = (UserContact) user;
			User friend = new UserDAOImpl(sessionFactory).getUser(userContact
					.getId_friend_user());
			listUser.add(friend);
		}

		return listUser;
	}

	@Override
	@Transactional
	public boolean addFriend(UserContact userContact) {
		Session session = sessionFactory.getCurrentSession();
		session.save(userContact);

		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<String> getFriendsEmails(long id_user) {
		Criteria criteria = sessionFactory.getCurrentSession()
				.createCriteria(UserContact.class)
				.add(Restrictions.eq("id_user", id_user));

		List<Object> result = criteria.list();
		List<String> listUser = new ArrayList<String>();

		for (Object user : result) {
			UserContact userContact = (UserContact) user;
			User friend = new UserDAOImpl(sessionFactory).getUser(userContact
					.getId_friend_user());
			listUser.add(friend.getUsername());
		}

		return listUser;
	}
}