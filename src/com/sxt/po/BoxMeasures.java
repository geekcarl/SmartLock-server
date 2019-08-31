package com.sxt.po;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class BoxMeasures {

	private int id;
	private BoxInfo boxInfo;
	private String doors;
	private double voltage;
	private double pose_x;
	private double pose_y;
	private double pose_z;
	private double temperature;
	private int humidity;
	private Integer dbm;
	private Date update_time;
	
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="box_id")
	public BoxInfo getBoxInfo() {
		return boxInfo;
	}
	
	public void setBoxInfo(BoxInfo boxInfo) {
		this.boxInfo = boxInfo;
	}
	public String getDoors() {
		return doors;
	}
	public void setDoors(String doors) {
		this.doors = doors;
	}
	public double getVoltage() {
		return voltage;
	}
	public void setVoltage(double voltage) {
		this.voltage = voltage;
	}
	public double getPose_x() {
		return pose_x;
	}
	public void setPose_x(double pose_x) {
		this.pose_x = pose_x;
	}
	public double getPose_y() {
		return pose_y;
	}
	public void setPose_y(double pose_y) {
		this.pose_y = pose_y;
	}
	public double getPose_z() {
		return pose_z;
	}
	public void setPose_z(double pose_z) {
		this.pose_z = pose_z;
	}
	public double getTemperature() {
		return temperature;
	}
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	public int getHumidity() {
		return humidity;
	}
	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}
	public Integer getDbm() {
		return dbm;
	}
	public void setDbm(Integer dbm) {
		this.dbm = dbm;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
}
