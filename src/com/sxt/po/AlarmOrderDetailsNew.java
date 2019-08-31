package com.sxt.po;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


public class AlarmOrderDetailsNew {
	private int id;
	private int order_id;
	private int alarm_event_id;
	private int box_id;
	private String box_no;
	private String box_address;
    private String alarm_type;
    private String create_time;
    private String done_type;
    private String done_time;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	public int getAlarmevent_id() {
		return alarm_event_id;
	}
	public void setAlarmevent_id(int alarm_event_id) {
		this.alarm_event_id = alarm_event_id;
	}
	public int getBox_id() {
		return box_id;
	}
	public void setBox_id(int box_id) {
		this.box_id = box_id;
	}
	public String getBox_no() {
		return box_no;
	}
	public void setBox_no(String box_no) {
		this.box_no = box_no;
	}
	public String getBox_address() {
		return box_address;
	}
	public void setBox_address(String box_address) {
		this.box_address = box_address;
	}
	
	public String getAlarm_type() {
		return alarm_type;
	}
	public void setAlarm_type(String alarm_type) {
		this.alarm_type = alarm_type;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getDone_type() {
		return done_type;
	}
	public void setDone_type(String done_type) {
		this.done_type = done_type;
	}
	public String getDone_time() {
		return done_time;
	}
	public void setDone_time(String done_time) {
		this.done_time = done_time;
	}
   
    
 

    
}

