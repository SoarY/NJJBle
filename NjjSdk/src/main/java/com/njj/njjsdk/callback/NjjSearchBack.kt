package com.njj.njjsdk.callback

import com.njj.njjsdk.protocol.entity.BLEDevice

/**
 * @ClassName NjjECGCallBack
 * @Description TODO
 * @Author
 * @Date 2022/7/29 14:55
 * @Version 1.0
 */
interface NjjSearchBack {

    fun onSearchStarted()

    fun onDeviceFounded(device: BLEDevice?)

    fun onSearchStopped()

    fun onSearchCanceled()
}