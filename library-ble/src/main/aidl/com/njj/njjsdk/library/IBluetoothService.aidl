// IBluetoothManager.aidl
package com.njj.njjsdk.library;

// Declare any non-default types here with import statements

import com.njj.njjsdk.library.IResponse;

interface IBluetoothService {
    void callBluetoothApi(int code, inout Bundle args, IResponse response);
}
