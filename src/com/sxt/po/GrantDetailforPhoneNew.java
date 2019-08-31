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

public class GrantDetailforPhoneNew {
    
	private Integer hjjf_id;
	private String hjjf_no;
	private String begin_time;
	private String end_time;
	
	public Integer getHjjf_id() {
		return hjjf_id;
	}
	public void setHjjf_id(Integer hjjf_id) {
		this.hjjf_id = hjjf_id;
	}
	public String getHjjf_no() {
		return hjjf_no;
	}
	public void setHjjf_no(String hjjf_no) {
		this.hjjf_no = hjjf_no;
	}
	public String getBegin_time() {
		return begin_time;
	}
	public void setBegin_time(String begin_time) {
		this.begin_time = begin_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	
}
