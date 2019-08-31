package com.sxt.po;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "doorevent")
public class DoorEventAdd {
    private int id;
    private int box_id;
    private Date open_time;
    private String open_keys;
    private String open_operators;
    private String open_rfids;
    private Date close_time;
    private String close_keys;
    private String close_operators;
    private String close_rfids;
    private String remarks;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBox_id() {
        return box_id;
    }

    public void setBox_id(int box_id) {
        this.box_id = box_id;
    }

    public Date getOpen_time() {
        return open_time;
    }

    public void setOpen_time(Date open_time) {
        this.open_time = open_time;
    }

    public String getOpen_keys() {
        return open_keys;
    }

    public void setOpen_keys(String open_keys) {
        this.open_keys = open_keys;
    }

    public String getOpen_operators() {
        return open_operators;
    }

    public void setOpen_operators(String open_operators) {
        this.open_operators = open_operators;
    }

    public String getOpen_rfids() {
        return open_rfids;
    }

    public void setOpen_rfids(String open_rfids) {
        this.open_rfids = open_rfids;
    }

    public Date getClose_time() {
        return close_time;
    }

    public void setClose_time(Date close_time) {
        this.close_time = close_time;
    }

    public String getClose_keys() {
        return close_keys;
    }

    public void setClose_keys(String close_keys) {
        this.close_keys = close_keys;
    }

    public String getClose_operators() {
        return close_operators;
    }

    public void setClose_operators(String close_operators) {
        this.close_operators = close_operators;
    }

    public String getClose_rfids() {
        return close_rfids;
    }

    public void setClose_rfids(String close_rfids) {
        this.close_rfids = close_rfids;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "DoorEventAdd{" +
                "id=" + id +
                ", box_id=" + box_id +
                ", open_time=" + open_time +
                ", open_keys='" + open_keys + '\'' +
                ", open_operators='" + open_operators + '\'' +
                ", open_rfids='" + open_rfids + '\'' +
                ", close_time=" + close_time +
                ", close_keys='" + close_keys + '\'' +
                ", close_operators='" + close_operators + '\'' +
                ", close_rfids='" + close_rfids + '\'' +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
