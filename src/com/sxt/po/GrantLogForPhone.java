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
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name="grantlog")
public class GrantLogForPhone {
	private int id;
	private String log_name;
	private Date grant_time;
	private Integer auth_type;
	private String operators_name;
	private String key_no;
	private String key_rfid;
	private String card_no;
	private String card_rfid;
	private String grant_user;
	private Integer key_id;
	private Integer dep_id;
	private Integer operators_id;
	private String remarks;
	private List<GrantDetailforPhone> gds;
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLog_name() {
		return log_name;
	}
	public void setLog_name(String logName) {
		log_name = logName;
	}
	public Date getGrant_time() {
		return grant_time;
	}
	public void setGrant_time(Date grantTime) {
		grant_time = grantTime;
	}
	public String getGrant_user() {
		return grant_user;
	}
	public void setGrant_user(String grantUser) {
		grant_user = grantUser;
	}
	
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@OneToMany(cascade={CascadeType.REMOVE}, fetch=FetchType.EAGER, mappedBy="grantLog")
	public List<GrantDetailforPhone> getGds() {
		return gds;
	}
	public void setGds(List<GrantDetailforPhone> gds) {
		this.gds = gds;
	}
	
	public String getOperators_name() {
		return operators_name;
	}
	public void setOperators_name(String operators_name) {
		this.operators_name = operators_name;
	}
	public String getKey_no() {
		return key_no;
	}
	public void setKey_no(String key_no) {
		this.key_no = key_no;
	}
	public String getKey_rfid() {
		return key_rfid;
	}
	public void setKey_rfid(String key_rfid) {
		this.key_rfid = key_rfid;
	}
	public String getCard_no() {
		return card_no;
	}
	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}
	public String getCard_rfid() {
		return card_rfid;
	}
	public void setCard_rfid(String card_rfid) {
		this.card_rfid = card_rfid;
	}
	public Integer getAuth_type() {
		return auth_type;
	}
	public void setAuth_type(Integer auth_type) {
		this.auth_type = auth_type;
	}
	public Integer getKey_id() {
		return key_id;
	}
	public void setKey_id(Integer key_id) {
		this.key_id = key_id;
	}
	public Integer getDep_id() {
		return dep_id;
	}
	public void setDep_id(Integer dep_id) {
		this.dep_id = dep_id;
	}
	public Integer getOperators_id() {
		return operators_id;
	}
	public void setOperators_id(Integer operators_id) {
		this.operators_id = operators_id;
	}
}
