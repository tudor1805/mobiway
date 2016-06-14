package ro.pub.acs.mobiway.dao;

import java.util.List;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;
import ro.pub.acs.mobiway.model.*;

public class TrafficEventDAOImpl implements TrafficEventDAO {
	private SessionFactory sessionFactory;

	public TrafficEventDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional
	public TrafficEvent get(String eventName) {
		 Criteria criteria = sessionFactory.getCurrentSession()
				 .createCriteria(TrafficEvent.class)
				 .add(Restrictions.eq("name", eventName));

		 Object result = criteria.uniqueResult();
		 TrafficEvent event = null;
		 if (result != null)
			 event = (TrafficEvent) result;

		 return event;
	 }


	@Override
	@Transactional
	public TrafficEvent get(int id) {
		 Criteria criteria = sessionFactory.getCurrentSession()
				 .createCriteria(TrafficEvent.class)
				 .add(Restrictions.eq("id", id));

		 Object result = criteria.uniqueResult();
		 TrafficEvent event = null;
		 if (result != null)
			 event = (TrafficEvent) result;

		 return event;
	 }

	@Override
	@Transactional
	public int update(TrafficEvent event) {
		Session session = sessionFactory.getCurrentSession();
		session.update(event);

		return event.getId();
	}

	@Override
	@Transactional
	public int add(TrafficEvent event) {
		Session session = sessionFactory.getCurrentSession();
		session.save(event);

		return event.getId();
	}
}
