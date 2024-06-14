package com.njj.njjsdk.utils;

import android.app.Application;
import android.content.Context;

/**
 * @ClassName ApplicationProxy
 * @Description TODO
 * @Author LibinFan
 * @Date 2022/10/8 14:16
 * @Version 1.0
 */
public class ApplicationProxy {
    private static Application mApp;

    public void setApplication(Application app) {
        if (null == app) {
            throw new IllegalArgumentException("The app can not be null!");
        }

        mApp = app;
    }

    public Application getApplication() {
        if (null == mApp) {
            throw new IllegalArgumentException("The app is null, need call setApplication in Host*Application attachBase_Fun");
        }
        return mApp;
    }

    public Context getApplicationContext() {
        if (null == mApp) {
            throw new IllegalArgumentException("The app is null, need call setApplication in Host*Application attachBase_Fun");
        }
        return mApp.getApplicationContext();
    }

    public static ApplicationProxy getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static final ApplicationProxy instance = new ApplicationProxy();
    }


}
