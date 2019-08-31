package com.sxt.po;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


@Entity 
@JsonIgnoreProperties(value={"boxModules"})
public class BoxInfo {
	private int id;
	private String box_no;
	private Long controller_id;
	private String box_name;
	private String address;
	private String business_area;
	private String sim_phone_no;
	private Double longitude;
	private Double latitude;
	private Department department;
	private String box_type;
	private int locks_count;
	private Double b_longitude;
	private Double b_latitude;
	private String k_code;
	private String remarks;
	private int use_state;
	private String use_begindate;
	private String  use_enddate;
	private Integer workorder_department;
	private Integer workorder_receive_id;
	private BoxVarInfo boxVarInfo;
	private List<BoxStates> boxStates;
	private List<BoxModule> boxModules;

	
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
	public void setBox_no(String boxNo) {
		box_no = boxNo;
	}
	public Long getController_id() {
		return controller_id;
	}
	public void setController_id(Long controllerId) {
		controller_id = controllerId;
	}
	public String getBox_name() {
		return box_name;
	}
	public void setBox_name(String boxName) {
		box_name = boxName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBusiness_area() {
		return business_area;
	}
	public void setBusiness_area(String businessArea) {
		business_area = businessArea;
	}
	public String getSim_phone_no() {
		return sim_phone_no;
	}
	public void setSim_phone_no(String simPhoneNo) {
		sim_phone_no = simPhoneNo;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	
	@ManyToOne
	@JoinColumn(name="department_id")
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public String getBox_type() {
		return box_type;
	}
	public void setBox_type(String boxType) {
		box_type = boxType;
	}
	public int getLocks_count() {
		return locks_count;
	}
	public void setLocks_count(int locksCount) {
		locks_count = locksCount;
	}
	public double getB_longitude() {
		return b_longitude;
	}
	public void setB_longitude(Double bLongitude) {
		b_longitude = bLongitude;
	}
	public double getB_latitude() {
		return b_latitude;
	}
	public void setB_latitude(Double bLatitude) {
		b_latitude = bLatitude;
	}
	public String getK_code() {
		return k_code;
	}
	public void setK_code(String kCode) {
		k_code = kCode;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	@OneToOne(cascade={CascadeType.REFRESH, CascadeType.REMOVE}, fetch=FetchType.EAGER, mappedBy="boxInfo")
	public BoxVarInfo getBoxVarInfo() {
		return boxVarInfo;
	}
	public void setBoxVarInfo(BoxVarInfo boxVarInfo) {
		this.boxVarInfo = boxVarInfo;
	}
	@OneToMany(cascade={CascadeType.REFRESH, CascadeType.REMOVE}, fetch=FetchType.EAGER, mappedBy="boxInfo")
	public List<BoxStates> getBoxStates() {
		return boxStates;
	}
	public void setBoxStates(List<BoxStates> boxStates) {
		this.boxStates = boxStates;
	}
	
	@OneToMany(cascade={CascadeType.REFRESH, CascadeType.REMOVE}, mappedBy="boxInfo")
	public List<BoxModule> getBoxModules() {
		return boxModules;
	}
	public void setBoxModules(List<BoxModule> boxModules) {
		this.boxModules = boxModules;
	}
	
	public int getUse_state() {
		return use_state;
	}
	public void setUse_state(int use_state) {
		this.use_state = use_state;
	}
	public String getUse_begindate() {
		return use_begindate;
	}
	public void setUse_begindate(String use_begindate) {
		this.use_begindate = use_begindate;
	}
	public String getUse_enddate() {
		return use_enddate;
	}
	public void setUse_enddate(String use_enddate) {
		this.use_enddate = use_enddate;
	}
	public Integer getWorkorder_department() {
		return workorder_department;
	}
	public void setWorkorder_department(Integer workorder_department) {
		this.workorder_department = workorder_department;
	}
	public Integer getWorkorder_receive_id() {
		return workorder_receive_id;
	}
	public void setWorkorder_receive_id(Integer workorder_receive_id) {
		this.workorder_receive_id = workorder_receive_id;
	}
	

	
	
	
	
}
