package com.sxt.po;


public class OrderRespectDetail {
	private int id;
	private int order_id;
	private String order_type;   //1、成端2、跳纤3、直熔
	private int box_id;
	private String box_no;
	private String box_address;
	private String core;
	private String terminal;
	private String a_terminal;
	private String z_terminal;
	private String a_core;
	private String z_core;
	private String operate_type;   //1、安装2、解除安装
	private int sequence;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	public String getOrder_type() {
		return order_type;
	}
	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}
	public int getBox_id() {
		return box_id;
	}
	public void setBox_id(int box_id) {
		this.box_id = box_id;
	}
	public String getBox_no() {
		return box_no;
	}
	public void setBox_no(String box_no) {
		this.box_no = box_no;
	}
	public String getBox_address() {
		return box_address;
	}
	public void setBox_address(String box_address) {
		this.box_address = box_address;
	}
	public String getCore() {
		return core;
	}
	public void setCore(String core) {
		this.core = core;
	}
	public String getTerminal() {
		return terminal;
	}
	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}
	public String getA_terminal() {
		return a_terminal;
	}
	public void setA_terminal(String a_terminal) {
		this.a_terminal = a_terminal;
	}
	public String getZ_terminal() {
		return z_terminal;
	}
	public void setZ_terminal(String z_terminal) {
		this.z_terminal = z_terminal;
	}
	public String getA_core() {
		return a_core;
	}
	public void setA_core(String a_core) {
		this.a_core = a_core;
	}
	public String getZ_core() {
		return z_core;
	}
	public void setZ_core(String z_core) {
		this.z_core = z_core;
	}
	public String getOperate_type() {
		return operate_type;
	}
	public void setOperate_type(String operate_type) {
		this.operate_type = operate_type;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	
	
	
	
	
}
