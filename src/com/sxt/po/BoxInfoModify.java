package com.sxt.po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.Table;


@Entity
@Table(name="boxinfo")
public class BoxInfoModify {
	
	private int id;
	private String box_no;
	private long controller_id;
	private String box_name;
	private String address;
	private String sim_phone_no;
	private double longitude;
	private double latitude;
	private double b_longitude;
	private double b_latitude;
	private int department_id;
	private String box_type;
	private int locks_count;
	private String k_code;
	private String remarks;
	private int use_state;
	private int is_deleted;
	private String lastedittime;
	private Integer lastedituser;
	

	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBox_no() {
		return box_no;
	}
	public void setBox_no(String box_no) {
		this.box_no = box_no;
	}
	public long getController_id() {
		return controller_id;
	}
	public void setController_id(long controller_id) {
		this.controller_id = controller_id;
	}
	public String getBox_name() {
		return box_name;
	}
	public void setBox_name(String box_name) {
		this.box_name = box_name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSim_phone_no() {
		return sim_phone_no;
	}
	public void setSim_phone_no(String sim_phone_no) {
		this.sim_phone_no = sim_phone_no;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public int getDepartment_id() {
		return department_id;
	}
	public void setDepartment_id(int department_id) {
		this.department_id = department_id;
	}
	public String getBox_type() {
		return box_type;
	}
	public void setBox_type(String box_type) {
		this.box_type = box_type;
	}
	public int getLocks_count() {
		return locks_count;
	}
	public void setLocks_count(int locks_count) {
		this.locks_count = locks_count;
	}
	public String getK_code() {
		return k_code;
	}
	public void setK_code(String k_code) {
		this.k_code = k_code;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public int getUse_state() {
		return use_state;
	}
	public void setUse_state(int use_state) {
		this.use_state = use_state;
	}
	public double getB_longitude() {
		return b_longitude;
	}
	public void setB_longitude(double b_longitude) {
		this.b_longitude = b_longitude;
	}
	public double getB_latitude() {
		return b_latitude;
	}
	public void setB_latitude(double b_latitude) {
		this.b_latitude = b_latitude;
	}
	public int getIs_deleted() {
		return is_deleted;
	}
	public void setIs_deleted(int is_deleted) {
		this.is_deleted = is_deleted;
	}
	public String getLastedittime() {
		return lastedittime;
	}
	public void setLastedittime(String lastedittime) {
		this.lastedittime = lastedittime;
	}
	public Integer getLastedituser() {
		return lastedituser;
	}
	public void setLastedituser(Integer lastedituser) {
		this.lastedituser = lastedituser;
	}

}
