package com.njj.njjsdk.protocol.entity

/**
 * @ClassName NjjWeekSportData
 * @Description TODO
 * @Date 2022/7/25 18:24
 * @Version 1.0
 */
class NjjWeekSportData: Any() {
     var timeMill :Long = 0 //时间戳
    var step = 0
    var startTime: Long = 0
    var car:Long=0
    var distancce = 0
    var duration = 0

    lateinit var stepDatas:List<NjjStepData>

}