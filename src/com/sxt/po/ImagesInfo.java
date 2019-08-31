package com.sxt.po;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


 
public class ImagesInfo {
	
	private String picturetime;
	private int count;
	
	
	public String getPicturetime() {
		return picturetime;
	}
	public void setPicturetime(String picturetime) {
		this.picturetime = picturetime;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}

	
	
	

}
