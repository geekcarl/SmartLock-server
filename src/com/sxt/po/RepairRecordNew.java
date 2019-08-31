package com.sxt.po;


public class RepairRecordNew {
	

	private String depname;
	private String box_no;  	
	private String box_address;  	
	private String create_time;
	private String appuser;  	
	private String app_result;  //审批结果
	private String is_create;   //是否产生工单  	
	private String repairtype;  	
	private String remark;  	
	private String app_time;  	
	private String respect_start_time;
	private String respect_end_time;
	private int workorder_id;    //工单号
	

	public String getDepname() {
		return depname;
	}
	public void setDepname(String depname) {
		this.depname = depname;
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
	public String getAppuser() {
		return appuser;
	}
	public void setAppuser(String appuser) {
		this.appuser = appuser;
	}
	
	public String getIs_create() {
		return is_create;
	}
	public void setIs_create(String is_create) {
		this.is_create = is_create;
	}
	public String getRepairtype() {
		return repairtype;
	}
	public void setRepairtype(String repairtype) {
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
	public String getApp_result() {
		return app_result;
	}
	public void setApp_result(String app_result) {
		this.app_result = app_result;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	


	
	
	

}
