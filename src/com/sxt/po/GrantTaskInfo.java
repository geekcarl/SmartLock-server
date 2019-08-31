package com.sxt.po;

import java.util.ArrayList;
import java.util.List;

public class GrantTaskInfo {
    private int taskId;
    private String grantName; //授权任务名称
    private String grantTime;
    private int userid;
    private String operUserName;
    private String begin_time;
    private String end_time;
    private List<Box> openBoxs; //所有开锁编码，客户端运算实现保证一个编码能开多个锁
    private String state;
    private String grantUserName;

    public String getGrantName() {
        return grantName;
    }

    public void setGrantName(String grantName) {
        this.grantName = grantName;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(String begin_time) {
        this.begin_time = begin_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public GrantTaskInfo() {
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public List<Box> getOpenBoxs() {
        return openBoxs;
    }

    public void setOpenBoxs(List<Box> openBoxs) {
        this.openBoxs = openBoxs;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getGrantUserName() {
        return grantUserName;
    }

    public void setGrantUserName(String grantUserName) {
        this.grantUserName = grantUserName;
    }

    public String getOperUserName() {
        return operUserName;
    }

    public void setOperUserName(String operUserName) {
        this.operUserName = operUserName;
    }


    public String getGrantTime() {
        return grantTime;
    }

    public void setGrantTime(String grantTime) {
        this.grantTime = grantTime;
    }

    public static class Box {
        public int boxId;
        public String boxNo;
        public List<String> lockCodes;

        public void addLock(String lock) {
            if (lockCodes == null) {
                lockCodes = new ArrayList<String>();
            }
            lockCodes.add(lock);
        }
    }

}
