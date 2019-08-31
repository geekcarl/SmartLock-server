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
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonManagedReference;


@Entity
@Table(name="box_module")
@JsonIgnoreProperties(value={"boxInfo", "boxTerminals"})
public class BoxModule {
	private int id;
	private BoxInfo boxInfo;
	private String sideName;
	private int rows;
	private int cols;
	private List<BoxTerminal> boxTerminals;
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="box_id")
	public BoxInfo getBoxInfo() {
		return boxInfo;
	}
	public void setBoxInfo(BoxInfo boxInfo) {
		this.boxInfo = boxInfo;
	}
	public String getSideName() {
		return sideName;
	}
	public void setSideName(String sideName) {
		this.sideName = sideName;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getCols() {
		return cols;
	}
	public void setCols(int cols) {
		this.cols = cols;
	}
	@OneToMany(cascade={CascadeType.REFRESH, CascadeType.REMOVE}, mappedBy="boxModule")
	public List<BoxTerminal> getBoxTerminals() {
		return boxTerminals;
	}
	public void setBoxTerminals(List<BoxTerminal> boxTerminals) {
		this.boxTerminals = boxTerminals;
	}
}
