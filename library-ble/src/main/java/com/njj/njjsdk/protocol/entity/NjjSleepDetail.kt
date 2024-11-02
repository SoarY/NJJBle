package com.njj.njjsdk.protocol.entity

class NjjSleepDetail : Any() {
    var timeMill = 0         //时间戳
    var status = 0           //睡眠类型  0 清醒 1 浅睡 2 深睡
    var startTime: Long = 0 //开始时间
    var endTime: Long = 0   //结束时间
    var duration = 0        //睡眠时长

    companion object {
        const val SLEEP_TYPE_WAKE = 0
        const val SLEEP_TYPE_LIGHT = 1
        const val SLEEP_TYPE_DEEP = 2
    }
}