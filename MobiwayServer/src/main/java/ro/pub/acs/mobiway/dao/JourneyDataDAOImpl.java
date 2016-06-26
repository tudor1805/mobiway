package ro.pub.acs.mobiway.dao;

import java.util.*;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import ro.pub.acs.mobiway.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@EnableTransactionManagement
public class JourneyDataDAOImpl implements JourneyDataDAO {
	private static final long serialVersionUID = 1L;

	@Autowired
	private SessionFactory sessionFactory;

	public JourneyDataDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional
	public List<JourneyData> getByJourneyId(Journey journeyId) {
		Criteria criteria = sessionFactory.getCurrentSession()
				.createCriteria(JourneyData.class).add(Restrictions.eq("journeyId", journeyId));

		// @SuppressWarnings("unchecked")
		return criteria.list();
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
