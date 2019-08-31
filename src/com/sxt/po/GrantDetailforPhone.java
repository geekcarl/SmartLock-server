package com.sxt.po;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
@Table(name="grantdetail")
@JsonIgnoreProperties(value={"grantLog"})
public class GrantDetailforPhone {
	private int id;
	private GrantLogForPhone grantLog;
	private Integer box_id;
	private Date begin_time;
	private Date end_time;
	private String remarks;
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	} 
	public void setId(int id) {
		this.id = id;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="log_id")
	public GrantLogForPhone getGrantLog() {
		return grantLog;
	}
	public void setGrantLog(GrantLogForPhone grantLog) {
		this.grantLog = grantLog;
	}

	public Date getBegin_time() {
		return begin_time;
	}
	public void setBegin_time(Date beginTime) {
		begin_time = beginTime;
	}
	public Date getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Date endTime) {
		end_time = endTime;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public Integer getBox_id() {
		return box_id;
	}
	public void setBox_id(Integer box_id) {
		this.box_id = box_id;
	}
	
	
}
