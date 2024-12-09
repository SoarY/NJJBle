package com.njj.njjsdk.callback;

public class NjjStockCallback {

    public interface ICallBack{
        void onReceiveData();
    }


    public static void onReceiveData() {
        CallBackManager.getInstance().runOnUIThread(() -> {
            for (NjjStockCallback.ICallBack callBack : CallBackManager.getInstance().getStockCallback()) {
                callBack.onReceiveData();
            }
        });
    }
}
