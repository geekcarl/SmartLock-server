package com.sxt.po;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class BoxPrivilege {
	private int id;
	private int box_id;
	private int user_id;
	private int set_user_id;
	private Date set_date;
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBox_id() {
		return box_id;
	}
	public void setBox_id(int box_id) {
		this.box_id = box_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getSet_user_id() {
		return set_user_id;
	}
	public void setSet_user_id(int set_user_id) {
		this.set_user_id = set_user_id;
	}
	public Date getSet_date() {
		return set_date;
	}
	public void setSet_date(Date set_date) {
		this.set_date = set_date;
	}
}
