package com.sxt.po;

public class DoorEventLog {
    private String keycode;
    private String lockcode;
    private String operTime;
    private int operType;
    private int operUserId;

    public String getKeycode() {
        return keycode;
    }

    public void setKeycode(String keycode) {
        this.keycode = keycode;
    }

    public String getLockcode() {
        return lockcode;
    }

    public void setLockcode(String lockcode) {
        this.lockcode = lockcode;
    }

    public String getOperTime() {
        return operTime;
    }

    public void setOperTime(String operTime) {
        this.operTime = operTime;
    }

    public int getOperType() {
        return operType;
    }

    public void setOperType(int operType) {
        this.operType = operType;
    }

    public int getOperUserId() {
        return operUserId;
    }

    public void setOperUserId(int operUserId) {
        this.operUserId = operUserId;
    }

    @Override
    public String toString() {
        return "DoorEventLog{" +
                "keycode='" + keycode + '\'' +
                ", lockcode='" + lockcode + '\'' +
                ", operTime='" + operTime + '\'' +
                ", operType=" + operType +
                ", operUserId=" + operUserId +
                '}';
    }
}
