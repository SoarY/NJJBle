package com.njj.njjsdk.callback

import com.njj.njjsdk.protocol.entity.*

/**
 * @ClassName NjjECGCallBack
 * @Description TODO
 * @Author
 * @Date 2022/7/29 14:55
 * @Version 1.0
 */
interface NjjConfig1CallBack {

    /**
     * 写入成功
     */
    fun onWriteSuccess();

    /**
     * 获取久坐提醒数据
     */
    fun onLongSitEntity(njjLongSitEntity: NjjLongSitEntity)

    /**
     * 获取饮水提醒数据
     */
    fun onNjjDrinkWaterEntity(njjDrinkWaterEntity: NjjDrinkWaterEntity)

    /**
     * 获取洗手提醒数据
     */
    fun onNjjWashHandEntity(njjWashHandEntity: NjjWashHandEntity)

    /**
     * 获取抬腕亮屏提醒数据
     */
    fun onNjjWristScreenEntity(njjWristScreenEntity: NjjWristScreenEntity)

    /**
     * 获取勿扰提醒数据
     */
    fun onNjjDisturbEntity(njjDisturbEntity: NjjDisturbEntity)

    /**
     * 获取吃药提醒数据
     */
    fun onNjjMedicineEntity(njjMedicineEntity: NjjMedicineEntity)

    /**
     * 获取失败
     */
    fun onFail()

}