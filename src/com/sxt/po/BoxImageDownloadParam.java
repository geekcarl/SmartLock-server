package com.sxt.po;

import java.util.Date;


public class BoxImageDownloadParam {
	
	private Integer boxid;
	private Date pictureTime;
	private Integer num;
	
	public Integer getBoxid() {
		return boxid;
	}
	public void setBoxid(Integer boxid) {
		this.boxid = boxid;
	}
	public Date getPictureTime() {
		return pictureTime;
	}
	public void setPictureTime(Date pictureTime) {
		this.pictureTime = pictureTime;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}

}
