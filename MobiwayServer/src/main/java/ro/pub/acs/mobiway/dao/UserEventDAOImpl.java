package ro.pub.acs.mobiway.dao;

import java.util.List;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;
import ro.pub.acs.mobiway.model.*;

public class UserEventDAOImpl implements UserEventDAO {
	private SessionFactory sessionFactory;

	public UserEventDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional
	public UserEvent get(int id) {
		 Criteria criteria = sessionFactory.getCurrentSession()
				 .createCriteria(UserEvent.class)
				 .add(Restrictions.eq("id", id));

		 Object result = criteria.uniqueResult();
		 UserEvent event = null;
		 if (result != null)
			 event = (UserEvent) result;

		 return event;
	 }

	@Override
	@Transactional
	public UserEvent get(String osmId) {
		 Criteria criteria = sessionFactory.getCurrentSession()
				 .createCriteria(UserEvent.class)
				 .add(Restrictions.eq("osm_way_id", osmId));

		 Object result = criteria.uniqueResult();
		 UserEvent event = null;
		 if (result != null)
			 event = (UserEvent) result;

		 return event;
	}

	@Override
	@Transactional
	public int update(UserEvent event) {
		Session session = sessionFactory.getCurrentSession();
		session.update(event);

		return event.getId();
	}

	@Override
	@Transactional
	public int add(UserEvent event) {
		Session session = sessionFactory.getCurrentSession();
		session.save(event);

		return event.getId();
	}
}
