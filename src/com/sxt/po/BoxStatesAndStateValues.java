package com.sxt.po;

import java.util.List;

public class BoxStatesAndStateValues {
	private List<BoxInfo> bis;
	
	//获取的时候按照等级排序
	private List<DTO_BoxStates_Values_Levels> bss;

	//获取的时候按照等级排序
	private List<StateValue> svs;
	public List<BoxInfo> getBis() {
		return bis;
	}
	public void setBis(List<BoxInfo> bis) {
		this.bis = bis;
	}
	public List<DTO_BoxStates_Values_Levels> getBss() {
		return bss;
	}
	public void setBss(List<DTO_BoxStates_Values_Levels> bss) {
		this.bss = bss;
	}
	public List<StateValue> getSvs() {
		return svs;
	}
	public void setSvs(List<StateValue> svs) {
		this.svs = svs;
	}
}
