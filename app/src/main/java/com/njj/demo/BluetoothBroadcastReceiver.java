package com.njj.demo;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.njj.njjsdk.utils.LogUtil;

/**
 * @ClassName BluetoothBroadcastReceiver
 * @Description TODO
 * @Author LibinFan
 * @Date 2022/12/20 11:33
 * @Version 1.0
 */

public class BluetoothBroadcastReceiver extends BroadcastReceiver {
    private String TAG = "BluetoothMusicTool";

    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtil.e("intent.action" + intent.getAction());
        switch (intent.getAction()) {
            case BluetoothAdapter.ACTION_STATE_CHANGED:
                if (intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0) == BluetoothAdapter.STATE_ON) {
                    LogUtil.e("open bluetooth");
                } else {
                    LogUtil.e("close bluetooth");
                }
                break;
            case BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED:
                if (intent.getIntExtra(BluetoothAdapter.EXTRA_CONNECTION_STATE, 0)==   BluetoothAdapter.STATE_CONNECTED) {
//                    PreferenceCompanion.setBt()
//                    log("bluetooth connect device===${PreferenceCompanion.getBt()}")
                }else if (intent.getIntExtra(BluetoothAdapter.EXTRA_CONNECTION_STATE, 0)== BluetoothAdapter.STATE_DISCONNECTED){

                    /*PreferenceCompanion.setBt(IBluetoothMusicTool.STATE_DISCONNECT)
                    log("bluetooth disconnect device===${PreferenceCompanion.getBt()}")*/
                }
                break;

        }
    }
}
