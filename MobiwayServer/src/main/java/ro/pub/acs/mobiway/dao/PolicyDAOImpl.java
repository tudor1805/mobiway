package ro.pub.acs.mobiway.dao;

import java.util.List;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;
import ro.pub.acs.mobiway.model.*;

public class PolicyDAOImpl implements PolicyDAO {
	private SessionFactory sessionFactory;

	public PolicyDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional
	public List<Policy> list(String appId) {
		@SuppressWarnings("unchecked")
		List<Policy> listPolicy = (List<Policy>) sessionFactory.getCurrentSession()
				.createCriteria(Policy.class)
				.add(Restrictions.eq("appId", appId))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();

		return listPolicy;
	}

	@Override
	@Transactional
	public List<Policy> list(String appId, User user) {
		@SuppressWarnings("unchecked")
		List<Policy> listPolicy = (List<Policy>) sessionFactory.getCurrentSession()
				.createCriteria(Policy.class)
				.add(Restrictions.eq("appId", appId))
				.add(Restrictions.eq("userId", user))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();

		return listPolicy;
	}


	@Override
	@Transactional
        public Policy get(int id) {
                 Criteria criteria = sessionFactory.getCurrentSession()
                                 .createCriteria(Policy.class)
				 .add(Restrictions.eq("id", id));

                 Object result = criteria.uniqueResult();
                 Policy policy = null;
                 if (result != null)
                         policy = (Policy) result;

                 return policy;
         }

	@Override
	@Transactional
	public Policy get(String policyName, String appId) {
                Criteria criteria = sessionFactory.getCurrentSession()
                                 .createCriteria(Policy.class)
				 .add(Restrictions.eq("policyName", policyName))
				 .add(Restrictions.eq("appId", appId));

                 Object result = criteria.uniqueResult();
                 Policy policy = null;
                 if (result != null)
                         policy = (Policy) result;

                 return policy;
	}

	@Override
	@Transactional
	public Integer updatePolicy(Policy policy) {
		Session session = sessionFactory.getCurrentSession();
		session.update(policy);

		return policy.getId();
	}

	@Override
	@Transactional
	public Integer addPolicy(Policy policy) {
		Session session = sessionFactory.getCurrentSession();
		session.save(policy);

		return policy.getId();
	}
}
