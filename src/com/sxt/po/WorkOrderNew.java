package com.sxt.po;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name="workorder")
public class WorkOrderNew {
	private int id;
	private String order_no;
	private String title;
	private int type;
	private User create_user;
	private Department department;
	private User receive_operators;
	private int done_type;
	private String content;
	private int create_type;
	private Date create_time;
	private Date respect_starttime;
	private Date respect_endtime;
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
	
	@ManyToOne
	@JoinColumn(name="user_id")
	@NotFound(action=NotFoundAction.IGNORE)
	public User getCreate_user() {
		return create_user;
	}
	public void setCreate_user(User createUser) {
		create_user = createUser;
	}
	@ManyToOne
	@JoinColumn(name="department_id")
	@NotFound(action=NotFoundAction.IGNORE)
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	
	@ManyToOne
	@JoinColumn(name="receive_operators_id")
	@NotFound(action=NotFoundAction.IGNORE)
	public User getReceive_operators() {
		return receive_operators;
	}
	public void setReceive_operators(User receiveOperators) {
		receive_operators = receiveOperators;
	}
	
	public int getDone_type() {
		return done_type;
	}
	public void setDone_type(int doneType) {
		done_type = doneType;
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
	public void setCreate_type(int createType) {
		create_type = createType;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date createTime) {
		create_time = createTime;
	}
	public Date getRespect_starttime() {
		return respect_starttime;
	}
	public void setRespect_starttime(Date respectStarttime) {
		respect_starttime = respectStarttime;
	}
	public Date getRespect_endtime() {
		return respect_endtime;
	}
	public void setRespect_endtime(Date respectEndtime) {
		respect_endtime = respectEndtime;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getIs_deleted() {
		return is_deleted;
	}
	public void setIs_deleted(int is_deleted) {
		this.is_deleted = is_deleted;
	}
	
}
