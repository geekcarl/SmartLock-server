package com.sxt.po;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


public class AlarmEventDetail {
	private int id;
	private String  box_no;
	private String  depname;
	private String alarm_type;
	private Long controller_id;
	private String address;
	private String alarm_time;
	private String alarm_keys;
	private String alarm_operators;
	private String alarm_rfids;

	private String remarks;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getAlarm_type() {
		return alarm_type;
	}
	public void setAlarm_type(String alarm_type) {
		this.alarm_type = alarm_type;
	}
	public Long getController_id() {
		return controller_id;
	}
	public void setController_id(Long controller_id) {
		this.controller_id = controller_id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getAlarm_keys() {
		return alarm_keys;
	}
	public void setAlarm_keys(String alarm_keys) {
		this.alarm_keys = alarm_keys;
	}
	public String getAlarm_operators() {
		return alarm_operators;
	}
	public void setAlarm_operators(String alarm_operators) {
		this.alarm_operators = alarm_operators;
	}
	public String getAlarm_rfids() {
		return alarm_rfids;
	}
	public void setAlarm_rfids(String alarm_rfids) {
		this.alarm_rfids = alarm_rfids;
	}
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getBox_no() {
		return box_no;
	}
	public void setBox_no(String box_no) {
		this.box_no = box_no;
	}
	public String getDepname() {
		return depname;
	}
	public void setDepname(String depname) {
		this.depname = depname;
	}
	public String getAlarm_time() {
		return alarm_time;
	}
	public void setAlarm_time(String alarm_time) {
		this.alarm_time = alarm_time;
	}
}
