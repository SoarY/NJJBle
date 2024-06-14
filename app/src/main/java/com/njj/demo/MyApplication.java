package com.njj.demo;

import android.app.Application;

import com.njj.njjsdk.manger.NJJOtaManage;
import com.njj.njjsdk.utils.ApplicationProxy;

/**
 * @ClassName MyApplication
 * @Description TODO
 * @Author Darcy
 * @Date 2022/8/1 11:29
 * @Version 1.0
 */
public class MyApplication extends Application {
    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        NJJOtaManage.getInstance().init(this);
        ApplicationProxy.getInstance().setApplication(this);
    }

    public static synchronized MyApplication getInstance() {
        if (mInstance == null) {
            mInstance = new MyApplication();
        }
        return mInstance;
    }
}
