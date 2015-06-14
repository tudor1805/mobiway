package ro.pub.acs.traffic.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import ro.pub.acs.traffic.model.User;

public class UserDAOImpl implements UserDAO {
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
	public User getUser(long id) {
		Criteria criteria = sessionFactory.getCurrentSession()
				.createCriteria(User.class)
				.add(Restrictions.eq("id", id));
		
		Object result = criteria.uniqueResult();
		User user = null;
		if(result != null)
			user = (User) result;
		
		return user;
	}
	
	@Override
	@Transactional
	public User getUser(String email) {
		Criteria criteria = sessionFactory.getCurrentSession()
				.createCriteria(User.class)
				.add(Restrictions.eq("username", email));
		
		Object result = criteria.uniqueResult();
		User user = null;
		if(result != null)
			user = (User) result;
		
		return user;
	}
	
	@Override
	@Transactional
	public User getUser(String email, String password) {
		Criteria criteria = sessionFactory.getCurrentSession()
				.createCriteria(User.class);
		criteria = criteria.add(Restrictions.eq("username", email));
		criteria = criteria.add(Restrictions.eq("password", password));
		
		Object result = criteria.uniqueResult();
		User user = null;
		if(result != null)
			user = (User) result;
		
		return user;
	}
	
	@Override
	@Transactional
	public User getUser(String token, long id){
		Criteria criteria = sessionFactory.getCurrentSession()
				.createCriteria(User.class)
				.add(Restrictions.eq("auth_token", token));
		
		Object result = criteria.uniqueResult();
		User user = null;
		if(result != null)
			user = (User) result;
		
		return user;
	}
	
	@Override
	@Transactional
	public long addUser(User user) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(user);
		
		return user.getId_user();
	}
	
	@Override
	@Transactional
	public long updateUser(User user) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(user);
		
		return user.getId_user();
	}

}