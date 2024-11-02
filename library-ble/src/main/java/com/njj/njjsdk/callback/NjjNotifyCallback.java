package com.njj.njjsdk.callback;

import com.njj.njjsdk.protocol.entity.NjjStepData;

/**
 * @ClassName SingleHeartOxBloodCallback
 * @Description TODO
 * @Author Darcy
 * @Date 2022/7/26 14:04
 * @Version 1.0
 */
 public interface NjjNotifyCallback {
    void onBloodPressureData(  int systolicPressure,int diastolicPressure);
    void onHeartRateData( int rate);
    void onOxyData(int rate);

    void takePhone(int value);

    void onStepData(NjjStepData njjStepData);

    void findPhone(int value);

   void endCallPhone(int value);
}
