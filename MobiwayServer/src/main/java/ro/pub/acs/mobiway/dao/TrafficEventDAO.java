package ro.pub.acs.mobiway.dao;

import java.io.Serializable;
import java.util.*;

import ro.pub.acs.mobiway.model.*;

public interface TrafficEventDAO extends Serializable {

	public int update(TrafficEvent event);

	public TrafficEvent get(String eventName);
	
	public TrafficEvent get(int id);

	public int add(TrafficEvent event);
}
