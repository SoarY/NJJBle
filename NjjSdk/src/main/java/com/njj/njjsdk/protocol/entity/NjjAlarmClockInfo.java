package com.njj.njjsdk.protocol.entity;

import java.util.Arrays;

/**
 * 闹钟实体类
 *
 * @ClassName AlarmClockInfo
 * @Description TODO
 * @Author Darcy
 * @Date 2021/4/24 14:43
 * @Version 1.0
 */
public class NjjAlarmClockInfo {

    private String alarmFlag;//闹钟唯一标识

    private int alarmTime;//闹钟时间（00:00）,以秒的形式存储

    //闹钟周期，十进制数保存，使用二进制的1表示开，0表示关，
    //0,0,0,1,0,0,0
    private int alarmCycle;

    private boolean alarmState;//闹钟状态

    //是否被删除
    private boolean deleteFlag;

    //闹钟类型
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }



    public NjjAlarmClockInfo() {

    }

    public NjjAlarmClockInfo(String flag, int time, int cycle, boolean state, String des, boolean deleteFlag) {
        this.alarmFlag = flag;
        this.alarmTime = time;
        this.alarmCycle = cycle;
        this.alarmState = state;
        this.deleteFlag = deleteFlag;

    }

    public NjjAlarmClockInfo(String flag, int time, int cycle, boolean state, boolean deleteFlag, int type) {
        this.alarmFlag = flag;
        this.alarmTime = time;
        this.alarmCycle = cycle;
        this.alarmState = state;
        this.deleteFlag = deleteFlag;
        this.type = type;
    }

    public String getAlarmFlag() {
        return alarmFlag;
    }

    public void setAlarmFlag(String alarmFlag) {
        this.alarmFlag = alarmFlag;
    }

    public int getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(int alarmTime) {
        this.alarmTime = alarmTime;
    }

    public int getAlarmCycle() {
        return alarmCycle;
    }

    public void setAlarmCycle(int alarmCycle) {
        this.alarmCycle = alarmCycle;
    }

    public boolean isAlarmState() {
        return alarmState;
    }

    public void setAlarmState(boolean alarmState) {
        this.alarmState = alarmState;
    }



    @Override
    public String toString() {
        return "AlarmClockInfo{" +
                "alarmFlag='" + alarmFlag + '\'' +
                ", alarmTime=" + alarmTime +
                ", alarmCycle=" + alarmCycle +

                ", alarmState=" + alarmState +

                ", deleteFlag=" + deleteFlag +
                ", type=" + type +
                '}';
    }
}
