package com.sxt.po;

import java.util.List;

public class ModulesAndTerminalsForXQ {
	private List<BoxModuleNew> bms;
	private List<BoxTerminalUsedForXQNew> bts;
	
	
	public List<BoxModuleNew> getBms() {
		return bms;
	}
	public void setBms(List<BoxModuleNew> bms) {
		this.bms = bms;
	}
	public List<BoxTerminalUsedForXQNew> getBts() {
		return bts;
	}
	public void setBts(List<BoxTerminalUsedForXQNew> bts) {
		this.bts = bts;
	}
}
