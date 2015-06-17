package ro.pub.acs.traffic.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
	public List<Location> list() {
		@SuppressWarnings("unchecked")
		List<Location> listLocation = (List<Location>) sessionFactory.getCurrentSession()
				.createCriteria(Location.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();

		return listLocation;
	}

	@Override
	@Transactional
	public Location getLocation(long id_user) {
		Criteria criteria = sessionFactory.getCurrentSession()
				.createCriteria(Location.class)
				.add(Restrictions.eq("id_user", id_user));
		
		Object result = criteria.uniqueResult();
		Location location = null;
		if(result != null)
			location = (Location) result;
		
		return location;
	}
	
	@Override
	@Transactional
	public long updateLocation(Location location) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(location);
		
		return location.getId_user();
	}
	
	@Override
	@Transactional
	public long addLocation(Location location) {
		Session session = sessionFactory.getCurrentSession();
		session.save(location);
		
		return location.getId_user();
	}

}