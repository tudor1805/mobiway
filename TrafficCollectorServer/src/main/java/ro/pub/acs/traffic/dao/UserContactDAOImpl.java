package ro.pub.acs.traffic.dao;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

import ro.pub.acs.traffic.model.*;

public class UserContactDAOImpl implements UserContactDAO {
	private SessionFactory sessionFactory;

	public UserContactDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public UserContact get(int id) {
		Criteria criteria = sessionFactory.getCurrentSession()
				.createCriteria(User.class).add(Restrictions.eq("id", id));

		Object result = criteria.uniqueResult();
		UserContact userContact = null;
		if (result != null)
			userContact = (UserContact) result;

		return userContact;
	}

	@Override
	public int update(UserContact userContact) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(userContact);

		return userContact.getId().intValue();
	}

	@Override
	public int add(UserContact userContact) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(userContact);

		return userContact.getId().intValue();
	}
	
}