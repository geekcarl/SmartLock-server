package com.sxt.po;

import java.util.Date;
import java.util.List;


public class WorkOrderUpload {
	private int id;
	/*private String receive_operators;
	private String receive_time;
	private String content;
	private String create_type;
	private String create_time;
	private String respect_starttime;
	private String respect_endtime;*/
	private int  done_type;
	private String infact_starttime;
	private String infact_endtime;
	private String remarks;
	private List<tiaoqian> tq;
	//private List<guanlian> als;
	//private List<zhirong> ois;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getInfact_starttime() {
		return infact_starttime;
	}
	public void setInfact_starttime(String infact_starttime) {
		this.infact_starttime = infact_starttime;
	}
	public String getInfact_endtime() {
		return infact_endtime;
	}
	public void setInfact_endtime(String infact_endtime) {
		this.infact_endtime = infact_endtime;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public List<tiaoqian> getTq() {
		return tq;
	}
	public void setTq(List<tiaoqian> tq) {
		this.tq = tq;
	}
	public int getDone_type() {
		return done_type;
	}
	public void setDone_type(int done_type) {
		this.done_type = done_type;
	}
	
	

	
	

	
}
