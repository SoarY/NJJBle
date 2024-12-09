package com.njj.njjsdk.protocol.entity;


/**
 * // 0x00:多云，
 *     // 0x01:晴天 ，
 *     // 0x02：雪天，
 *     // 0x03：雨天，
 *     // 0x04：阴天，
 *     // 0x05：沙尘，
 *     // 0x06：风，
 *     // 0x7：雾霾
 */
public class NJJWeatherData {



    private int week;

    private int weatherType;//天气类型
    // 0x00：零度以上
    // 0x01：零度以下
    private int tempLimit;//温度限制

    private int tempData;//温度值

    private int highestTemp;//最高温度

    private int minimumTemp;//最低温度

    private String pressure;

    private String ultLevel;

    private int humidity;

    private int windDirDay;

    private String windScaleDay;

    private int vis;

    /**
     * 预报当天总降水量，默认单位：毫米
     */
    private int precip;


    public int getWindDirDay() {
        return windDirDay;
    }

    public void setWindDirDay(int windDirDay) {
        this.windDirDay = windDirDay;
    }

    public String getWindScaleDay() {
        return windScaleDay;
    }

    public void setWindScaleDay(String windScaleDay) {
        this.windScaleDay = windScaleDay;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getVis() {
        return vis;
    }

    public void setVis(int vis) {
        this.vis = vis;
    }

    public int getPrecip() {
        return precip;
    }

    public void setPrecip(int precip) {
        this.precip = precip;
    }

    public int getWeatherType() {
        return weatherType;
    }

    public void setWeatherType(int weatherType) {
        this.weatherType = weatherType;
    }

    public int getTempLimit() {
        return tempLimit;
    }

    public void setTempLimit(int tempLimit) {
        this.tempLimit = tempLimit;
    }

    public int getTempData() {
        return tempData;
    }

    public void setTempData(int tempData) {
        this.tempData = tempData;
    }

    public int getHighestTemp() {
        return highestTemp;
    }

    public void setHighestTemp(int highestTemp) {
        this.highestTemp = highestTemp;
    }

    public int getMinimumTemp() {
        return minimumTemp;
    }

    public void setMinimumTemp(int minimumTemp) {
        this.minimumTemp = minimumTemp;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getUltLevel() {
        return ultLevel;
    }

    public void setUltLevel(String ultLevel) {
        this.ultLevel = ultLevel;
    }
}
