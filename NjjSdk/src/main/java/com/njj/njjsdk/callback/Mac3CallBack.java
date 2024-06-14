package com.njj.njjsdk.callback;

/**
 * 设备信息回调
 *
 * @ClassName BatteryCallBack
 * @Description TODO
 * @Author Darcy
 * @Date 2021/4/12 11:58
 * @Version 1.0
 */
public class Mac3CallBack {
    public interface ICallBack {

        void onSuccess(String mac);

        void onFail();
    }

    /**
     * 获取到设备信息
     *
     * @param mac 3.0mac地址
     */
    public static void onSuccess(String mac) {
        CallBackManager.getInstance().runOnUIThread(() -> {
            for (ICallBack callBack : CallBackManager.getInstance().getMac3CallBackList()) {
                callBack.onSuccess(mac);
            }
        });
    }

    /**
     * 发送失败
     */
    public static void onFail() {
        CallBackManager.getInstance().runOnUIThread(() -> {
            for (ICallBack callBack : CallBackManager.getInstance().getMac3CallBackList()) {
                callBack.onFail();
            }
        });
    }
}
