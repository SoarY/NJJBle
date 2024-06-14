package com.njj.njjsdk.utils;

import android.content.Context;


/**
 * 这个类主要用来存储和蓝牙相关的数据
 *
 * <p>
 * 后期扩展使用的本地初始化数据
 * </p>
 *
 * @ClassName BLESDKLocalData
 * @Description TODO
 * @Author Darcy
 * @Date 2021/4/16 14:31
 * @Version 1.0
 */
public class BLESDKLocalData extends SharedPreferencesUtil {
    //是否回连
    private static final String KEY_IS_CONNECT = "KEY_IS_CONNECT";
    private static final String KEY_MAC = "key_mac";//蓝牙mac地址

    @Override
    protected String getName() {
        return "BLESDKLocalData";
    }

    private static BLESDKLocalData instance;

    private BLESDKLocalData() {
    }

    public static BLESDKLocalData getInstance() {
        if (instance == null) {
            instance = new BLESDKLocalData();
        }
        return instance;
    }

    @Override
    public void init(Context context) {
        super.init(context);
        //BLESDKLocalData.getInstance().setLastDeviceMac("");
    }

    /************ 是否回连 ******************/
    @Deprecated
    public void setReconnectFlag(boolean isReConnectFlag) {
        putValue(KEY_IS_CONNECT, isReConnectFlag);
    }

    @Deprecated
    public boolean isReConnectFlag() {
        return getValue(KEY_IS_CONNECT, false);
    }

    /************ 蓝牙的mac地址 ******************/
    public void setMacAddress(String macAddress) {
        putValue(KEY_MAC, macAddress);
    }

    public String getMacAddress() {
        return getValue(KEY_MAC, "");
    }



}
