package ro.pub.acs.mobiway.dao;

import java.util.*;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;
import ro.pub.acs.mobiway.model.*;

public class UserPolicyDAOImpl implements UserPolicyDAO {
	private SessionFactory sessionFactory;

	public UserPolicyDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional
	public List<UserPolicy> getUserAcceptedPoliciesByApp(User user, String appId) {
		Session session = sessionFactory.getCurrentSession();

		Criteria criteria = sessionFactory.getCurrentSession()
			.createCriteria(UserPolicy.class)
			.add(Restrictions.eq("idUser", user))
			.add(Restrictions.eq("appId", appId));

		List <UserPolicy> acceptedPolicies = new ArrayList<UserPolicy>();
                Object result = criteria.list();
		if (result != null) {
			acceptedPolicies = (List <UserPolicy>) result;
		}

		return acceptedPolicies;
	}

	@Override
	@Transactional
	public void clearPolicies(User user, String appId) {
		Session session = sessionFactory.getCurrentSession();

		String hql = "delete from UserPolicy where id_user= :uId and app_id= :appId";
		Query result = session.createQuery(hql);
		result.setParameter("uId", user);
		result.setParameter("appId", appId);

		result.executeUpdate();
	}


	@Override
	@Transactional
	public Integer update(UserPolicy userPolicy) {
		Session session = sessionFactory.getCurrentSession();
		session.update(userPolicy);

		return userPolicy.getId();
	}

	@Override
	@Transactional
	public Integer add(UserPolicy userPolicy) {
		Session session = sessionFactory.getCurrentSession();
		session.save(userPolicy);

		return userPolicy.getId();
	}
}
