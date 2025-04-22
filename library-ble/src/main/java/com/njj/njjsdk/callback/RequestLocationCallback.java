package com.njj.njjsdk.callback;

public class RequestLocationCallback {
    public interface ICallBack{
        void onReceiveData();
    }

    /**
     * 接收到找手机数据
     */
    public static void onReceiveData() {
        CallBackManager.getInstance().runOnUIThread(() -> {
            for (ICallBack callBack : CallBackManager.getInstance().getRequestLocationCallback()) {
                callBack.onReceiveData();
            }
        });
    }
}
