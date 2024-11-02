package com.njj.njjsdk.callback

/**
 * @ClassName NjjPushOtaCallback
 * @Description TODO
 * @Author  Darcy
 * @Date 2022/7/28 11:43
 * @Version 1.0
 */
interface NjjPushOtaCallback {
    fun onPushStart(pushType: Int)

    fun onPushProgress(progress: Int)

    fun onPushSuccess()

    fun onPushError(data: Int, pack: Int)
}