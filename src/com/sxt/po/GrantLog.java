package com.sxt.po;

import java.util.Date;
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

@Entity
public class GrantLog {
    private int id;
    private String log_name;
    private Date grant_time;
    private String grant_user;
    private KeyInfo key;
    private User operators;
    private String remarks;
    private List<GrantDetail> gds;

    private int dep_id;
    private int auth_type;
    private String operators_name;

    private String key_no;
    private String key_rfid;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLog_name() {
        return log_name;
    }

    public void setLog_name(String logName) {
        log_name = logName;
    }

    public Date getGrant_time() {
        return grant_time;
    }

    public void setGrant_time(Date grantTime) {
        grant_time = grantTime;
    }

    public String getGrant_user() {
        return grant_user;
    }

    public void setGrant_user(String grantUser) {
        grant_user = grantUser;
    }

    @OneToOne
    @JoinColumn(name = "key_id")
    public KeyInfo getKey() {
        return key;
    }

    public void setKey(KeyInfo key) {
        this.key = key;
    }

    @ManyToOne
    @JoinColumn(name = "operators_id")
    public User getOperators() {
        return operators;
    }

    public void setOperators(User operators) {
        this.operators = operators;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @OneToMany(cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER, mappedBy = "grantLog")
    public List<GrantDetail> getGds() {
        return gds;
    }

    public void setGds(List<GrantDetail> gds) {
        this.gds = gds;
    }

    public int getDep_id() {
        return this.dep_id;
    }

    public void setDep_id(int dep_id) {
        this.dep_id = dep_id;
    }

    public String getOperators_name() {
        return this.operators_name;
    }

    public void setOperators_name(String operators_name) {
        this.operators_name = operators_name;
    }

    public int getAuth_type() {
        return this.auth_type;
    }

    public void setAuth_type(int auth_type) {
        this.auth_type = auth_type;
    }

    public String getKey_no() {
        return key_no;
    }

    public void setKey_no(String key_no) {
        this.key_no = key_no;
    }

    public String getKey_rfid() {
        return key_rfid;
    }

    public void setKey_rfid(String key_rfid) {
        this.key_rfid = key_rfid;
    }

    public static final String STATE_PENDING = "0";
    public static final String STATE_AUTHING = "1";
    public static final String STATE_DOWNED = "2";
    public static final String STATE_ENDED = "3";
}
