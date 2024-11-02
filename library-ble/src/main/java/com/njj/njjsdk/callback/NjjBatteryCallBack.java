package com.njj.njjsdk.callback;

/**
 * 电池电量回调
 *
 * @ClassName BatteryCallBack
 * @Description TODO
 * @Author Darcy
 * @Date 2021/4/12 11:58
 * @Version 1.0
 */
public interface NjjBatteryCallBack {
    void onSuccess(int batteryLevel);

    void onFail();
}
