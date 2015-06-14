package ro.pub.acs.traffic.model;

import java.util.Date;

public class Stats {
	private long id;
	private String id_user;
	private String server_info;
	private String page;
	private long type;
	private Date login_date;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getId_user() {
		return id_user;
	}
	public void setId_user(String id_user) {
		this.id_user = id_user;
	}
	public String getServer_info() {
		return server_info;
	}
	public void setServer_info(String server_info) {
		this.server_info = server_info;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public long getType() {
		return type;
	}
	public void setType(long type) {
		this.type = type;
	}
	public Date getLogin_date() {
		return login_date;
	}
	public void setLogin_date(Date login_date) {
		this.login_date = login_date;
	}
}
