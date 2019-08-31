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

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


public class WorkOrderDetail {
	private int id;
	private String order_no;
	private String title;
	private String type;
	private String create_user;
	private String department;
	private String receive_operators;
	private String receive_time;
	private String done_time;
	private int done_type;
	private String content;
	private String create_type;
	private String create_time;
	private String respect_starttime;
	private String respect_endtime;
	private String infact_starttime;
	private String infact_endtime;
	private String remarks;
	private List<BoxModule> bms;
	private List<tiaoqian> tq;
	private List<guanlian> gl;
	private List<zhirong> zr;
	private List<OpticalcableCore> opcorelst;
	private List<AlarmOrderDetailsNew> als;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCreate_user() {
		return create_user;
	}
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getReceive_operators() {
		return receive_operators;
	}
	public void setReceive_operators(String receive_operators) {
		this.receive_operators = receive_operators;
	}
	public String getReceive_time() {
		return receive_time;
	}
	public void setReceive_time(String receive_time) {
		this.receive_time = receive_time;
	}
	public String getDone_time() {
		return done_time;
	}
	public void setDone_time(String done_time) {
		this.done_time = done_time;
	}
	public int getDone_type() {
		return done_type;
	}
	public void setDone_type(int done_type) {
		this.done_type = done_type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreate_type() {
		return create_type;
	}
	public void setCreate_type(String create_type) {
		this.create_type = create_type;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getRespect_starttime() {
		return respect_starttime;
	}
	public void setRespect_starttime(String respect_starttime) {
		this.respect_starttime = respect_starttime;
	}
	public String getRespect_endtime() {
		return respect_endtime;
	}
	public void setRespect_endtime(String respect_endtime) {
		this.respect_endtime = respect_endtime;
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
	/*public List<OrderRespectDetail> getOps() {
		return ops;
	}
	public void setOps(List<OrderRespectDetail> ops) {
		this.ops = ops;
	}*/
	public List<AlarmOrderDetailsNew> getAls() {
		return als;
	}
	public void setAls(List<AlarmOrderDetailsNew> als) {
		this.als = als;
	}
	public List<tiaoqian> getTq() {
		return tq;
	}
	public void setTq(List<tiaoqian> tq) {
		this.tq = tq;
	}
	public List<guanlian> getGl() {
		return gl;
	}
	public void setGl(List<guanlian> gl) {
		this.gl = gl;
	}
	public List<OpticalcableCore> getOpcorelst() {
		return opcorelst;
	}
	public void setOpcorelst(List<OpticalcableCore> opcorelst) {
		this.opcorelst = opcorelst;
	}
	public List<zhirong> getZr() {
		return zr;
	}
	public void setZr(List<zhirong> zr) {
		this.zr = zr;
	}
	public List<BoxModule> getBms() {
		return bms;
	}
	public void setBms(List<BoxModule> bms) {
		this.bms = bms;
	}
	
	

	
}
