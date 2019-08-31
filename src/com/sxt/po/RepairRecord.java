package com.sxt.po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class RepairRecord {
	
	private int id;
	private int user_id;   //申请人
	private int department_id;
	private String create_time;
	private int box_id;
	private Integer appuser_id;  //审批人
	private int app_result;  //审批结果
	private int is_create;  	
	private int repairtype;  	
	private String remark;  	
	private String app_time;  	
	private int receivedepartment_id;  	
	private int receiveuser_id;  	
	private String respect_start_time;
	private String respect_end_time;
	private int workorder_id;
	private int is_deleted;
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getDepartment_id() {
		return department_id;
	}
	public void setDepartment_id(int department_id) {
		this.department_id = department_id;
	}
	public int getBox_id() {
		return box_id;
	}
	public void setBox_id(int box_id) {
		this.box_id = box_id;
	}
	public Integer getAppuser_id() {
		return appuser_id;
	}
	public void setAppuser_id(Integer appuser_id) {
		this.appuser_id = appuser_id;
	}
	public int getApp_result() {
		return app_result;
	}
	public void setApp_result(int app_result) {
		this.app_result = app_result;
	}
	public int getIs_create() {
		return is_create;
	}
	public void setIs_create(int is_create) {
		this.is_create = is_create;
	}
	public int getRepairtype() {
		return repairtype;
	}
	public void setRepairtype(int repairtype) {
		this.repairtype = repairtype;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getApp_time() {
		return app_time;
	}
	public void setApp_time(String app_time) {
		this.app_time = app_time;
	}
	public int getReceivedepartment_id() {
		return receivedepartment_id;
	}
	public void setReceivedepartment_id(int receivedepartment_id) {
		this.receivedepartment_id = receivedepartment_id;
	}
	public int getReceiveuser_id() {
		return receiveuser_id;
	}
	public void setReceiveuser_id(int receiveuser_id) {
		this.receiveuser_id = receiveuser_id;
	}
	public String getRespect_start_time() {
		return respect_start_time;
	}
	public void setRespect_start_time(String respect_start_time) {
		this.respect_start_time = respect_start_time;
	}
	public String getRespect_end_time() {
		return respect_end_time;
	}
	public void setRespect_end_time(String respect_end_time) {
		this.respect_end_time = respect_end_time;
	}
	public int getWorkorder_id() {
		return workorder_id;
	}
	public void setWorkorder_id(int workorder_id) {
		this.workorder_id = workorder_id;
	}
	public int getIs_deleted() {
		return is_deleted;
	}
	public void setIs_deleted(int is_deleted) {
		this.is_deleted = is_deleted;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	
	
	
	

}
