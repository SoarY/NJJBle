package com.njj.njjsdk.protocol.entity

/**
 * @ClassName BloodPressure
 * @Description TODO
 * @Date 2022/7/22 11:23
 * @Version 1.0
 */
 class NjjBloodPressure : Any(){
    var timeMill:Int=0
    var systolicPressure //收缩压-->高压
            : String?=null
    var diastolicPressure //舒张压
            : String?=null
}