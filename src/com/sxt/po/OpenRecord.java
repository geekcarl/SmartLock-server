package com.sxt.po;

import java.util.Date;

public class OpenRecord {

    private String keycode;
    private String lockcode;
    private String opentime;
    private String level = "";

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

    public String getOpentime() {
        return opentime;
    }

    public void setOpentime(String opentime) {
        this.opentime = opentime;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}