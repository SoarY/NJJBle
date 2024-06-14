package com.njj.njjsdk.callback

/**
 * @ClassName NjjFirmwareCallback
 * @Description TODO
 * @Date 2022/7/25 16:07
 * @Version 1.0
 */
interface NjjFirmwareCallback {
    fun onFirmwareSuccess(firmware:String)

    fun onFirmwareFail()
}