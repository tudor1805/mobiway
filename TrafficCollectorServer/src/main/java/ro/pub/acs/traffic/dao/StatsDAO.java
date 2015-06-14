package ro.pub.acs.traffic.dao;

import java.util.List;
import ro.pub.acs.traffic.model.Stats;

public interface StatsDAO {
	public List<Stats> list();
	public Stats getStats(long id);
}