package com.sxt.po;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
@Table(name="box_terminal")
public class BoxTerminalNew {

	private int id;
	private Integer box_id;
	private Integer box_module_id;
	private int row;
	private int col;
	private String name;
	private String sideName;
	private String label_info;
	private int status;
	private String remarks;
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Integer getBox_id() {
		return box_id;
	}
	public void setBox_id(Integer box_id) {
		this.box_id = box_id;
	}
	public Integer getBox_module_id() {
		return box_module_id;
	}
	public void setBox_module_id(Integer box_module_id) {
		this.box_module_id = box_module_id;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSideName() {
		return sideName;
	}
	public void setSideName(String sideName) {
		this.sideName = sideName;
	}
	public String getLabel_info() {
		return label_info;
	}
	public void setLabel_info(String label_info) {
		this.label_info = label_info;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
