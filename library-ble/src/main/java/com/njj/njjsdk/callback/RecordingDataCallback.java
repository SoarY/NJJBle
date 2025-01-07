package com.njj.njjsdk.callback;


import com.njj.njjsdk.entity.RecordingDataEntity;

public class RecordingDataCallback {
    public interface ICallBack {
        void onReceiveData(RecordingDataEntity recordingDataEntity);

    }

    /**
     * 接收到找手机数据
     */
    public static void onReceiveData(RecordingDataEntity recordingDataEntity) {
        CallBackManager.getInstance().runOnUIThread(() -> {
            for (ICallBack callBack : CallBackManager.getInstance().getRecordingDataCallback()) {
                callBack.onReceiveData(recordingDataEntity);
            }
        });
    }
}
