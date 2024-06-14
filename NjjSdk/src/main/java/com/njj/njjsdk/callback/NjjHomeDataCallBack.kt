package com.njj.njjsdk.callback

import com.njj.njjsdk.protocol.entity.*

/**
 * @ClassName NjjHomeDataCallBack
 * @Description TODO
 * @Author  Darcy
 * @Date 2022/7/27 16:22
 * @Version 1.0
 */
interface NjjHomeDataCallBack {
    fun homeDataCallBack(njjEcgData: NjjEcgData,njjHeartData: NjjHeartData,njjStepData: NjjStepData,
                        njjBloodPressure: NjjBloodPressure,njjBloodOxyData: NjjBloodOxyData)
}