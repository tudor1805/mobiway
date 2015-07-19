package ro.pub.acs.mobiway.dao;

import java.util.List;

import org.hibernate.*;
import org.hibernate.criterion.*;
import org.springframework.transaction.annotation.Transactional;

import ro.pub.acs.mobiway.model.*;

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
				.add(Restrictions.eq("idUser", user.getId()));

		Object result = criteria.uniqueResult();
		Journey journey = null;
		if (result != null)
			journey = (Journey) result;

		return journey;
	}

	@Override
	@Transactional
	public Journey getCurrentJourney(User user) {
		Journey journey = null;

		DetachedCriteria maxId = DetachedCriteria.forClass(Journey.class)
				.setProjection(Projections.max("id") );

		try{
			Criteria criteria = sessionFactory.getCurrentSession()
				.createCriteria(Journey.class);

			criteria.add(Property.forName("id").eq(maxId));
			criteria.add(Restrictions.eq("idUser", user));

			journey = (Journey) criteria.list().get(0);
		} catch(Exception ex) {
			// ex.printStackTrace();
		}

		return journey;
	}

	@Override
	@Transactional
	public Journey get(int id) {
		Criteria criteria = sessionFactory.getCurrentSession()
				.createCriteria(Journey.class).add(Restrictions.eq("id", id));

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
