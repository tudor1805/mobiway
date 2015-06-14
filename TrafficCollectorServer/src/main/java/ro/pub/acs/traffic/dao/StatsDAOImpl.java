package ro.pub.acs.traffic.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import ro.pub.acs.traffic.model.Stats;

public class StatsDAOImpl implements StatsDAO {
	private SessionFactory sessionFactory;

	public StatsDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional
	public List<Stats> list() {
		@SuppressWarnings("unchecked")
		List<Stats> listStats = (List<Stats>) sessionFactory.getCurrentSession()
				.createCriteria(Stats.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();

		return listStats;
	}

	@Override
	@Transactional
	public Stats getStats(long id) {
		Criteria criteria = sessionFactory.getCurrentSession()
				.createCriteria(Stats.class)
				.add(Restrictions.eq("id", id));
		
		Object result = criteria.uniqueResult();
		Stats stats = null;
		if(result != null)
			stats = (Stats) result;
		
		return stats;
	}

}