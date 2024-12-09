package com.njj.njjsdk.manger;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;


import com.njj.njjsdk.callback.NjjPushOtaCallback;
import com.njj.njjsdk.library.Constants;

import com.njj.njjsdk.protocol.cmd.cmd.CmdMergeImpl;
import com.njj.njjsdk.protocol.entity.BLEDevice;
import com.njj.njjsdk.protocol.entity.EmergencyContact;
import com.njj.njjsdk.protocol.entity.NJJGPSSportEntity;
import com.njj.njjsdk.utils.BleBeaconUtil;
import com.njj.njjsdk.utils.LogUtil;
import com.njj.njjsdk.utils.NJJLog;
import com.njj.njjsdk.utils.NjjUtils;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;




/**
 * 推送瑞昱大文件帮助类
 * 真正的实现逻辑不在这里
 * <p>
 * 包含推送 表盘 ,自定义表盘,联系人
 * </p>
 *
 * @ClassName PushRyDataHelper
 * @Description TODO
 * @Author Darcy
 * @Date 2022/4/19 14:25
 * @Version 1.0
 */
public class NjjPushDataHelper {

    //推送间隔
    private int delayTime = 15;
    //收到开始推送指令，延时0.1秒
    private final int spaceTime = 30;

    private int isAdd = 0; //是否发送修复包
    private int mPkg;  //修复包  包号

    protected boolean isStartPush = false;
    private int type;
    private boolean isBig = false;

    private Timer timer;
    private TimerTask task;
    //将要写入的大文件字节
    private byte[] buffer;
    private int mProgress = 0;
    private int mPosition = 0;


    public byte[] getBuffer() {
        return buffer;
    }

    public void setBuffer(byte[] buffer) {
        this.buffer = buffer;
    }


    public NjjPushDataHelper() {
        BLEDevice bleDevice = NjjBleManger.getInstance().getmBleDevice();
        Map<String, String> beaconMap = BleBeaconUtil.parseData(bleDevice.getScanRecord());
        String id = beaconMap.get("company");
        if (!TextUtils.isEmpty(id)) {
            if (id.startsWith("FF0102")) {
                delayTime = 6;
            }
        }
        isStartPush = false;
    }


    //开始升级前，必须保证字节数组已经加载完成
    public void startPushDial(String path, NJjPushListener pushListener) {
        this.type = 0x01;
        this.pushListener = pushListener;
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NotNull ObservableEmitter<Boolean> emitter) throws Exception {
                try {
                    checkRes(path);
                    emitter.onNext(true);
                } catch (IOException e) {
                    e.printStackTrace();
                    emitter.onError(e);
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    //开始升级前，必须保证字节数组已经加载完成
    public void startUIOTA(String path, NJjPushListener pushListener) {
        this.type = 0x03;
        this.pushListener = pushListener;
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NotNull ObservableEmitter<Boolean> emitter) throws Exception {
                try {
                    checkRes(path);
                    emitter.onNext(true);
                } catch (IOException e) {
                    e.printStackTrace();
                    emitter.onError(e);
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * @param path         文件路径
     * @param pushListener
     */
    public void startPushJieLiOTA(String path, NJjPushListener pushListener) {
        this.type = 0x0A;
        this.pushListener = pushListener;
        Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {
            try {
                checkRes(path);
                emitter.onNext(true);
            } catch (IOException e) {
                e.printStackTrace();
                emitter.onError(e);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    public void startPushJieLiBigOTA(String path, NJjPushListener pushListener) {
        this.type = 0x0A;

      /*  CmdConstKt.EVT_TYPE_OTA_START =206;
        CmdConstKt.EVT_TYPE_OTA_DATA=207;
        CmdConstKt.EVT_TYPE_OTA_END=208;*/

        this.isBig = true;
        this.pushListener = pushListener;
        Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {
            try {
                checkRes(path);
                emitter.onNext(true);
            } catch (IOException e) {
                e.printStackTrace();
                emitter.onError(e);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void startCharacterOTA(String path, NJjPushListener pushListener) {
        this.type = 0x09;
        this.pushListener = pushListener;
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NotNull ObservableEmitter<Boolean> emitter) throws Exception {
                try {
                    checkRes(path);
                    emitter.onNext(true);
                } catch (IOException e) {
                    e.printStackTrace();
                    emitter.onError(e);
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void startLogoOTA(String path, NJjPushListener pushListener) {
        this.type = 0x07;
        this.pushListener = pushListener;
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NotNull ObservableEmitter<Boolean> emitter) throws Exception {
                try {
                    checkRes(path);
                    emitter.onNext(true);
                } catch (IOException e) {
                    e.printStackTrace();
                    emitter.onError(e);
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


    public void startFile(String path, int type, NJjPushListener pushListener) {
        this.type = type;
        this.pushListener = pushListener;
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NotNull ObservableEmitter<Boolean> emitter) throws Exception {
                try {
                    checkRes(path);
                    emitter.onNext(true);
                } catch (IOException e) {
                    e.printStackTrace();
                    emitter.onError(e);
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void startBigFile(String path, int type, NJjPushListener pushListener) {
        this.type = type;
        isBig=true;
        this.pushListener = pushListener;
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NotNull ObservableEmitter<Boolean> emitter) throws Exception {
                try {
                    checkRes(path);
                    emitter.onNext(true);
                } catch (IOException e) {
                    e.printStackTrace();
                    emitter.onError(e);
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 推送自定义表盘
     *
     * @param mUri            表盘地址
     * @param bigWidth        正常图 宽度
     * @param bigHeight       正常图 高度
     * @param smallNeedWidth  缩略图宽度
     * @param smallNeedHeight 缩略图高度
     * @param position        位置 0上 1下
     * @param colors          颜色
     * @param pushListener    监听推送进度
     */
    public void starPushCustomDial(String mUri, int bigWidth, int bigHeight, int smallNeedWidth,
                                   int smallNeedHeight, int position, String colors,
                                   NJjPushListener pushListener) {
        this.type = 0x01;
        this.pushListener = pushListener;
//        pushListener.onPushStart();
        Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {
            checkCustomDial(mUri, bigWidth, bigHeight, smallNeedWidth, smallNeedHeight, position, colors);
            emitter.onNext(true);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 推送联系人
     *
     * @param contactList  联系人列表
     * @param pushListener 推送监听
     */
    public void startPushContactDial(List<EmergencyContact> contactList, NJjPushListener pushListener) {
        NJJLog.d("推送联系人");
        this.type = 0x02;
        this.pushListener = pushListener;
        Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {
            setContactBuffer(contactList);
            emitter.onNext(true);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    Observer<Boolean> observer = new Observer<Boolean>() {
        @Override
        public void onSubscribe(@NotNull Disposable d) {

        }

        @Override
        public void onNext(@NotNull Boolean aBoolean) {
            if (aBoolean) {
                if (isBig){
                    NjjProtocolHelper.getInstance().startSendSuperBigData(type, buffer, new NjjPushOtaCallback() {
                        @Override
                        public void onPushError(int data, int pack) {
                            handPushError(data, pack);
                        }

                        @Override
                        public void onPushSuccess() {
                            LogUtil.e("ry onPushSuccess");
                            if (isStartPush) {
                                handPushSuccess();
                            }

                        }

                        @Override
                        public void onPushProgress(int position) {
                            if (type != 0x02) {
                                HandProgress(position);
                            }
                        }

                        @Override
                        public void onPushStart(int pushType) {
                            if (!isStartPush) {
                                isStartPush = true;
                                checkStartType();
                                if (pushListener != null)
                                    pushListener.onPushStart();
                            }

                        }
                    });
                }else {
                    NjjProtocolHelper.getInstance().startSendBigData(type, buffer, new NjjPushOtaCallback() {
                        @Override
                        public void onPushError(int data, int pack) {
                            handPushError(data, pack);
                        }

                        @Override
                        public void onPushSuccess() {
                            LogUtil.e("ry onPushSuccess");
                            if (isStartPush) {
                                handPushSuccess();
                            }

                        }

                        @Override
                        public void onPushProgress(int position) {
                            if (type != 0x02) {
                                HandProgress(position);
                            }
                        }

                        @Override
                        public void onPushStart(int pushType) {
                            if (!isStartPush) {
                                isStartPush = true;
                                checkStartType();
                                if (pushListener != null)
                                    pushListener.onPushStart();
                            }

                        }
                    });
                }

            } else {
                pushListener.onPushError(9);
            }
        }

        @Override
        public void onError(@NotNull Throwable e) {
            pushListener.onPushError(9);
        }

        @Override
        public void onComplete() {

        }
    };

    private void HandProgress(int position) {
        if (position - mPosition == 1) {
            mPosition = position;
            if (position == buffer.length / 230) {
                LogUtil.e("最后一包");
                isAdd = 1;
                mPkg = position - 1;
                initTimer(1000 * 5, 1000 * 5);
            } else {
                if (getCurrentProgress(position) > mProgress) {
                    mPosition = position;
                    mProgress = getCurrentProgress(position);
                    if (pushListener != null)
                        pushListener.onPushProgress(mProgress);
                }
            }
        }
    }

    public int getCurrentProgress(int progress) {
        if (null == buffer) {
            LogUtil.e("getCurrentProgress 大文件的buffer还没有初始化完成.....");
            return 0;
        }
        double maxSize = buffer.length / 230;
        return (int) (100 * (progress / maxSize));
    }

    /**
     * 发送成功推送结束包
     */
    private void handPushSuccess() {
//        NjjBleManger.getInstance().getBluetoothClient().refreshCache();
        NjjBleManger.getInstance().getBluetoothClient().clearRequest(NjjBleManger.getInstance().getmBleDevice().getDevice().getAddress(), Constants.REQUEST_WRITE);
        LogUtil.e("ry 大文件写入成功");
        if (pushListener != null)
            pushListener.onPushSuccess();
        destroyTimer();

    }

    //    OTA_TYPE_SUCCESS       = 0,//升级成功
//    OTA_TYPE_FAIL_1		= 1,//上一包序号与下一包序号不连续
//    OTA_TYPE_FAIL_2		= 2,//多次写入，均失败
//    OTA_TYPE_FAIL_3		= 3,//未接收到开始指令，就接收数据
//    OTA_TYPE_FAIL_4		= 4,//超时时间内未正确接收到数据
//    OTA_TYPE_FAIL_5		= 5,//主动结束升级
//    OTA_TYPE_REPAIR		= 6,//修复包id序号
//    OTA_TYPE_FAIL_7		= 7,//表盘太大，无法升级
//    8                       //本机推送失败
    private void handPushError(int data, int pack) {
        if (data == 1) {   //补包
            NjjBleManger.getInstance().getBluetoothClient().clearRequest(NjjBleManger.getInstance().getmBleDevice().getDevice().getAddress(), Constants.REQUEST_WRITE);
            isAdd = 1;
            mPkg = pack;
            initTimer(0, 2000);
        } else if (data == 6) {
            isAdd = 0;  //表示 不需要发送修复包
            initTimer(spaceTime, delayTime);
        } else {
            if (pushListener != null)
                pushListener.onPushError(data);
            destroyTimer();
        }

    }

    private void startPushData() {
        byte[] bytes;
        if (isBig) {
            bytes = CmdMergeImpl.INSTANCE.sendDialBigData(isAdd, mPkg);
        } else {
            bytes = CmdMergeImpl.INSTANCE.sendDialData(isAdd, mPkg);
        }

        if (null != bytes) {
            if (isBig) {
                NjjProtocolHelper.getInstance().startBigDailRuiYu(bytes);
            } else {
                NjjProtocolHelper.getInstance().startDailRuiYu(bytes);
            }

        }
    }


    //这里才是真正开始写表盘文件
    private synchronized void checkStartType() {
        initTimer(spaceTime, delayTime);
    }

    private void initTimer(int tSpaceTime, int tDelayTime) {
        if (task != null) {
            task.cancel();
            task = null;
        }

        task = new TimerTask() {
            @Override
            public void run() {
                startPushData();
            }
        };

        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        timer = new Timer();
        timer.schedule(task, tSpaceTime, tDelayTime);
    }

    private void destroyTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (task != null) {
            task.cancel();
            task = null;
        }

        //将要写入的大文件字节
        buffer = null;
        isStartPush = false;
        isAdd = 0;
        mProgress = 0;
        mPosition = 0;
        pushListener = null;
    }

    public void destroyHelper() {
        if (isStartPush) {
            byte[] end;
            if (isBig){
                end = CmdMergeImpl.INSTANCE.endSendBigDialData(0x01);
            }else {
                end = CmdMergeImpl.INSTANCE.endSendDialData(0x01);
            }
            NjjBleManger.getInstance().writeData(end);
           /* byte[] end = CmdMergeImpl.INSTANCE.endSendDialData(0x01);
            NjjBleManger.getInstance().writeData(end);*/

        }
        destroyTimer();
    }

    public void startGPSOTA(String url, NJJGPSSportEntity gpsSportEntity, List<Integer> speeds,
                            int extremity, int anaerobic, int aerobic, int burning, int warm,int height,int width,
                            NJjPushListener pushListener) {
        this.type=0x06;
        this.pushListener = pushListener;
        Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {

                    checkGPSDATA(url, speeds, gpsSportEntity, speeds.size(), extremity, anaerobic, aerobic, burning, warm,height,width);
                    emitter.onNext(true);
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private void checkGPSDATA(String url, List<Integer> speeds, NJJGPSSportEntity gpsSportEntity, int valid, int extremity,
                              int anaerobic, int aerobic, int burning, int warm, int height, int width) {


/*        DeviceAdapterBean deviceAdapterBean = BleDataCache.getInstance().getDeviceAdapterBean();
        needWidth = deviceAdapterBean.getWidth();
        needHeight = deviceAdapterBean.getHeight();*/

        Bitmap bitmap = NjjUtils.getSmallBitmap(
                BitmapFactory.decodeFile(url),
                width, height);

        byte[] gpsData = CmdMergeImpl.INSTANCE.getGPSData(gpsSportEntity);
        byte[] speedBytes = CmdMergeImpl.INSTANCE.getOther(speeds, valid, extremity, anaerobic, aerobic, burning, warm);
        byte[] bgbByte = getBufferByBitmap(bitmap);

        buffer = new byte[gpsData.length + speedBytes.length + bgbByte.length];

        System.arraycopy(gpsData, 0, buffer, 0, gpsData.length);
        System.arraycopy(speedBytes, 0, buffer, gpsData.length, speedBytes.length);
        System.arraycopy(bgbByte, 0, buffer, gpsData.length + speedBytes.length, bgbByte.length);

    }


    private void setContactBuffer(List<EmergencyContact> contactList) {
        buffer = new byte[contactList.size() * 2 * 20];
        int num = 0;
        for (int i = 0; i < contactList.size(); i++) {
            EmergencyContact contact = contactList.get(i);
            String contactName = contact.getContactName();
            String phoneNumber = contact.getPhoneNumber();
            try {
                byte[] nBytes = contactName.getBytes("UTF-8");
                byte[] nameBytes = new byte[20];

                if (nBytes.length > 19) {
                    nameBytes[0] = 19;
                    System.arraycopy(nBytes, 0, nameBytes, 1, 19);
                } else {
                    nameBytes[0] = (byte) nBytes.length;
                    System.arraycopy(nBytes, 0, nameBytes, 1, nBytes.length);
                }


                byte[] pBytes = phoneNumber.getBytes("UTF-8");
                byte[] phoneBytes = new byte[20];

                if (pBytes.length > 19) {
                    phoneBytes[0] = 19;
                    System.arraycopy(pBytes, 0, phoneBytes, 1, 19);
                } else {
                    phoneBytes[0] = (byte) pBytes.length;
                    System.arraycopy(pBytes, 0, phoneBytes, 1, pBytes.length);
                }
                System.arraycopy(nameBytes, 0, buffer, 40 * num, nameBytes.length);
                System.arraycopy(phoneBytes, 0, buffer, 40 * num + 20, phoneBytes.length);
                num++;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }

        LogUtil.e(buffer.length + "");


    }


    //N02  4E0002  240*283
    //W57  4E3034  320*380
    //N07  4E3037  240*283
    //N08  4E3038  240*283
    //N09  4E3039  320*380
    //N03  4E3033  240*283
    //N05  4E3035  360*360
    //N05-42  4E3042  360*360
    //N05-43  4E3043  360*360
    //N0A  4E3041  240*283


    /**
     * 自定义表盘数据初始化
     *
     * @param mUri
     * @param bigWidth
     * @param bigHeight
     * @param smallNeedWidth
     * @param smallNeedHeight
     * @param position
     * @param colors
     */
    private void checkCustomDial(String mUri, int bigWidth, int bigHeight,
                                 int smallNeedWidth, int smallNeedHeight, int position, String colors) {

        Bitmap bitmap1 = BitmapFactory.decodeFile(mUri);
        Bitmap smallBitmap = NjjUtils.getSmallBitmap(bitmap1, smallNeedWidth, smallNeedHeight);

        Bitmap bitmap = NjjUtils.getSmallBitmap(
                BitmapFactory.decodeFile(mUri),
                bigWidth, bigHeight);

        byte[] bg = getBufferByBitmap(bitmap);
        byte[] smallBg = getBufferByBitmap(smallBitmap);

//        LogUtil.e("大图==="+bg.length+"===小图=="+smallBg.length);
        buffer = new byte[17 + bg.length + smallBg.length];
        byte[] defaultByte = CmdMergeImpl.INSTANCE.getDefaultByte(position, colors, bigWidth, bigHeight, smallNeedWidth, smallNeedHeight);

        System.arraycopy(defaultByte, 0, buffer, 0, defaultByte.length);
        System.arraycopy(bg, 0, buffer, defaultByte.length, bg.length);
        System.arraycopy(smallBg, 0, buffer, defaultByte.length + bg.length, smallBg.length);
//        LogUtil.d("初始化自定义表盘完毕");

    }

    //校验表盘文件
    private void checkRes(String path) throws IOException {
        InputStream inputStream = new FileInputStream(new File(path));
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            // byte[] buffer=null;
            int len = 0;
            byte[] buf = new byte[2048];
            while ((len = bufferedInputStream.read(buf)) != -1) {
                byteArrayOutputStream.write(buf, 0, len);
            }
            byteArrayOutputStream.flush();
            buffer = byteArrayOutputStream.toByteArray();
            //startPushDial();
            LogUtil.e("--------------------文件数据初始化完毕:" + buffer.length);
        } catch (Exception e) {

        } finally {
            inputStream.close();
            bufferedInputStream.close();
            byteArrayOutputStream.close();
        }

    }

    private byte[] getBufferByBitmap(Bitmap bmap) {
        int bytes = bmap.getByteCount();
        ByteBuffer buffer = ByteBuffer.allocate(bytes);
        bmap.copyPixelsToBuffer(buffer);
        return changeArrayIndex(buffer.array());
    }

    private byte[] changeArrayIndex(byte[] bytes) {
        byte des;
        for (int i = 0; i < bytes.length; i += 2) {
            des = bytes[i];
            bytes[i] = bytes[i + 1];
            bytes[i + 1] = des;
        }
        return bytes;
    }


    public interface NJjPushListener {
        void onPushSuccess();

        void onPushError(int code);

        void onPushStart();

        void onPushProgress(int progress);
    }

    private NJjPushListener pushListener;

}
