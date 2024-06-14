package com.njj.njjsdk.protocol.entity;

/**
 * 公共设置实体类
 * 久坐提醒实体类
 * <p>
 * 饮水提醒也适用
 * </p>
 *
 * @ClassName CommonSetEntity
 * @Description TODO
 * @Author Darcy
 * @Date 2021/4/23 16:43
 * @Version 1.0
 */
public class NjjCommonSetEntity {


    private int sitStatue;
    //提醒频率
    private int notifyRate;

    //开始时间
    private int startTime;
    //结束时间
    private int endTime;



    public NjjCommonSetEntity() {

    }


    public int getSitStatue() {
        return sitStatue;
    }

    public void setSitStatue(int sitStatue) {
        this.sitStatue = sitStatue;
    }

    public int getNotifyRate() {
        return notifyRate;
    }

    public void setNotifyRate(int notifyRate) {
        this.notifyRate = notifyRate;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }



}
