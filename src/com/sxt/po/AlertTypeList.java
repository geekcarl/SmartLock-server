package com.sxt.po;

import java.util.List;

public class AlertTypeList {

	private String typename;
	private int  count;
	private List<BoxInfoGroupbyDep>  boxlist;
	
	
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<BoxInfoGroupbyDep> getBoxlist() {
		return boxlist;
	}
	public void setBoxlist(List<BoxInfoGroupbyDep> boxlist) {
		this.boxlist = boxlist;
	}
	
}
