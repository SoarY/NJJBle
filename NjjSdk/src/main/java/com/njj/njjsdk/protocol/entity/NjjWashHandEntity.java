package com.njj.njjsdk.protocol.entity;

/**
 * @ClassName WashHandEntity
 * @Description TODO
 * @Author LibinFan
 * @Date 2022/11/3 14:03
 * @Version 1.0
 */
public class NjjWashHandEntity {
    private int  washHandStatus;
    private int washHandInterval;
    private int washHandBeginTime;
    private int washHandEndTime;

    public int getWashHandStatus() {
        return washHandStatus;
    }

    public void setWashHandStatus(int washHandStatus) {
        this.washHandStatus = washHandStatus;
    }

    public int getWashHandInterval() {
        return washHandInterval;
    }

    public void setWashHandInterval(int washHandInterval) {
        this.washHandInterval = washHandInterval;
    }

    public int getWashHandBeginTime() {
        return washHandBeginTime;
    }

    public void setWashHandBeginTime(int washHandBeginTime) {
        this.washHandBeginTime = washHandBeginTime;
    }

    public int getWashHandEndTime() {
        return washHandEndTime;
    }

    public void setWashHandEndTime(int washHandEndTime) {
        this.washHandEndTime = washHandEndTime;
    }

    @Override
    public String toString() {
        return "NjjWashHandEntity{" +
                "washHandStatus=" + washHandStatus +
                ", washHandInterval=" + washHandInterval +
                ", washHandBeginTime=" + washHandBeginTime +
                ", washHandEndTime=" + washHandEndTime +
                '}';
    }
}
