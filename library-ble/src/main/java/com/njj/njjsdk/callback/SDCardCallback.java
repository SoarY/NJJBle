package com.njj.njjsdk.callback;

/**
 * @ClassName HandOnCallback
 * @Description TODO
 * @Author Darcy
 * @Date 2022/8/2 15:55
 * @Version 1.0
 */
public class SDCardCallback {
    public interface ICallBack{
        void onReceiveData(int size);
    }

    /**
     * 接收到找手机数据
     */
    public static void onReceiveData(int size) {
        CallBackManager.getInstance().runOnUIThread(() -> {
            for (ICallBack callBack : CallBackManager.getInstance().getSDCardCallbackCallback()) {
                callBack.onReceiveData(size);
            }
        });
    }
}
