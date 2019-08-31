package com.sxt.po;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(value={"boxInfo"}) 
public class BoxVarInfo {
	private int id;
	private BoxInfoModify boxInfo;
	private Date last_heard;
	private Date power_on;
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="box_id")
	public BoxInfoModify getBoxInfo() {
		return boxInfo;
	}
	
	public void setBoxInfo(BoxInfoModify b) {
		this.boxInfo = b;
	}
	public Date getLast_heard() {
		return last_heard;
	}
	public void setLast_heard(Date lastHeard) {
		last_heard = lastHeard;
	}
	public Date getPower_on() {
		return power_on;
	}
	public void setPower_on(Date powerOn) {
		power_on = powerOn;
	}
}
