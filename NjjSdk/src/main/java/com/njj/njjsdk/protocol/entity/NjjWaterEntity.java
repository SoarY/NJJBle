package com.njj.njjsdk.protocol.entity;

/**
 * @ClassName DrinkWaterEntity
 * @Description TODO
 * @Author LibinFan
 * @Date 2022/11/3 14:03
 * @Version 1.0
 */
public class NjjWaterEntity {
    private int  drinkWaterStatus;
    private int drinkWaterInterval;
    private int drinkWaterBeginTime;
    private int drinkWaterEndTime;

    public int getDrinkWaterStatus() {
        return drinkWaterStatus;
    }

    public void setDrinkWaterStatus(int drinkWaterStatus) {
        this.drinkWaterStatus = drinkWaterStatus;
    }

    public int getDrinkWaterInterval() {
        return drinkWaterInterval;
    }

    public void setDrinkWaterInterval(int drinkWaterInterval) {
        this.drinkWaterInterval = drinkWaterInterval;
    }

    public int getDrinkWaterBeginTime() {
        return drinkWaterBeginTime;
    }

    public void setDrinkWaterBeginTime(int drinkWaterBeginTime) {
        this.drinkWaterBeginTime = drinkWaterBeginTime;
    }

    public int getDrinkWaterEndTime() {
        return drinkWaterEndTime;
    }

    public void setDrinkWaterEndTime(int drinkWaterEndTime) {
        this.drinkWaterEndTime = drinkWaterEndTime;
    }
}
