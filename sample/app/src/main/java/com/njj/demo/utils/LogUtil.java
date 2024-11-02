package com.njj.demo.utils;

import android.util.Log;

/**
 * 创建时间：2020/2/1 17: 22
 * 编写人：Darcy
 * 功能描述：日志工具类
 */
public class LogUtil {

    /**
     * 日志标记
     */
    public static String TAG = "LogUtil";

    /**
     * 是否调试模式
     */
    public static boolean isDebug = true;

    public static void setIsDebug(boolean isDebug) {
        LogUtil.isDebug = isDebug;
    }

    public static void d(String msg) {
        if (isDebug) Log.d(TAG, msg);
    }

    public static void i(String msg) {
        if (isDebug) Log.i(TAG, msg);
    }

    public static void w(String msg) {
        if (isDebug) Log.w(TAG, msg);
    }

    public static void e(String msg) {
        if (isDebug) Log.e(TAG, msg);
    }

}
