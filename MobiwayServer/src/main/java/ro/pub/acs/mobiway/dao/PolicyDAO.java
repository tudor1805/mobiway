package ro.pub.acs.mobiway.dao;

import java.util.List;
import ro.pub.acs.mobiway.model.*;

public interface PolicyDAO {
	public List<Policy> list(String appId);
	public List<Policy> list(String appId, User user);
	public Policy get(String policyName, String appId);
	public Policy get(int id);
	public Integer updatePolicy(Policy policy);
	public Integer addPolicy(Policy policy);
}
