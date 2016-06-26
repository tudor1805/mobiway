package ro.pub.acs.mobiway.dao;

import java.util.*;
import java.io.Serializable;
import ro.pub.acs.mobiway.model.*;

public interface JourneyDataDAO extends Serializable {
	public JourneyData get(int id);

	public int update(JourneyData journey);

	public int add(JourneyData journey);

	public List<JourneyData> getByJourneyId(Journey journeyId);
}
