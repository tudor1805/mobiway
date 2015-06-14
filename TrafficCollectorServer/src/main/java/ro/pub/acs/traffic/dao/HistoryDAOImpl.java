package ro.pub.acs.traffic.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import ro.pub.acs.traffic.model.History;

public class HistoryDAOImpl implements HistoryDAO {
	private SessionFactory sessionFactory;

	public HistoryDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional
	public List<History> list() {
		@SuppressWarnings("unchecked")
		List<History> listHistory = (List<History>) sessionFactory.getCurrentSession()
				.createCriteria(History.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();

		return listHistory;
	}

	@Override
	@Transactional
	public History getHistory(long id) {
		Criteria criteria = sessionFactory.getCurrentSession()
				.createCriteria(History.class)
				.add(Restrictions.eq("id", id));
		
		Object result = criteria.uniqueResult();
		History history = null;
		if(result != null)
			history = (History) result;
		
		return history;
	}

}
