package com.sxt.po;

import javax.persistence.Entity;
import javax.persistence.Id;



@Entity
public class Cabinet {
	
	private Integer device_id;
	private Integer initializing_angles_switch;
	private Integer initializing_angles_now;
	
    @Id
	public Integer getDevice_id() {
		return device_id;
	}
	public void setDevice_id(Integer device_id) {
		this.device_id = device_id;
	}
	public Integer getInitializing_angles_switch() {
		return initializing_angles_switch;
	}
	public void setInitializing_angles_switch(Integer initializing_angles_switch) {
		this.initializing_angles_switch = initializing_angles_switch;
	}
	public Integer getInitializing_angles_now() {
		return initializing_angles_now;
	}
	public void setInitializing_angles_now(Integer initializing_angles_now) {
		this.initializing_angles_now = initializing_angles_now;
	}
	
	
	

}
