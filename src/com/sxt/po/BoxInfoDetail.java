package com.sxt.po;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;



public class BoxInfoDetail {
	private int id;
	private String box_no;
	private long controller_id;
	private String address;
	private String sim_phone_no;
	private String department;
	private String box_type;
	private String use_state;
	private Integer locks_count;
    private Integer dbm;  //信号强度
    private String connStates;   //在线状态
    private String doorStates;   //门状态
    private String volStates;    //电量状态
    private String boxStates;   //箱体状态
    private String waterStates;   //浸水状态
	private String tempStates;  //温度状态
	private String last_heard;
	private Date installDate;
	private double longitude;
	private double latitude;
	private String k_code;
	private List<LockSimpleInfo>  lockinfo;
	
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
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getBox_type() {
		return box_type;
	}
	public void setBox_type(String box_type) {
		this.box_type = box_type;
	}
	public Integer getLocks_count() {
		return locks_count;
	}
	public void setLocks_count(Integer locks_count) {
		this.locks_count = locks_count;
	}
	public Integer getDbm() {
		return dbm;
	}
	public void setDbm(Integer dbm) {
		this.dbm = dbm;
	}
	
	public String getLast_heard() {
		return last_heard;
	}
	public void setLast_heard(String last_heard) {
		this.last_heard = last_heard;
	}
	public Date getInstallDate() {
		return installDate;
	}
	public void setInstallDate(Date installDate) {
		this.installDate = installDate;
	}
	public String getUse_state() {
		return use_state;
	}
	public void setUse_state(String use_state) {
		this.use_state = use_state;
	}
	public String getConnStates() {
		return connStates;
	}
	public void setConnStates(String connStates) {
		this.connStates = connStates;
	}
	public String getDoorStates() {
		return doorStates;
	}
	public void setDoorStates(String doorStates) {
		this.doorStates = doorStates;
	}
	public String getVolStates() {
		return volStates;
	}
	public void setVolStates(String volStates) {
		this.volStates = volStates;
	}
	public String getWaterStates() {
		return waterStates;
	}
	public void setWaterStates(String waterStates) {
		this.waterStates = waterStates;
	}
	public String getTempStates() {
		return tempStates;
	}
	public void setTempStates(String tempStates) {
		this.tempStates = tempStates;
	}
	public String getBoxStates() {
		return boxStates;
	}
	public void setBoxStates(String boxStates) {
		this.boxStates = boxStates;
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
	public String getK_code() {
		return k_code;
	}
	public void setK_code(String k_code) {
		this.k_code = k_code;
	}
	
	
	public List<LockSimpleInfo> getLockinfo() {
		return lockinfo;
	}
	public void setLockinfo(List<LockSimpleInfo> lockinfo) {
		this.lockinfo = lockinfo;
	}
	

	

}
