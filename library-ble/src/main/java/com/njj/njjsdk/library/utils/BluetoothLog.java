package com.njj.njjsdk.library.utils;

import android.util.Log;

import com.njj.njjsdk.utils.LogUtil;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by dingjikerbo on 2015/12/16.
 */
public class BluetoothLog {

    private static final String LOG_TAG = "NJJ";

    /**
     * 是否调试模式
     */
    public static boolean isDebug = true;

    public static void setIsDebug(boolean isDebug) {
        BluetoothLog.isDebug = isDebug;
    }

    public static void i(String msg) {
        if (isDebug) Log.i(LOG_TAG, msg);
    }

    public static void e(String msg) {
        if (isDebug) Log.e(LOG_TAG, msg);
    }

    public static void v(String msg) {
        if (isDebug)
            Log.v(LOG_TAG, msg);
    }

    public static void d(String msg) {
        if (isDebug) Log.d(LOG_TAG, msg);
    }

    public static void w(String msg) {
        if (isDebug) Log.w(LOG_TAG, msg);
    }

    public static void e(Throwable e) {
        e(getThrowableString(e));
    }

    public static void w(Throwable e) {
        w(getThrowableString(e));
    }

    private static String getThrowableString(Throwable e) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);

        while (e != null) {
            e.printStackTrace(printWriter);
            e = e.getCause();
        }

        String text = writer.toString();

        printWriter.close();

        return text;
    }
}
