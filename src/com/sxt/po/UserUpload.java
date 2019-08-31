package com.sxt.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


public class UserUpload {
	
	private int id;
	private String user_name;
	private String pwd_old;
	private String pwd_new;
	
	
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getPwd_old() {
		return pwd_old;
	}
	public void setPwd_old(String pwd_old) {
		this.pwd_old = pwd_old;
	}
	public String getPwd_new() {
		return pwd_new;
	}
	public void setPwd_new(String pwd_new) {
		this.pwd_new = pwd_new;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	

	
	
}
