package com.njj.njjsdk.protocol.entity;

/**
 * @ClassName drinkWaterEntity
 * @Description TODO
 * @Author LibinFan
 * @Date 2022/11/3 14:03
 * @Version 1.0
 */
public class NjjWristScreenEntity {
    private int  wristScreenStatus;
    private int wristScreenInterval;
    private int wristScreenBeginTime;
    private int wristScreenEndTime;

    public int getWristScreenStatus() {
        return wristScreenStatus;
    }

    public void setWristScreenStatus(int wristScreenStatus) {
        this.wristScreenStatus = wristScreenStatus;
    }

    public int getWristScreenInterval() {
        return wristScreenInterval;
    }

    public void setWristScreenInterval(int wristScreenInterval) {
        this.wristScreenInterval = wristScreenInterval;
    }

    public int getWristScreenBeginTime() {
        return wristScreenBeginTime;
    }

    public void setWristScreenBeginTime(int wristScreenBeginTime) {
        this.wristScreenBeginTime = wristScreenBeginTime;
    }

    public int getWristScreenEndTime() {
        return wristScreenEndTime;
    }

    public void setWristScreenEndTime(int wristScreenEndTime) {
        this.wristScreenEndTime = wristScreenEndTime;
    }

    @Override
    public String toString() {
        return "NjjWristScreenEntity{" +
                "wristScreenStatus=" + wristScreenStatus +
                ", wristScreenInterval=" + wristScreenInterval +
                ", wristScreenBeginTime=" + wristScreenBeginTime +
                ", wristScreenEndTime=" + wristScreenEndTime +
                '}';
    }
}
