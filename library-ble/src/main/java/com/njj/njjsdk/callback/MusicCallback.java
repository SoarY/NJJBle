package com.njj.njjsdk.callback;

public class MusicCallback {

    public interface ICallBack{
        void onPause();
        void onResume();
        void onNext();
        void onPrevious();
        void onVolume(int volume);
    }

    public static void onPause() {
        CallBackManager.getInstance().runOnUIThread(() -> {
            for (ICallBack callBack : CallBackManager.getInstance().getMusicCallback()) {
                callBack.onPause();
            }
        });
    }
    public static void onResume() {
        CallBackManager.getInstance().runOnUIThread(() -> {
            for (ICallBack callBack : CallBackManager.getInstance().getMusicCallback()) {
                callBack.onResume();
            }
        });
    }
    public static void onNext() {
        CallBackManager.getInstance().runOnUIThread(() -> {
            for (ICallBack callBack : CallBackManager.getInstance().getMusicCallback()) {
                callBack.onNext();
            }
        });
    }
    public static void onPrevious() {
        CallBackManager.getInstance().runOnUIThread(() -> {
            for (ICallBack callBack : CallBackManager.getInstance().getMusicCallback()) {
                callBack.onPrevious();
            }
        });
    }

    public static void onVolume(int volume) {
        CallBackManager.getInstance().runOnUIThread(() -> {
            for (ICallBack callBack : CallBackManager.getInstance().getMusicCallback()) {
                callBack.onVolume(volume);
            }
        });
    }
}
