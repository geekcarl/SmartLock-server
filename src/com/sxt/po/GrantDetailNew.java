package com.sxt.po;

import java.util.List;


public class GrantDetailNew {
	private String begin_time;
	private String end_time;
	private List<LockModify> locklist;
	
	
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
	public List<LockModify> getLocklist() {
		return locklist;
	}
	public void setLocklist(List<LockModify> locklist) {
		this.locklist = locklist;
	}
	
	
}
