package ro.pub.acs.traffic.dao;

import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import ro.pub.acs.traffic.model.*;

public class JourneyDataDAOImpl implements JourneyDataDAO {
	private static final long serialVersionUID = 1L;
	private SessionFactory sessionFactory;

	public JourneyDataDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional
	public JourneyData get(int id) {
		Criteria criteria = sessionFactory.getCurrentSession()
				.createCriteria(User.class).add(Restrictions.eq("id", id));

		Object result = criteria.uniqueResult();
		JourneyData journeyData = null;
		if (result != null)
			journeyData = (JourneyData) result;

		return journeyData;
	}

	@Override
	@Transactional
	public int add(JourneyData journeyData) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(journeyData);

		return journeyData.getId().intValue();
	}

	@Override
	@Transactional
	public int update(JourneyData journeyData) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(journeyData);

		return journeyData.getId().intValue();
	}
}