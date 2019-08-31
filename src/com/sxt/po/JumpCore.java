package com.sxt.po;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class JumpCore {
	private int id;
	private BoxTerminal a;
	private BoxTerminal b;
	private Date timestamp;
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@OneToOne
	@JoinColumn(name="terminal_a_id")
	public BoxTerminal getA() {
		return a;
	}
	public void setA(BoxTerminal a) {
		this.a = a;
	}
	@OneToOne
	@JoinColumn(name="terminal_b_id")
	public BoxTerminal getB() {
		return b;
	}
	public void setB(BoxTerminal b) {
		this.b = b;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
}
