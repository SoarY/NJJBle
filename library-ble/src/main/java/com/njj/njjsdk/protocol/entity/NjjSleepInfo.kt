package com.njj.njjsdk.protocol.entity

/**
 * 睡眠数据详情数据
 *
 * @ClassName SleepInfo
 * @Description TODO
 * @Author Darcy
 * @Date 2022/7/20 19:26
 * @Version 1.0
 */
class NjjSleepInfo : Any() {
    var timeMill = 0     //时间戳（标记）

    var date: String? = null //日期 yyyy-MM-dd

    var beginTime = 0       // 入睡时间 时间戳

    var endTime = 0         // 醒来时间 时间戳

    var sleepTime = 0        // 睡眠时长（深睡时长 + 浅睡时长） 单位 分钟

    var deepSleepTime = 0    // 深睡时长 单位 分钟

    var lightSleepTime = 0   //浅睡时长 单位 分钟

    var weekTimes = 0        //清醒时长 单位 分钟 （一般无用）

    lateinit var sleepDetailList: ArrayList<NjjSleepDetail> //(睡眠详情数据)

}