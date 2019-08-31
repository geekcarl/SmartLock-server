package com.sxt.po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name="workorder")
public class WorkOrderTable {
	private int id;
	private String order_no;
	private String title;
	private int type;
	private Integer user_id;
	private Integer department_id;
	private Integer receive_operators_id;
	private int done_type;
	private String content;
	private int create_type;
	private String create_time;
	private String respect_starttime;
	private String respect_endtime;
	private String remarks;
	private int is_deleted;

	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String orderNo) {
		order_no = orderNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	
	@NotFound(action=NotFoundAction.IGNORE)
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	
	@NotFound(action=NotFoundAction.IGNORE)
	public Integer getDepartment_id() {
		return department_id;
	}
	public void setDepartment_id(Integer department_id) {
		this.department_id = department_id;
	}
	
	@NotFound(action=NotFoundAction.IGNORE)
	public Integer getReceive_operators_id() {
		return receive_operators_id;
	}
	public void setReceive_operators_id(Integer receive_operators_id) {
		this.receive_operators_id = receive_operators_id;
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
	public int getCreate_type() {
		return create_type;
	}
	public void setCreate_type(int create_type) {
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
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public int getIs_deleted() {
		return is_deleted;
	}
	public void setIs_deleted(int is_deleted) {
		this.is_deleted = is_deleted;
	}
	

	
	
}
