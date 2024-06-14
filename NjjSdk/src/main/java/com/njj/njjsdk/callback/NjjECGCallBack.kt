package com.njj.njjsdk.callback

/**
 * @ClassName NjjECGCallBack
 * @Description TODO
 * @Author
 * @Date 2022/7/29 14:55
 * @Version 1.0
 */
interface NjjECGCallBack {

    fun onReceivePPGData(type: Int, time: Int, heart: Int)

    fun onECGReceiveEnd(type: Int)

}