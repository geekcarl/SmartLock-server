package com.sxt.po;

public class UserInfoPhoneRmoteGrant {
    private String grantName;
    private int userid;
    private String grantUserName;
    private String boxid;
    private String begin_time;
    private String end_time;

    public UserInfoPhoneRmoteGrant() {
    }

    public int getUserid() {
        return this.userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getBoxid() {
        return this.boxid;
    }

    public void setBoxid(String boxid) {
        this.boxid = boxid;
    }

    public String getBegin_time() {
        return this.begin_time;
    }

    public void setBegin_time(String begin_time) {
        this.begin_time = begin_time;
    }

    public String getEnd_time() {
        return this.end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getGrantName() {
        return grantName;
    }

    public void setGrantName(String grantName) {
        this.grantName = grantName;
    }


    public String getGrantUserName() {
        return grantUserName;
    }

    public void setGrantUserName(String grantUserName) {
        this.grantUserName = grantUserName;
    }
}
