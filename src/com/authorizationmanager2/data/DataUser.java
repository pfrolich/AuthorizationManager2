package com.authorizationmanager2.data;

public class DataUser {
	private int id;
	private String fName;
	private String sName;
	private String email;
	private String sDate;
	private String act;
	public DataUser(int id, String fName, String sName, String email, String sDate, String act) {
		super();
		this.id = id;
		this.fName = fName;
		this.sName = sName;
		this.email = email;
		this.sDate = sDate;
		this.act = act;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getsName() {
		return sName;
	}
	public void setsName(String sName) {
		this.sName = sName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getsDate() {
		return sDate;
	}
	public void setsDate(String sDate) {
		this.sDate = sDate;
	}
	public String getAct() {
		return act;
	}
	public void setAct(String act) {
		this.act = act;
	}
}
