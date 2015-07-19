package ro.pub.acs.mobiway.dao;

import java.util.List;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;
import ro.pub.acs.mobiway.model.*;

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
	public Location getLocation(User user) {
		Criteria criteria = sessionFactory.getCurrentSession()
				.createCriteria(Location.class)
				.add(Restrictions.eq("idUser", user.getId()));
		
		Object result = criteria.uniqueResult();
		Location location = null;
		if(result != null)
			location = (Location) result;
		
		return location;
	}
	
	@Override
	@Transactional
	public Integer updateLocation(Location location) {
		Session session = sessionFactory.getCurrentSession();
		session.update(location);
		
		return location.getIdUser();
	}
	
	@Override
	@Transactional
	public Integer addLocation(Location location) {
		Session session = sessionFactory.getCurrentSession();
		session.save(location);
		
		return location.getIdUser();
	}

}
