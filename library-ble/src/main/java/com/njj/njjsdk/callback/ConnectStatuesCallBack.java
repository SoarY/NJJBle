package com.njj.njjsdk.callback;

/**
 * 蓝牙连接状态回调
 * <p>
 * <p>
 * 包含
 * </p>
 *
 * @ClassName ConnectStatuesCallBack
 * @Description TODO
 * @Author Darcy
 * @Date 2021/4/15 19:25
 * @Version 1.0
 */
public class ConnectStatuesCallBack {

    public interface ICallBack {
        /**
         * 连接
         */
        void onConnected(String mac);


        /**
         * 正在连接
         */
        void onConnecting(String mac);

        /**
         * 连接断开
         */
        void onDisConnected(String mac);

        /**
         * 发现服务
         * @param code
         */
        void onDiscoveredServices(int code,String mac);

        void onConnectFail(String mac);
    }


    /**
     * 已经连接
     */
    public static void onConnected(String mac) {
        CallBackManager.getInstance().runOnUIThread(() -> {
            for (ICallBack callBack : CallBackManager.getInstance().getConnectStatuesCallBackList()) {
                callBack.onConnected(mac);
            }
        });
    }


    /**
     * 已经断开连接
     */
    public static void onConnectFail(String mac) {
        CallBackManager.getInstance().runOnUIThread(() -> {
            for (ICallBack callBack : CallBackManager.getInstance().getConnectStatuesCallBackList()) {
                callBack.onConnectFail(mac);
            }
        });
    }

    public static void onDisConnected(String mac) {
        CallBackManager.getInstance().runOnUIThread(() -> {
            for (ICallBack callBack : CallBackManager.getInstance().getConnectStatuesCallBackList()) {
                callBack.onDisConnected(mac);
            }
        });
    }

    /**
     * 正在连接
     */
    public static void onConnecting(String mac) {
        CallBackManager.getInstance().runOnUIThread(() -> {
            for (ICallBack callBack : CallBackManager.getInstance().getConnectStatuesCallBackList()) {
                callBack.onConnecting(mac);
            }
        });
    }

    /**
     * 正在连接
     */
    public static void onDiscoveredServices(int code, String macAddress) {
        CallBackManager.getInstance().runOnUIThread(() -> {
            for (ICallBack callBack : CallBackManager.getInstance().getConnectStatuesCallBackList()) {
                callBack.onDiscoveredServices(code,macAddress);
            }
        });
    }
}
