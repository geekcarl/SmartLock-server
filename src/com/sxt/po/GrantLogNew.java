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

@Entity
@Table(name="grantlog")
public class GrantLogNew {
	private int id;
	private String log_name;
	private Date grant_time;
	private int key_id;
	private List<GrantDetail> gds;
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	@OneToMany(cascade={CascadeType.REMOVE}, fetch=FetchType.EAGER, mappedBy="grantLog")
	public List<GrantDetail> getGds() {
		return gds;
	}
	public void setGds(List<GrantDetail> gds) {
		this.gds = gds;
	}
	public String getLog_name() {
		return log_name;
	}
	public void setLog_name(String log_name) {
		this.log_name = log_name;
	}
	public Date getGrant_time() {
		return grant_time;
	}
	public void setGrant_time(Date grant_time) {
		this.grant_time = grant_time;
	}
	public int getKey_id() {
		return key_id;
	}
	public void setKey_id(int key_id) {
		this.key_id = key_id;
	}
}
