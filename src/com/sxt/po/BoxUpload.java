package com.sxt.po;

import java.util.Date;
import java.util.List;



public class BoxUpload {
	private int id;
	private String box_no;
	private long controller_id;
	private String box_name;
	private String address;
	private String sim_phone_no;
	private double longitude;
	private double latitude;
	
	private int department_id;
	private String box_type;
	private int locks_count;
	private String k_code;
	private String remarks;
	private List<LockSimpleInfo>  locklist;
	

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
	
	public int getDepartment_id() {
		return department_id;
	}
	public void setDepartment_id(int department_id) {
		this.department_id = department_id;
	}
	public List<LockSimpleInfo> getLocklist() {
		return locklist;
	}
	public void setLocklist(List<LockSimpleInfo> locklist) {
		this.locklist = locklist;
	}

	

	
}
