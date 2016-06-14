package ro.pub.acs.mobiway.dao;

import java.util.*;

import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;
import ro.pub.acs.mobiway.model.User;

import org.springframework.beans.factory.annotation.Autowired;
public class UserDAOImpl implements UserDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public UserDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional
	public List<User> list() {
		@SuppressWarnings("unchecked")
		List<User> listUser = (List<User>) sessionFactory.getCurrentSession()
				.createCriteria(User.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();

		return listUser;
	}

	@Override
	@Transactional
	public User get(int id) {
		Criteria criteria = sessionFactory.getCurrentSession()
				.createCriteria(User.class).add(Restrictions.eq("id", id));

		Object result = criteria.uniqueResult();
		User user = null;
		if (result != null)
			user = (User) result;

		return user;
	}

	@Override
	@Transactional
	public User get(String email) {
		Criteria criteria = sessionFactory.getCurrentSession()
				.createCriteria(User.class)
				.add(Restrictions.eq("username", email));

		Object result = criteria.uniqueResult();
		User user = null;
		if (result != null)
			user = (User) result;

		return user;
	}

	@Override
	@Transactional
	public User get(String email, String password) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				User.class);
		criteria = criteria.add(Restrictions.eq("username", email));
		criteria = criteria.add(Restrictions.eq("password", password));

		Object result = criteria.uniqueResult();
		User user = null;
		if (result != null)
			user = (User) result;

		return user;
	}

	@Override
	@Transactional
	public User get(String token, int id) {
		Criteria criteria = sessionFactory.getCurrentSession()
				.createCriteria(User.class)
				.add(Restrictions.eq("auth_token", token));

		Object result = criteria.uniqueResult();
		User user = null;
		if (result != null)
			user = (User) result;

		return user;
	}

	@Override
	@Transactional
	public int add(User user) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(user);

		return user.getId();
	}

	@Override
	@Transactional
	public int update(User user) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(user);

		return user.getId();
	}

	@Override
	@Transactional
	public User loginUser(String username, String password) {
		Criteria criteria = sessionFactory.getCurrentSession()
				.createCriteria(User.class)
				.add(Restrictions.eq("username", username))
				.add(Restrictions.eq("password", password));

		Object result = criteria.uniqueResult();
		User user = null;
		if (result != null)
			user = (User) result;

		return user;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<User> getUsersWithPhone() {
		Criteria criteria = sessionFactory.getCurrentSession()
				.createCriteria(User.class)
				.add(Restrictions.isNotNull("phone"));

		List<Object> result = criteria.list();
		List<User> listUser = new ArrayList<User>();
		
		if(result != null)
			for(Object user : result){
				User friend = (User) user; 
				listUser.add(friend);
			}

		return listUser;
	}

}
