package com.njj.njjsdk.protocol.entity;


import java.util.List;

/**
 * @ClassName BleDeviceFun
 * @Description TODO
 * @Author LibinFan
 * @Date 2022/10/24 10:56
 * @Version 1.0
 */
public class BleDeviceFun {
    //屏幕高
    private int lcdHeight;
    //屏幕宽
    private int lcdWidth;
    //预览图高
    private int previewHeight;
    //预览图宽
    private int previewWidth;

    //0 :方屏 1 :圆屏
    private int lcdType;

    private int lcdRadian;
    //是否支持视频表盘
    private boolean isVideo;

    //自定义表盘结构版本
    private boolean isNoTime;
    //是否支持自定义表盘
    private boolean isSupportCustomDial;
    //是否支持表盘市场
    private boolean supportDialLibrary;
    //是否支持蓝牙通话
    private boolean supportBtPhone;
    //是否支持查找手机
    private boolean supportFindPhone;
    //是否支持查找手表
    private boolean supportFindWatch;
    //是否支持心率            0:不支持  bit1:全天 bit2:7天  （1 表示支持  0 表示不支持）
    private int supportHr;
    //是否支持血压            0:不支持  bit1:全天 bit2:7天  （1 表示支持  0 表示不支持）
    private int supportBp;
    //是否支持血氧            0:不支持  bit1:全天 bit2:7天  （1 表示支持  0 表示不支持）
    private int supportBo2;
    //是否支持心电            0:不支持  bit1:全天 bit2:7天  （1 表示支持  0 表示不支持）
    private int supportEcg;
    //是否支持体温            0:不支持  bit1:全天 bit2:7天  （1 表示支持  0 表示不支持）
    private int supportTemp;
    /**
     * :不支持 非0:最多闹钟个数
     */
    private int supportAlarm;
    /**
     * 0:不支持  非0:最多日程个数
     */
    private int supportSchedule;

    //是否支持久坐
    private boolean supportLongSit;
    //是否支持喝水
    private boolean supportDrinkWater;
    ////是否支持洗手
    private boolean supportWashHand;
    //是否支持天气
    private boolean supportWeather;
    //是否支持女性健康
    private boolean supportWomenHealth;
    ////是否支持吃药提醒        1:支持 0:不支持
    private boolean supportMedic;
    //是否支持核酸码
    private boolean healthQrcode;
    //是否支持GPS运动         1:支持 0:不支持
    private boolean supportGpsSport;
    //0:不支持 1:支持并未安装  3:支持并已安装
    private List<Integer> games;

    public boolean isSupportGpsSport() {
        return supportGpsSport;
    }

    public void setSupportGpsSport(boolean supportGpsSport) {
        this.supportGpsSport = supportGpsSport;
    }

    public int getLcdHeight() {
        return lcdHeight;
    }

    public void setLcdHeight(int lcdHeight) {
        this.lcdHeight = lcdHeight;
    }

    public int getLcdWidth() {
        return lcdWidth;
    }

    public void setLcdWidth(int lcdWidth) {
        this.lcdWidth = lcdWidth;
    }

    public int getPreviewHeight() {
        return previewHeight;
    }

    public void setPreviewHeight(int previewHeight) {
        this.previewHeight = previewHeight;
    }

    public int getPreviewWidth() {
        return previewWidth;
    }

    public void setPreviewWidth(int previewWidth) {
        this.previewWidth = previewWidth;
    }

    public int getLcdType() {
        return lcdType;
    }

    public void setLcdType(int lcdType) {
        this.lcdType = lcdType;
    }

    public int getLcdRadian() {
        return lcdRadian;
    }

    public void setLcdRadian(int lcdRadian) {
        this.lcdRadian = lcdRadian;
    }

    public boolean isVideo() {
        return isVideo;
    }

    public void setVideo(boolean video) {
        isVideo = video;
    }

    public boolean isNoTime() {
        return isNoTime;
    }

    public void setNoTime(boolean noTime) {
        isNoTime = noTime;
    }

    public boolean isSupportCustomDial() {
        return isSupportCustomDial;
    }

    public void setSupportCustomDial(boolean supportCustomDial) {
        isSupportCustomDial = supportCustomDial;
    }

    public boolean isSupportDialLibrary() {
        return supportDialLibrary;
    }

    public void setSupportDialLibrary(boolean supportDialLibrary) {
        this.supportDialLibrary = supportDialLibrary;
    }

    public boolean isSupportBtPhone() {
        return supportBtPhone;
    }

    public void setSupportBtPhone(boolean supportBtPhone) {
        this.supportBtPhone = supportBtPhone;
    }

    public boolean isSupportFindPhone() {
        return supportFindPhone;
    }

    public void setSupportFindPhone(boolean supportFindPhone) {
        this.supportFindPhone = supportFindPhone;
    }

    public boolean isSupportFindWatch() {
        return supportFindWatch;
    }

    public void setSupportFindWatch(boolean supportFindWatch) {
        this.supportFindWatch = supportFindWatch;
    }

    public int getSupportHr() {
        return supportHr;
    }

    public void setSupportHr(int supportHr) {
        this.supportHr = supportHr;
    }

    public int getSupportBp() {
        return supportBp;
    }

    public void setSupportBp(int supportBp) {
        this.supportBp = supportBp;
    }

    public int getSupportBo2() {
        return supportBo2;
    }

    public void setSupportBo2(int supportBo2) {
        this.supportBo2 = supportBo2;
    }

    public int getSupportEcg() {
        return supportEcg;
    }

    public void setSupportEcg(int supportEcg) {
        this.supportEcg = supportEcg;
    }

    public int getSupportTemp() {
        return supportTemp;
    }

    public void setSupportTemp(int supportTemp) {
        this.supportTemp = supportTemp;
    }

    public int getSupportAlarm() {
        return supportAlarm;
    }

    public void setSupportAlarm(int supportAlarm) {
        this.supportAlarm = supportAlarm;
    }

    public int getSupportSchedule() {
        return supportSchedule;
    }

    public void setSupportSchedule(int supportSchedule) {
        this.supportSchedule = supportSchedule;
    }

    public boolean isSupportLongSit() {
        return supportLongSit;
    }

    public void setSupportLongSit(boolean supportLongSit) {
        this.supportLongSit = supportLongSit;
    }

    public boolean isSupportDrinkWater() {
        return supportDrinkWater;
    }

    public void setSupportDrinkWater(boolean supportDrinkWater) {
        this.supportDrinkWater = supportDrinkWater;
    }

    public boolean isSupportWashHand() {
        return supportWashHand;
    }

    public void setSupportWashHand(boolean supportWashHand) {
        this.supportWashHand = supportWashHand;
    }

    public boolean isSupportWeather() {
        return supportWeather;
    }

    public void setSupportWeather(boolean supportWeather) {
        this.supportWeather = supportWeather;
    }

    public boolean isSupportWomenHealth() {
        return supportWomenHealth;
    }

    public void setSupportWomenHealth(boolean supportWomenHealth) {
        this.supportWomenHealth = supportWomenHealth;
    }

    public boolean isSupportMedic() {
        return supportMedic;
    }

    public void setSupportMedic(boolean supportMedic) {
        this.supportMedic = supportMedic;
    }

    public boolean isHealthQrcode() {
        return healthQrcode;
    }

    public void setHealthQrcode(boolean healthQrcode) {
        this.healthQrcode = healthQrcode;
    }

    public List<Integer> getGames() {
        return games;
    }

    public void setGames(List<Integer> games) {
        this.games = games;
    }

    @Override
    public String toString() {
        return "BleDeviceFun{" +
                "lcdHeight=" + lcdHeight +
                ", lcdWidth=" + lcdWidth +
                ", previewHeight=" + previewHeight +
                ", previewWidth=" + previewWidth +
                ", lcdType=" + lcdType +
                ", lcdRadian=" + lcdRadian +
                ", isVideo=" + isVideo +
                ", isNoTime=" + isNoTime +
                ", isSupportCustomDial=" + isSupportCustomDial +
                ", supportDialLibrary=" + supportDialLibrary +
                ", supportBtPhone=" + supportBtPhone +
                ", supportFindPhone=" + supportFindPhone +
                ", supportFindWatch=" + supportFindWatch +
                ", supportHr=" + supportHr +
                ", supportBp=" + supportBp +
                ", supportBo2=" + supportBo2 +
                ", supportEcg=" + supportEcg +
                ", supportTemp=" + supportTemp +
                ", supportAlarm=" + supportAlarm +
                ", supportSchedule=" + supportSchedule +
                ", supportLongSit=" + supportLongSit +
                ", supportDrinkWater=" + supportDrinkWater +
                ", supportWashHand=" + supportWashHand +
                ", supportWeather=" + supportWeather +
                ", supportWomenHealth=" + supportWomenHealth +
                ", supportMedic=" + supportMedic +
                ", healthQrcode=" + healthQrcode +
                ", supportGpsSport=" + supportGpsSport +
                ", games=" + games +
                '}';
    }
}
