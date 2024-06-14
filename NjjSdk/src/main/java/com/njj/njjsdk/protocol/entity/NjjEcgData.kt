package com.njj.njjsdk.protocol.entity

/**
 * @ClassName EcgData
 * @Description TODO
 * @Date 2022/7/22 10:31
 * @Version 1.0
 */
 class NjjEcgData : Any(){
    var ecgItems: MutableList<Int>?=null
    //心电测量时间
    var testTimeMill=0
    var ecgval: String?=null
    var timeMill=0
}