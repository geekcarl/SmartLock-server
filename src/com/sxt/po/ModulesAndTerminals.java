package com.sxt.po;

import java.util.List;

public class ModulesAndTerminals {
	private List<BoxModuleNew> bms;
	private List<BoxTerminalUsedNew> bts;
	
	
	public List<BoxModuleNew> getBms() {
		return bms;
	}
	public void setBms(List<BoxModuleNew> bms) {
		this.bms = bms;
	}
	public List<BoxTerminalUsedNew> getBts() {
		return bts;
	}
	public void setBts(List<BoxTerminalUsedNew> bts) {
		this.bts = bts;
	}
}
