package com.authorizationmanager2.data;

public class DataUserId {
	
	private int id;
	private String  user;
	private String dPwd;
	private String sDate;
	private String mDate;
	private Boolean val;
	private Boolean block;
	private String  lvl;
	public DataUserId(int id, String user, String dPwd, String sDate, String mDate, Boolean val, Boolean block,
			String lvl) {
		super();
		this.id = id;
		this.user = user;
		this.dPwd = dPwd;
		this.sDate = sDate;
		this.mDate = mDate;
		this.val = val;
		this.block = block;
		this.lvl = lvl;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getdPwd() {
		return dPwd;
	}
	public void setdPwd(String dPwd) {
		this.dPwd = dPwd;
	}
	public String getsDate() {
		return sDate;
	}
	public void setsDate(String sDate) {
		this.sDate = sDate;
	}
	public String getmDate() {
		return mDate;
	}
	public void setmDate(String mDate) {
		this.mDate = mDate;
	}
	public Boolean getVal() {
		return val;
	}
	public void setVal(Boolean val) {
		this.val = val;
	}
	public Boolean getBlock() {
		return block;
	}
	public void setBlock(Boolean block) {
		this.block = block;
	}
	public String getLvl() {
		return lvl;
	}
	public void setLvl(String lvl) {
		this.lvl = lvl;
	}
	
}
