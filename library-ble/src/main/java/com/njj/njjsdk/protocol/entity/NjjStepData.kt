package com.njj.njjsdk.protocol.entity

/**
 * @ClassName StepDataP
 * @Description TODO
 * @Date 2021/7/22 9:34
 * @Version 1.0
 */
class NjjStepData : Any(){
    var timeMill :Int=0
    //卡路里
    var calData: String?=null
    //时长
    var duration: String?=null
    var stepNum : String?=null
    var distance: String?=null
}