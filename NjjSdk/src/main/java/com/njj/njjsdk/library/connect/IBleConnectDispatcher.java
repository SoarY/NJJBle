package com.njj.njjsdk.library.connect;

import com.njj.njjsdk.library.connect.request.BleRequest;

public interface IBleConnectDispatcher {

    void onRequestCompleted(BleRequest request);
}
