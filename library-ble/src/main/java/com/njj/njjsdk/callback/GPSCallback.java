package com.njj.njjsdk.callback;


import com.njj.njjsdk.protocol.entity.NJJGPSSportEntity;

/**
 * 控制播放音量的回调
 *
 * @ClassName AudioCallback
 * @Description TODO
 * @Author Darcy
 * @Date 2021/11/23 13:47
 * @Version 1.0
 */
public class GPSCallback {

    public interface ICallBack {
        void onGPSPermission();
        void onGPSCountdown(int sportId);
        void onGPSStart(int sportId);
        void onGPSSync(NJJGPSSportEntity gpsSportEntity);
        void onGPSPause(int sportId);
        void onGPSContinue(int sportId);
        void onGPSEnd(int sportId);
    }


    public static void onGPSPermission() {
        CallBackManager.getInstance().runOnUIThread(() -> {
            for (ICallBack callBack : CallBackManager.getInstance().getGPSCallback()) {
                callBack.onGPSPermission();
            }
        });
    }

    public static void onGPSCountdown(int sportId) {
        CallBackManager.getInstance().runOnUIThread(() -> {
            for (ICallBack callBack : CallBackManager.getInstance().getGPSCallback()) {
                callBack.onGPSCountdown(sportId);
            }
        });
    }

    public static void onGPSStart(int sportId) {
        CallBackManager.getInstance().runOnUIThread(() -> {
            for (ICallBack callBack : CallBackManager.getInstance().getGPSCallback()) {
                callBack.onGPSStart( sportId);
            }
        });
    }

    public static void onGPSSync(NJJGPSSportEntity gpsSportEntity) {
        CallBackManager.getInstance().runOnUIThread(() -> {
            for (ICallBack callBack : CallBackManager.getInstance().getGPSCallback()) {
                callBack.onGPSSync(gpsSportEntity);
            }
        });
    }

    public static void onGPSPause(int sportId) {
        CallBackManager.getInstance().runOnUIThread(() -> {
            for (ICallBack callBack : CallBackManager.getInstance().getGPSCallback()) {
                callBack.onGPSPause(sportId);
            }
        });
    }

    public static void onGPSContinue(int sportId) {
        CallBackManager.getInstance().runOnUIThread(() -> {
            for (ICallBack callBack : CallBackManager.getInstance().getGPSCallback()) {
                callBack.onGPSContinue(sportId);
            }
        });
    }

    public static void onGPSEnd(int sportId) {
        CallBackManager.getInstance().runOnUIThread(() -> {
            for (ICallBack callBack : CallBackManager.getInstance().getGPSCallback()) {
                callBack.onGPSEnd(sportId);
            }
        });
    }
}
