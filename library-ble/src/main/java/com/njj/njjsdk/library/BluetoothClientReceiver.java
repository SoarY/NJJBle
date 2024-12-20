package com.njj.njjsdk.library;

import com.njj.njjsdk.library.connect.listener.BleConnectStatusListener;
import com.njj.njjsdk.library.connect.response.BleNotifyResponse;
import com.njj.njjsdk.library.receiver.listener.BluetoothBondListener;
import com.njj.njjsdk.library.connect.listener.BluetoothStateListener;

import java.util.HashMap;
import java.util.List;

/**
 * Created by liwentian on 2017/1/13.
 */

public class BluetoothClientReceiver {

    private HashMap<String, HashMap<String, List<BleNotifyResponse>>> mNotifyResponses;
    private HashMap<String, List<BleConnectStatusListener>> mConnectStatusListeners;
    private List<BluetoothStateListener> mBluetoothStateListeners;
    private List<BluetoothBondListener> mBluetoothBondListeners;
}
