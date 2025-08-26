package com.njj.njjsdk.entity;

import java.io.Serializable;

public class ScheduleBean implements Serializable {
    private String title;
    //    <string name="str_starting">开始时</string>
    //    <string name="str_15forward">提前15分钟</string>
    //    <string name="str_30forward">提前30分钟</string>
    //    <string name="str_60forward">提前1小时</string>
    //    <string name="str_2_hour_forward">提前2小时</string>
    //    <string name="str_1_day_forward">提前一天</string>
    //    <string name="no_remid">不提醒</string>
    private int type;
    private String details;
    private long startTime;

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
}
