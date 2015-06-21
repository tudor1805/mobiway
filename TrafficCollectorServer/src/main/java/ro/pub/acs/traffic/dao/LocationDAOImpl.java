package ro.pub.acs.traffic.dao;

import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import ro.pub.acs.traffic.model.Location;

public class LocationDAOImpl implements LocationDAO {
	private SessionFactory sessionFactory;

	public LocationDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional
	public Location get(int id_user) {
		Criteria criteria = sessionFactory.getCurrentSession()
				.createCriteria(Location.class)
				.add(Restrictions.eq("id_user", id_user));

		Object result = criteria.uniqueResult();
		Location location = null;
		if (result != null)
			location = (Location) result;

		return location;
	}

	@Override
	@Transactional
	public long add(Location location) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(location);

		return location.getIdUser();
	}

	@Override
	@Transactional
	public long update(Location location) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(location);

		return location.getIdUser();
	}

}