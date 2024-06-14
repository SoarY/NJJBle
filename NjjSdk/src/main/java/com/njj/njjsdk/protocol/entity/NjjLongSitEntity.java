package com.njj.njjsdk.protocol.entity;

/**
 * @ClassName LongSitEntity
 * @Description TODO
 * @Author LibinFan
 * @Date 2022/11/3 14:03
 * @Version 1.0
 */
public class NjjLongSitEntity {
    private int  longSitStatus;
    private int longSitInterval;
    private int longSitBeginTime;
    private int longSitEndTime;

    public int getLongSitStatus() {
        return longSitStatus;
    }

    public void setLongSitStatus(int longSitStatus) {
        this.longSitStatus = longSitStatus;
    }

    public int getLongSitInterval() {
        return longSitInterval;
    }

    public void setLongSitInterval(int longSitInterval) {
        this.longSitInterval = longSitInterval;
    }

    public int getLongSitBeginTime() {
        return longSitBeginTime;
    }

    public void setLongSitBeginTime(int longSitBeginTime) {
        this.longSitBeginTime = longSitBeginTime;
    }

    public int getLongSitEndTime() {
        return longSitEndTime;
    }

    public void setLongSitEndTime(int longSitEndTime) {
        this.longSitEndTime = longSitEndTime;
    }

    @Override
    public String toString() {
        return "NjjLongSitEntity{" +
                "longSitStatus=" + longSitStatus +
                ", longSitInterval=" + longSitInterval +
                ", longSitBeginTime=" + longSitBeginTime +
                ", longSitEndTime=" + longSitEndTime +
                '}';
    }
}
