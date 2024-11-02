package com.njj.njjsdk.callback

/**
 * @ClassName CallBackDfu
 * @Description TODO
 * @Author  Darcy
 * @Date 2022/7/28 17:23
 * @Version 1.0
 */
interface NjjOtaCallBack {
    fun onSuccess()

    fun onError(errorType: Int, errorCode: Int)

    fun onProgressChanged(progress: Int)

}