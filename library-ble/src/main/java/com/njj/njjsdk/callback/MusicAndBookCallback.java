package com.njj.njjsdk.callback;


import com.njj.njjsdk.entity.BleBigFileEntity;

public class MusicAndBookCallback {
    public interface ICallBack{
        void onReceivedData(int type, BleBigFileEntity bleBigFileEntity);
    }

    /**
     * 接收到找手机数据
     */
    public static void onReceivedData(int type, BleBigFileEntity bleBigFileEntity) {
        CallBackManager.getInstance().runOnUIThread(() -> {
            for (ICallBack callBack : CallBackManager.getInstance().getMusicAndBookCallback()) {
                callBack.onReceivedData(type,bleBigFileEntity);
            }
        });
    }
}
