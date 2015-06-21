package ro.pub.acs.traffic.dao;

import java.util.List;

import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import ro.pub.acs.traffic.model.*;

public class JourneyDAOImpl implements JourneyDAO {
	private static final long serialVersionUID = 1L;
	
	private SessionFactory sessionFactory;

	public JourneyDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional
	public Journey get(User user) {
		Criteria criteria = sessionFactory.getCurrentSession()
				.createCriteria(Journey.class)
				.add(Restrictions.eq("id_user", user.getId()));

		Object result = criteria.uniqueResult();
		Journey journey = null;
		if (result != null)
			journey = (Journey) result;

		return journey;
	}

	@Override
	@Transactional
	public Journey get(int id) {
		Criteria criteria = sessionFactory.getCurrentSession()
				.createCriteria(User.class).add(Restrictions.eq("id", id));

		Object result = criteria.uniqueResult();
		Journey journey = null;
		if (result != null)
			journey = (Journey) result;

		return journey;
	}

	@Override
	@Transactional
	public List<Journey> getByUserId(User user) {
		Criteria criteria = sessionFactory.getCurrentSession()
				.createCriteria(Journey.class)
				.add(Restrictions.eq("idUser", user));

		@SuppressWarnings("unchecked")
		List<Journey> journeys = criteria.list();

		return journeys;
	}

	@Override
	@Transactional
	public int add(Journey journey) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(journey);

		return journey.getId().intValue();
	}

	@Override
	@Transactional
	public int update(Journey journey) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(journey);

		return journey.getId().intValue();
	}

}