package com.sxt.po;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class AlarmOrderDetails {
	private int id;
	private WorkOrder order;
	private int alarm_event_id;
	private BoxInfo boxinfo;
    private int alarm_type_id;
    private String alarm_type;
    private Date create_time;
    private  int done_type;
    private  Date done_time;
   
    
    @Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
    

	@ManyToOne
	@JoinColumn(name="order_id")
	public WorkOrder getOrder() {
		return order;
	}
	public void setOrder(WorkOrder order) {
		this.order = order;
	}
	
	
	
	@ManyToOne
	@JoinColumn(name="box_id")
	public BoxInfo getBoxinfo() {
		return boxinfo;
	}
	public void setBoxinfo(BoxInfo boxinfo) {
		this.boxinfo = boxinfo;
	}
	public int getAlarm_type_id() {
		return alarm_type_id;
	}
	public void setAlarm_type_id(int alarm_type_id) {
		this.alarm_type_id = alarm_type_id;
	}
	public String getAlarm_type() {
		return alarm_type;
	}
	public void setAlarm_type(String alarm_type) {
		this.alarm_type = alarm_type;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public int getDone_type() {
		return done_type;
	}
	public void setDone_type(int done_type) {
		this.done_type = done_type;
	}
	public Date getDone_time() {
		return done_time;
	}
	public void setDone_time(Date done_time) {
		this.done_time = done_time;
	}
	public int getAlarm_event_id() {
		return alarm_event_id;
	}
	public void setAlarm_event_id(int alarm_event_id) {
		this.alarm_event_id = alarm_event_id;
	}

    
}

