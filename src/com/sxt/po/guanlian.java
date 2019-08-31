package com.sxt.po;

import java.util.List;

public class guanlian {
	
	private AtomType from;
	private CoreAtomType  to;
	private int box_id;
	private String box_no;
	

	public int getBox_id() {
		return box_id;
	}
	public void setBox_id(int box_id) {
		this.box_id = box_id;
	}
	public AtomType getFrom() {
		return from;
	}
	public void setFrom(AtomType from) {
		this.from = from;
	}
	
	public String getBox_no() {
		return box_no;
	}
	public void setBox_no(String box_no) {
		this.box_no = box_no;
	}
	public CoreAtomType getTo() {
		return to;
	}
	public void setTo(CoreAtomType to) {
		this.to = to;
	}
	

}
