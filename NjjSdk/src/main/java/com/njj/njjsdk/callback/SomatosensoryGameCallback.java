package com.njj.njjsdk.callback;


import com.njj.njjsdk.protocol.entity.SomatosensoryGame;

/**
 * 控制播放音量的回调
 *
 * @ClassName AudioCallback
 * @Description TODO
 * @Author Darcy
 * @Date 2021/11/23 13:47
 * @Version 1.0
 */
public class SomatosensoryGameCallback {

    public interface ICallBack {
        void onReceiveData(SomatosensoryGame somatosensoryGame);
        void onReceiveStatus(int status);
    }


    public static void onReceiveData(SomatosensoryGame somatosensoryGame) {
        CallBackManager.getInstance().runOnUIThread(() -> {
            for (ICallBack callBack : CallBackManager.getInstance().getSomatosensoryGameCallback()) {
                callBack.onReceiveData(somatosensoryGame);
            }
        });
    }



    public static void onReceiveStatus(int  status) {
        CallBackManager.getInstance().runOnUIThread(() -> {
            for (ICallBack callBack : CallBackManager.getInstance().getSomatosensoryGameCallback()) {
                callBack.onReceiveStatus(status);
            }
        });
    }
}
