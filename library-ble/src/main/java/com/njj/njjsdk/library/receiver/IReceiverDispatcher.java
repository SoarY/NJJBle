package com.njj.njjsdk.library.receiver;

import com.njj.njjsdk.library.receiver.listener.BluetoothReceiverListener;

import java.util.List;

/**
 * Created by dingjikerbo on 16/11/26.
 */

public interface IReceiverDispatcher {

    List<BluetoothReceiverListener> getListeners(Class<?> clazz);
}
