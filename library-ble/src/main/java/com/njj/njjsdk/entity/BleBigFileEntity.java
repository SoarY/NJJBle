package com.njj.njjsdk.entity;

public class BleBigFileEntity {
    private int curId; //该条列表所在id
    private int totalId; //总列表数量
    private String name;
    private int time;

    public int getCurId() {
        return curId;
    }

    public void setCurId(int curId) {
        this.curId = curId;
    }

    public int getTotalId() {
        return totalId;
    }

    public void setTotalId(int totalId) {
        this.totalId = totalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "BleBigFileEntity{" +
                "curId=" + curId +
                ", totalId=" + totalId +
                ", name='" + name + '\'' +
                ", time=" + time +
                '}';
    }
}
