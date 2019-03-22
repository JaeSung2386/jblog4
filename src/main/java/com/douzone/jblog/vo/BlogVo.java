package com.douzone.jblog.vo;

public class BlogVo {
	private long user_no;
	private String title;
	private String logo;
	
	public long getUser_no() {
		return user_no;
	}
	public void setUser_no(long user_no) {
		this.user_no = user_no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	@Override
	public String toString() {
		return "BlogVo [user_no=" + user_no + ", title=" + title + ", logo=" + logo + "]";
	}
}
