package com.sxt.po;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="cellgrantlog")
public class CellGrantLog {

	private Integer id;
	private String user_name;
	private String Lock_code;
	private Integer Box_id;
	private Date Createtime;
	private Integer Server_flag;
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getLock_code() {
		return Lock_code;
	}
	public void setLock_code(String lock_code) {
		Lock_code = lock_code;
	}
	public Integer getBox_id() {
		return Box_id;
	}
	public void setBox_id(Integer box_id) {
		Box_id = box_id;
	}
	public Date getCreatetime() {
		return Createtime;
	}
	public void setCreatetime(Date createtime) {
		Createtime = createtime;
	}
	public Integer getServer_flag() {
		return Server_flag;
	}
	public void setServer_flag(Integer server_flag) {
		Server_flag = server_flag;
	}
	
}
