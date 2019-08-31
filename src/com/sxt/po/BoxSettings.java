package com.sxt.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="boxsettings")
public class BoxSettings {

	private int id;
	private int box_id;
	private int flag;
	private int hb_interval;
	private double volt_threshold;
	private int angle_threshold;
	private double high_t_threshold;
	private double low_t_threshold;
	private int lowpower_period;
	private int lowpower_periodpercent;
	private int shake_peroid;
	private int shake_frequency;
	private int shake_time;
	private String remarks;
	private int set_user;
	private Date lastedittime;
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
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public int getHb_interval() {
		return hb_interval;
	}
	public void setHb_interval(int hb_interval) {
		this.hb_interval = hb_interval;
	}
	public double getVolt_threshold() {
		return volt_threshold;
	}
	public void setVolt_threshold(double volt_threshold) {
		this.volt_threshold = volt_threshold;
	}
	public int getAngle_threshold() {
		return angle_threshold;
	}
	public void setAngle_threshold(int angle_threshold) {
		this.angle_threshold = angle_threshold;
	}
	public double getHigh_t_threshold() {
		return high_t_threshold;
	}
	public void setHigh_t_threshold(double high_t_threshold) {
		this.high_t_threshold = high_t_threshold;
	}
	public double getLow_t_threshold() {
		return low_t_threshold;
	}
	public void setLow_t_threshold(double low_t_threshold) {
		this.low_t_threshold = low_t_threshold;
	}
	@Column(updatable=false)
	public int getLowpower_period() {
		return lowpower_period;
	}
	public void setLowpower_period(int lowpower_period) {
		this.lowpower_period = lowpower_period;
	}
	@Column(updatable=false)
	public int getLowpower_periodpercent() {
		return lowpower_periodpercent;
	}
	public void setLowpower_periodpercent(int lowpower_periodpercent) {
		this.lowpower_periodpercent = lowpower_periodpercent;
	}
	@Column(updatable=false)
	public int getShake_peroid() {
		return shake_peroid;
	}
	public void setShake_peroid(int shake_peroid) {
		this.shake_peroid = shake_peroid;
	}
	@Column(updatable=false)
	public int getShake_frequency() {
		return shake_frequency;
	}
	public void setShake_frequency(int shake_frequency) {
		this.shake_frequency = shake_frequency;
	}
	@Column(updatable=false)
	public int getShake_time() {
		return shake_time;
	}
	public void setShake_time(int shake_time) {
		this.shake_time = shake_time;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public int getSet_user() {
		return set_user;
	}
	public void setSet_user(int set_user) {
		this.set_user = set_user;
	}
	public Date getLastedittime() {
		return lastedittime;
	}
	public void setLastedittime(Date lastedittime) {
		this.lastedittime = lastedittime;
	}
	
}
