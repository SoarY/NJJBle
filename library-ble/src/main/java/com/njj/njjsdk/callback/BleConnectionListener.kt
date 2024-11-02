package com.njj.njjsdk.callback

/**
 * @ClassName BleConnectionListener
 * @Description TODO
 * @Author  LibinFan
 * @Date 2022/10/22 11:48
 * @Version 1.0
 */
interface BleConnectionListener {
    fun onConnectionSuccess()

    fun onConnectionFail()

    fun disConnection()

    fun discoveredServices(code: Int)
}