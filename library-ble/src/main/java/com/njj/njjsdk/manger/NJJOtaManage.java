package com.njj.njjsdk.manger;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;


import com.njj.njjsdk.callback.NjjOtaCallBack;
import com.realsil.sdk.core.RtkConfigure;
import com.realsil.sdk.core.RtkCore;
import com.realsil.sdk.dfu.DfuConstants;
import com.realsil.sdk.dfu.RtkDfu;
import com.realsil.sdk.dfu.model.DfuConfig;
import com.realsil.sdk.dfu.model.DfuProgressInfo;
import com.realsil.sdk.dfu.model.OtaDeviceInfo;
import com.realsil.sdk.dfu.model.OtaModeInfo;
import com.realsil.sdk.dfu.model.Throughput;
import com.realsil.sdk.dfu.utils.ConnectParams;
import com.realsil.sdk.dfu.utils.DfuAdapter;
import com.realsil.sdk.dfu.utils.GattDfuAdapter;


import static com.njj.njjsdk.utils.LogUtil.isDebug;

/**
 * @ClassName RuiYuSdkManage
 * @Description TODO
 * @Author Darcy
 * @Date 2022/5/19 17:11
 * @Version 1.0
 */
public class NJJOtaManage {

    private static NJJOtaManage instance;
    private Context context;
    private GattDfuAdapter mDfuAdapter;
    private String mac;
    private boolean isOtaMode;
    private DfuConfig mDfuConfig;

    private NjjOtaCallBack otaCallBack;

    public static NJJOtaManage getInstance() {
        if (null == instance) {
            synchronized (NJJOtaManage.class) {
                if (null == instance) {
                    instance = new NJJOtaManage();
                }
            }
        }
        return instance;
    }

    //初始瑞昱相关的SDK
    public void init(Application context) {
        RtkConfigure configure = new RtkConfigure.Builder().debugEnabled(isDebug)
                .printLog(isDebug)
                .logTag("OTA")
                .build();
        this.context = context;
        RtkCore.initialize(context, configure);
        RtkDfu.initialize(context, isDebug);
        mDfuAdapter = GattDfuAdapter.getInstance(context);
        mDfuAdapter.initialize(mDfuAdapterCallback);
        mDfuConfig = new DfuConfig();
        mDfuConfig.setChannelType(DfuConfig.CHANNEL_TYPE_GATT);
//
        OtaModeInfo modeInfo = mDfuAdapter.getPriorityWorkMode(DfuConstants.OTA_MODE_SILENT_FUNCTION);
        mDfuConfig.setOtaWorkMode(modeInfo.getWorkmode());

        // enable battery level check feature
//            mDfuConfig.setBatteryCheckEnabled(true);
        // set the threshold value
//            mDfuConfig.setLowBatteryThreshold(30);

//            mDfuConfig.setVersionCheckEnabled(false);
    }

//    /**
//     * 连接设备
//     *
//     * @param mac m
//     * @return void
//     * @date 2021-10-25 14:52:38
//     */
//    public void connectDevice(String mac) {
//        if (TextUtils.isEmpty(mac)) {
//            mac = NjjBleManger.getInstance().getmBleDevice().ge();
//        }
//        this.mac = mac;
////        LogUtil.d("瑞昱设备,开始连接...");
//        ConnectParams.Builder connectParamsBuilder =  new ConnectParams.Builder()
//                .address(mac)
//                .createBond(true)
//                .reconnectTimes(10)
//                .localName(mDfuConfig.getLocalName());
//        if (!isOtaMode) {
//            mDfuAdapter.connectDevice(connectParamsBuilder.build());
//        }
//    }


    private DfuAdapter.DfuHelperCallback mDfuAdapterCallback = new DfuAdapter.DfuHelperCallback() {
        @Override
        public void onStateChanged(int state) {
            super.onStateChanged(state);
            if (state == DfuAdapter.STATE_INIT_OK) {
               /* otaCallBack.onProgressChanged(0);
                connectDevice(mDfuConfig.getAddress());*/
            } else if (state == DfuAdapter.STATE_PREPARED) {

            } else if (state == DfuAdapter.STATE_DISCONNECTED || state ==
                    DfuAdapter.STATE_CONNECT_FAILED) {
                // indicates connection disconnected
                // TODO ...
                if (otaCallBack != null) {
                    otaCallBack.onError(21, 21);
                }
            }

        }


        @Override
        public void onError(int type, int code) {
            super.onError(type, code);
            if (otaCallBack != null) {
                otaCallBack.onError(type, code);
            }

        }

        @Override
        public void onProcessStateChanged(int state, Throughput throughput) {
            super.onProcessStateChanged(state, throughput);
            if (state == DfuConstants.PROGRESS_IMAGE_ACTIVE_SUCCESS) {
                isOtaMode = false;
                if (otaCallBack != null) {
                    otaCallBack.onSuccess();
                }

            } else if (state == DfuConstants.STATE_DISCOVER_SERVICE) {
                if (otaCallBack != null) {
                    otaCallBack.onProgressChanged(0);
                }

            }
        }

        @Override
        public void onProgressChanged(DfuProgressInfo dfuProgressInfo) {
            super.onProgressChanged(dfuProgressInfo);
            if (dfuProgressInfo != null) {
                if (otaCallBack != null) {
                    otaCallBack.onProgressChanged(dfuProgressInfo.getProgress());
                }

            }
        }

    };

    /**
     * @param mac            蓝牙Mac地址
     * @param path           升级固件本地地址
     * @param njjOtaCallBack 结果回调
     */
    public void dfuUpdate(String mac, String path, NjjOtaCallBack njjOtaCallBack) {
        otaCallBack = njjOtaCallBack;
        DfuConfig mDfuConfig = new DfuConfig();
        mDfuConfig.setChannelType(DfuConfig.CHANNEL_TYPE_GATT);
        mDfuConfig.setVersionCheckEnabled(false);
        mDfuConfig.setAddress(mac);
        mDfuConfig.setFilePath(path);
//        mDfuConfig.setBufferCheckMtuUpdateEnabled();
//        mDfuConfig.setIcCheckEnabled(false);
        OtaDeviceInfo otaDeviceInfo = new OtaDeviceInfo();
        if (mDfuAdapter == null) {
            RtkConfigure configure = new RtkConfigure.Builder().debugEnabled(isDebug)
                    .printLog(isDebug)
                    .logTag("OTA")
                    .build();

            RtkCore.initialize(context, configure);
            RtkDfu.initialize(context, isDebug);
            mDfuAdapter = GattDfuAdapter.getInstance(context);
            mDfuAdapter.initialize(mDfuAdapterCallback);
            mDfuConfig = new DfuConfig();
            mDfuConfig.setChannelType(DfuConfig.CHANNEL_TYPE_GATT);
//
            OtaModeInfo modeInfo = mDfuAdapter.getPriorityWorkMode(DfuConstants.OTA_MODE_SILENT_FUNCTION);
            mDfuConfig.setOtaWorkMode(modeInfo.getWorkmode());
        }

        OtaModeInfo modeInfo = mDfuAdapter.getPriorityWorkMode(DfuConstants.OTA_MODE_SILENT_FUNCTION);
        mDfuConfig.setOtaWorkMode(modeInfo.getWorkmode());
        mDfuAdapter.startOtaProcedure(otaDeviceInfo, mDfuConfig);
    }


}
