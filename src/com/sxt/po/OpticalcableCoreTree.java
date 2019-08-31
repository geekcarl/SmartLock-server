package com.sxt.po;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

public class OpticalcableCoreTree {
	private int id;
	private String name;
	private String startAddress;
	private BoxInfo start_box;
	private String endAddress;
	private BoxInfo end_box;
	private int coreCounts;
	private String type;
	private String remarks;
	private List<CoreUsed> cores;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStartAddress() {
		return startAddress;
	}
	public void setStartAddress(String startAddress) {
		this.startAddress = startAddress;
	}
	public BoxInfo getStart_box() {
		return start_box;
	}
	public void setStart_box(BoxInfo startBox) {
		start_box = startBox;
	}
	public String getEndAddress() {
		return endAddress;
	}
	public void setEndAddress(String endAddress) {
		this.endAddress = endAddress;
	}
	public BoxInfo getEnd_box() {
		return end_box;
	}
	public void setEnd_box(BoxInfo endBox) {
		end_box = endBox;
	}
	public int getCoreCounts() {
		return coreCounts;
	}
	public void setCoreCounts(int coreCounts) {
		this.coreCounts = coreCounts;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public List<CoreUsed> getCores() {
		return cores;
	}
	public void setCores(List<CoreUsed> cores) {
		this.cores = cores;
	}
}
