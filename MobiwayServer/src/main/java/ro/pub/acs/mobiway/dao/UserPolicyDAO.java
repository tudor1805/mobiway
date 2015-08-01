package ro.pub.acs.mobiway.dao;

import java.util.List;
import ro.pub.acs.mobiway.model.*;

public interface UserPolicyDAO {
	public List<UserPolicy> getUserAcceptedPoliciesByApp(User user, String appId);
	public void clearPolicies(User user, String appId);
	public Integer update(UserPolicy policy);
	public Integer add(UserPolicy policy);
}
