package com.sxt.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="modificationinfo")
public class ModificationInfo {

	private int id;
	private int box_id;
	private int flag;
	private int shake_threshold;
	private int shake_rate;
	private String center_ip;
	private int center_upd_port;
	private int is_send;
	private Date sendsuccess_time;
	private int set_user;
	private Date lastedittime;
	private String remarks;
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
	public int getShake_threshold() {
		return shake_threshold;
	}
	public void setShake_threshold(int shake_threshold) {
		this.shake_threshold = shake_threshold;
	}
	public int getShake_rate() {
		return shake_rate;
	}
	public void setShake_rate(int shake_rate) {
		this.shake_rate = shake_rate;
	}
	public String getCenter_ip() {
		return center_ip;
	}
	public void setCenter_ip(String center_ip) {
		this.center_ip = center_ip;
	}
	public int getCenter_upd_port() {
		return center_upd_port;
	}
	public void setCenter_upd_port(int center_upd_port) {
		this.center_upd_port = center_upd_port;
	}
	@Column(updatable=false)
	public int getIs_send() {
		return is_send;
	}
	public void setIs_send(int is_send) {
		this.is_send = is_send;
	}
	@Column(updatable=false)
	public Date getSendsuccess_time() {
		return sendsuccess_time;
	}
	public void setSendsuccess_time(Date sendsuccess_time) {
		this.sendsuccess_time = sendsuccess_time;
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
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
