package com.njj.njjsdk.callback;

import android.os.Handler;
import android.os.Looper;



import java.util.ArrayList;
import java.util.List;


public final class CallBackManager {

    /**
     * 回调集合
     */

    //蓝牙连接状态连接回调
    private List<ConnectStatuesCallBack.ICallBack> mConnectStatuesCallBackList = new ArrayList<>();


    private List<SomatosensoryGameCallback.ICallBack> mSomatosensoryGameCallbackList = new ArrayList<>();

    //设备MAC3.0信息回调
    private List<Mac3CallBack.ICallBack> mMac3CallBackList = new ArrayList<>();

    private final List<GPSCallback.ICallBack> mGPSCallback = new ArrayList<>();

    private final List<MusicAndBookCallback.ICallBack> mMusicAndBookCallback=new ArrayList<>();

    private final List<SDCardCallback.ICallBack> mSDCardCallbackCallback=new ArrayList<>();

    private final List<NjjStockCallback.ICallBack> mStockCallback=new ArrayList<>();

    private final List<RecordingDataCallback.ICallBack> mRecordingDataCallback=new ArrayList<>();

    private final List<RequestLocationCallback.ICallBack> mRequestLocationCallback=new ArrayList<>();

    private final List<MusicCallback.ICallBack> mMusicCallback=new ArrayList<>();

    /**
     * 单例
     */
    private static CallBackManager instance;

    private CallBackManager() {
    }

    public static CallBackManager getInstance() {
        if (instance == null) {
            instance = new CallBackManager();
        }
        return instance;
    }


    /**
     * 在主线程中进行回调
     */
    private Handler mHandler = new Handler(Looper.getMainLooper());

    void runOnUIThread(Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
        } else {
            mHandler.post(runnable);
        }
    }

    /************** 连接状态监听 **************/

    public void registerConnectStatuesCallBack(String mac, ConnectStatuesCallBack.ICallBack callBack) {
        if (!mConnectStatuesCallBackList.contains(callBack) && callBack != null) {
            mConnectStatuesCallBackList.add(callBack);
        }

    }

    public void unregisterConnectStatuesCallBack(String mac, ConnectStatuesCallBack.ICallBack callBack) {
        if (mConnectStatuesCallBackList.contains(callBack) && callBack != null) {
            mConnectStatuesCallBackList.remove(callBack);
        }

    }

    public List<ConnectStatuesCallBack.ICallBack> getConnectStatuesCallBackList() {
        return mConnectStatuesCallBackList;
    }


    /************** 体感游戏数据监听 **************/
    public void registerSomatosensoryGameCallback(SomatosensoryGameCallback.ICallBack callBack) {
        if (!mSomatosensoryGameCallbackList.contains(callBack) && callBack != null) {
            mSomatosensoryGameCallbackList.add(callBack);
        }
    }

    public void unregisterSomatosensoryGameCallback(SomatosensoryGameCallback.ICallBack callBack) {
        if (mSomatosensoryGameCallbackList.contains(callBack) && callBack != null) {
            mSomatosensoryGameCallbackList.remove(callBack);
        }
    }

    public List<SomatosensoryGameCallback.ICallBack> getSomatosensoryGameCallback() {
        return mSomatosensoryGameCallbackList;
    }

    /************** 设备3.0mac地址信息数据监听 **************/
    public void registerMac3CallBack(Mac3CallBack.ICallBack callBack) {
        if (!mMac3CallBackList.contains(callBack) && callBack != null) {
            mMac3CallBackList.add(callBack);
        }
    }

    public void unregisterMac3CallBack(Mac3CallBack.ICallBack callBack) {
        if (mMac3CallBackList.contains(callBack) && callBack != null) {
            mMac3CallBackList.remove(callBack);
        }
    }

    public List<Mac3CallBack.ICallBack> getMac3CallBackList() {
        return mMac3CallBackList;
    }


    /****************************************GPS回调**************************************/
    public void registerGPSCallbackCallBack(GPSCallback.ICallBack callBack) {
        if (!mGPSCallback.contains(callBack) && callBack != null) {
            mGPSCallback.add(callBack);
        }
    }

    public void unregisterGPSCallback(GPSCallback.ICallBack callBack) {
        if (mGPSCallback.contains(callBack) && callBack != null) {
            mGPSCallback.remove(callBack);
        }
    }

    public List<GPSCallback.ICallBack> getGPSCallback() {
        return mGPSCallback;
    }



    public void registerStockCallback(NjjStockCallback.ICallBack callBack) {
        if (!mStockCallback.contains(callBack) && callBack != null) {
            mStockCallback.add(callBack);
        }
    }

    public void unregisterStockCallback(NjjStockCallback.ICallBack callBack) {
        if (!mStockCallback.contains(callBack) && callBack != null) {
            mStockCallback.remove(callBack);
        }
    }

    public void registerMusicAndBookCallback(MusicAndBookCallback.ICallBack callBack) {
        if (!mMusicAndBookCallback.contains(callBack) && callBack != null) {
            mMusicAndBookCallback.add(callBack);
        }
    }

    public void unregisterMusicAndBookCallback(MusicAndBookCallback.ICallBack callBack) {
        if (mMusicAndBookCallback.contains(callBack) && callBack != null) {
            mMusicAndBookCallback.remove(callBack);
        }
    }

    public List<MusicAndBookCallback.ICallBack> getMusicAndBookCallback() {
        return mMusicAndBookCallback;
    }

    public void registerSDCardCallbackCallback(SDCardCallback.ICallBack callBack) {
        if (!mSDCardCallbackCallback.contains(callBack) && callBack != null) {
            mSDCardCallbackCallback.add(callBack);
        }
    }

    public void unregisterSDCardCallbackCallback(SDCardCallback.ICallBack callBack) {
        if (mSDCardCallbackCallback.contains(callBack) && callBack != null) {
            mSDCardCallbackCallback.remove(callBack);
        }
    }

    public List<SDCardCallback.ICallBack> getSDCardCallbackCallback() {
        return mSDCardCallbackCallback;
    }

    public List<NjjStockCallback.ICallBack> getStockCallback() {
        return mStockCallback;
    }

    /****************************************实时录音回调*************************************/
    public void registerRecordingDataCallback(RecordingDataCallback.ICallBack callBack) {
        if (!mRecordingDataCallback.contains(callBack) && callBack != null) {
            mRecordingDataCallback.add(callBack);
        }
    }

    public void unregisterRecordingDataCallback(RecordingDataCallback.ICallBack callBack) {
        if (mRecordingDataCallback.contains(callBack) && callBack != null) {
            mRecordingDataCallback.remove(callBack);
        }
    }

    public List<RecordingDataCallback.ICallBack> getRecordingDataCallback() {
        return mRecordingDataCallback;
    }

    public void registerRequestLocationCallback(RequestLocationCallback.ICallBack callBack) {
        if (!mRequestLocationCallback.contains(callBack) && callBack != null) {
            mRequestLocationCallback.add(callBack);
        }
    }

    public void unregisterRequestLocationCallback(RequestLocationCallback.ICallBack callBack) {
        if (mRequestLocationCallback.contains(callBack) && callBack != null) {
            mRequestLocationCallback.remove(callBack);
        }
    }

    public List<RequestLocationCallback.ICallBack> getRequestLocationCallback() {
        return mRequestLocationCallback;
    }

    public void registerMusicCallback(MusicCallback.ICallBack callBack) {
        if (!mMusicCallback.contains(callBack) && callBack != null) {
            mMusicCallback.add(callBack);
        }
    }

    public void unregisterMusicCallback(MusicCallback.ICallBack callBack) {
        if (!mMusicCallback.contains(callBack) && callBack != null) {
            mMusicCallback.remove(callBack);
        }
    }

    public List<MusicCallback.ICallBack> getMusicCallback() {
        return mMusicCallback;
    }
}
