package com.sxt.po;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class KeyOpenLog {
	private int id;
	private String key_rfid;
	private String key_code;
	private String box_no;
	private Date open_time;
	private String box_address;
	private String lockcode;
	
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getKey_rfid() {
		return key_rfid;
	}
	public void setKey_rfid(String key_rfid) {
		this.key_rfid = key_rfid;
	}
	public String getKey_code() {
		return key_code;
	}
	public void setKey_code(String key_code) {
		this.key_code = key_code;
	}
	public String getBox_no() {
		return box_no;
	}
	public void setBox_no(String box_no) {
		this.box_no = box_no;
	}
	public Date getOpen_time() {
		return open_time;
	}
	public void setOpen_time(Date open_time) {
		this.open_time = open_time;
	}
	public String getBox_address() {
		return box_address;
	}
	public void setBox_address(String box_address) {
		this.box_address = box_address;
	}
	public String getLockcode() {
		return lockcode;
	}
	public void setLockcode(String lockcode) {
		this.lockcode = lockcode;
	}
	

}
