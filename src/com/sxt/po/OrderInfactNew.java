package com.sxt.po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.Table;

@Entity
@Table(name="orderinfact")
public class OrderInfactNew {
	private int id;
	private int order_id;
	private int order_type;
	private int box_id;
	private int core_id;
	private int terminal_id;
	private int a_terminal_id;
	private int z_terminal_id;
	private int a_core_id;
	private int z_core_id;
	private int operate_type;
	private int sequence;
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	public int getOrder_type() {
		return order_type;
	}
	public void setOrder_type(int order_type) {
		this.order_type = order_type;
	}
	public int getBox_id() {
		return box_id;
	}
	public void setBox_id(int box_id) {
		this.box_id = box_id;
	}
	public int getCore_id() {
		return core_id;
	}
	public void setCore_id(int core_id) {
		this.core_id = core_id;
	}
	public int getTerminal_id() {
		return terminal_id;
	}
	public void setTerminal_id(int terminal_id) {
		this.terminal_id = terminal_id;
	}
	public int getA_terminal_id() {
		return a_terminal_id;
	}
	public void setA_terminal_id(int a_terminal_id) {
		this.a_terminal_id = a_terminal_id;
	}
	public int getZ_terminal_id() {
		return z_terminal_id;
	}
	public void setZ_terminal_id(int z_terminal_id) {
		this.z_terminal_id = z_terminal_id;
	}
	public int getA_core_id() {
		return a_core_id;
	}
	public void setA_core_id(int a_core_id) {
		this.a_core_id = a_core_id;
	}
	public int getZ_core_id() {
		return z_core_id;
	}
	public void setZ_core_id(int z_core_id) {
		this.z_core_id = z_core_id;
	}
	public int getOperate_type() {
		return operate_type;
	}
	public void setOperate_type(int operate_type) {
		this.operate_type = operate_type;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	
	
}
