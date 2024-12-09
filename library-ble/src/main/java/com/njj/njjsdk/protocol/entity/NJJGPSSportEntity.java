package com.njj.njjsdk.protocol.entity;

/**
 * @ClassName GPSSportEntity
 * @Description TODO
 * @Author LibinFan
 * @Date 2022/11/11 14:26
 * @Version 1.0
 */
public class NJJGPSSportEntity {

    int sportHr;        //心率  只用于接收固件回复使用
    int sportValid;     //数据是否有效 0无效 1 有效  每隔一秒钟需要发送一次数据
    int sportType;      // 运动类型
    int sportTime;		//时间	（秒）
    int sportSteps;		//步数
    int sportKcal;		//卡路里（千卡）
    int sportDistance;	//距离	（米）
    int sportSpeed;		//公里 	（小时）
    int sportCadence;   //步频
    int sportStride;   //步幅

    public int getSportHr() {
        return sportHr;
    }

    public void setSportHr(int sportHr) {
        this.sportHr = sportHr;
    }

    public int getSportValid() {
        return sportValid;
    }

    public void setSportValid(int sportValid) {
        this.sportValid = sportValid;
    }

    public int getSportTime() {
        return sportTime;
    }

    public void setSportTime(int sportTime) {
        this.sportTime = sportTime;
    }

    public int getSportSteps() {
        return sportSteps;
    }

    public void setSportSteps(int sportSteps) {
        this.sportSteps = sportSteps;
    }

    public int getSportKcal() {
        return sportKcal;
    }

    public void setSportKcal(int sportKcal) {
        this.sportKcal = sportKcal;
    }

    public int getSportDistance() {
        return sportDistance;
    }

    public void setSportDistance(int sportDistance) {
        this.sportDistance = sportDistance;
    }

    public int getSportSpeed() {
        return sportSpeed;
    }

    public void setSportSpeed(int sportSpeed) {
        this.sportSpeed = sportSpeed;
    }

    public int getSportType() {
        return sportType;
    }

    public void setSportType(int sportType) {
        this.sportType = sportType;
    }

    public int getSportCadence() {
        return sportCadence;
    }

    public void setSportCadence(int sportCadence) {
        this.sportCadence = sportCadence;
    }

    public int getSportStride() {
        return sportStride;
    }

    public void setSportStride(int sportStride) {
        this.sportStride = sportStride;
    }

    @Override
    public String toString() {
        return "GPSSportEntity{" +
                "sportHr=" + sportHr +
                ", sportValid=" + sportValid +
                ", sportType=" + sportType +
                ", sportTime=" + sportTime +
                ", sportSteps=" + sportSteps +
                ", sportKcal=" + sportKcal +
                ", sportDistance=" + sportDistance +
                ", sportSpeed=" + sportSpeed +
                ", sportCadence=" + sportCadence +
                ", sportStride=" + sportStride +
                '}';
    }
}
