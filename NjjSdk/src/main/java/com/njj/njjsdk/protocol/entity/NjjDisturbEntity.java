package com.njj.njjsdk.protocol.entity;

/**
 * @ClassName DisturbEntity
 * @Description TODO
 * @Author LibinFan
 * @Date 2022/11/3 14:03
 * @Version 1.0
 */
public class NjjDisturbEntity {
    private int  disturbStatus;
    private int disturbInterval;
    private int disturbBeginTime;
    private int disturbEndTime;

    public int getDisturbStatus() {
        return disturbStatus;
    }

    public void setDisturbStatus(int disturbStatus) {
        this.disturbStatus = disturbStatus;
    }

    public int getDisturbInterval() {
        return disturbInterval;
    }

    public void setDisturbInterval(int disturbInterval) {
        this.disturbInterval = disturbInterval;
    }

    public int getDisturbBeginTime() {
        return disturbBeginTime;
    }

    public void setDisturbBeginTime(int disturbBeginTime) {
        this.disturbBeginTime = disturbBeginTime;
    }

    public int getDisturbEndTime() {
        return disturbEndTime;
    }

    public void setDisturbEndTime(int disturbEndTime) {
        this.disturbEndTime = disturbEndTime;
    }

    @Override
    public String toString() {
        return "NjjDisturbEntity{" +
                "disturbStatus=" + disturbStatus +
                ", disturbInterval=" + disturbInterval +
                ", disturbBeginTime=" + disturbBeginTime +
                ", disturbEndTime=" + disturbEndTime +
                '}';
    }
}
