package com.sxt.po;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

public class BoxTerminalUsedForXQNew {
	private int id;
	private BoxTerminalNew boxTerminal;
	private int frontUsed;
	private int frontFreezed;
	private List<BoxTerminalNew> frontTerminal;
	private int backUsed;
	private int backFreezed;
	private CoreNew backCore;
	
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public BoxTerminalNew getBoxTerminal() {
		return boxTerminal;
	}
	public void setBoxTerminal(BoxTerminalNew boxTerminal) {
		this.boxTerminal = boxTerminal;
	}
	public int getFrontUsed() {
		return frontUsed;
	}
	public void setFrontUsed(int frontUsed) {
		this.frontUsed = frontUsed;
	}
	public int getFrontFreezed() {
		return frontFreezed;
	}
	public void setFrontFreezed(int frontFreezed) {
		this.frontFreezed = frontFreezed;
	}
	
	
	public int getBackUsed() {
		return backUsed;
	}
	public void setBackUsed(int backUsed) {
		this.backUsed = backUsed;
	}
	public int getBackFreezed() {
		return backFreezed;
	}
	public void setBackFreezed(int backFreezed) {
		this.backFreezed = backFreezed;
	}
	
	public CoreNew getBackCore() {
		return backCore;
	}
	public void setBackCore(CoreNew backCore) {
		this.backCore = backCore;
	}

	public List<BoxTerminalNew> getFrontTerminal() {
		return frontTerminal;
	}

	public void setFrontTerminal(List<BoxTerminalNew> frontTerminal) {
		this.frontTerminal = frontTerminal;
	}

	
	
}
