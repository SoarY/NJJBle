package com.njj.njjsdk.protocol.entity;


/**
 * 同步天气的相关指令
 *
 * @ClassName SyncWeatherData
 * @Description TODO
 * @Author Darcy
 * @Date 2021/7/31 11:36
 * @Version 1.0
 */
public class NjjSyncWeatherData {

    // 0x00:多云，
    // 0x01:晴天 ，
    // 0x02：雪天，
    // 0x03：雨天，
    // 0x04：阴天，
    // 0x05：沙尘，
    // 0x06：风，
    // 0x7：雾霾

    private int weatherType;//天气类型

    private int tempData;//温度值

    public int getWeatherType() {
        return weatherType;
    }

    public void setWeatherType(int weatherType) {
        this.weatherType = weatherType;
    }



    public int getTempData() {
        return tempData;
    }

    public void setTempData(int tempData) {
        this.tempData = tempData;
    }



}
