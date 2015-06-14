package ro.pub.acs.traffic.dao;

import java.util.List;
import ro.pub.acs.traffic.model.History;

public interface HistoryDAO {
	public List<History> list();
	public History getHistory(long id);
}
