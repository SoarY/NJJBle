package com.njj.njjsdk.protocol.cmd;


import static com.njj.njjsdk.protocol.cmd.CmdConstKt.EVT_TYPE_WEATHER_FORECAST;

import com.njj.njjsdk.callback.Mac3CallBack;
import com.njj.njjsdk.callback.NjBtNameCallback;
import com.njj.njjsdk.callback.NjjConfig1CallBack;
import com.njj.njjsdk.callback.NjjDeviceFunCallback;
import com.njj.njjsdk.library.Code;
import com.njj.njjsdk.library.Constants;
import com.njj.njjsdk.library.connect.response.BleNotifyResponse;
import com.njj.njjsdk.library.connect.response.BleWriteResponse;
import com.njj.njjsdk.callback.NjjBatteryCallBack;
import com.njj.njjsdk.callback.NjjECGCallBack;
import com.njj.njjsdk.callback.NjjPushOtaCallback;
import com.njj.njjsdk.callback.NjjWriteCallback;
import com.njj.njjsdk.callback.NjjFirmwareCallback;

import com.njj.njjsdk.callback.NjjHomeDataCallBack;
import com.njj.njjsdk.callback.NjjNotifyCallback;
import com.njj.njjsdk.manger.NjjBleManger;
import com.njj.njjsdk.protocol.cmd.cmd.NjjAnalysisData;
import com.njj.njjsdk.protocol.cmd.cmd.CmdMergeImpl;
import com.njj.njjsdk.protocol.entity.NJJWeatherData;
import com.njj.njjsdk.protocol.entity.NjjAlarmClockInfo;
import com.njj.njjsdk.protocol.entity.NjjBloodOxyData;
import com.njj.njjsdk.protocol.entity.NjjBloodPressure;
import com.njj.njjsdk.protocol.entity.EmergencyContact;
import com.njj.njjsdk.protocol.entity.NjjDeviceInfoData;
import com.njj.njjsdk.protocol.entity.NjjDisturbEntity;
import com.njj.njjsdk.protocol.entity.NjjDrinkWaterEntity;
import com.njj.njjsdk.protocol.entity.NjjHeartData;
import com.njj.njjsdk.protocol.entity.NjjLongSitEntity;
import com.njj.njjsdk.protocol.entity.NjjSleepInfo;
import com.njj.njjsdk.protocol.entity.NjjStepData;
import com.njj.njjsdk.protocol.entity.NjjSyncWeatherData;
import com.njj.njjsdk.protocol.entity.NjjRunDetailsInfoData;
import com.njj.njjsdk.protocol.entity.NjjWashHandEntity;
import com.njj.njjsdk.protocol.entity.NjjWristScreenEntity;
import com.njj.njjsdk.utils.ByteAndStringUtil;
import com.njj.njjsdk.utils.LogUtil;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 * 发送命令实现类
 * <p>
 * 具体实现方法在这里
 * </P>
 *
 * @ClassName AppSendCmdToDeviceWrapper
 * @Description TODO
 * @Version 1.0
 */
public class NjjCmdToDeviceWrapper implements INjjCmdToDeviceWrapper {

    private NjjFirmwareCallback njjFirmwareCallback;
    private NjjBatteryCallBack njjBatteryCallBack;
    private NjjNotifyCallback singleHeartOxBloodCallback;
    private NjjDeviceFunCallback njjDeviceFunCallback;
    private NjjHomeDataCallBack homeDataCallBack;
    private NjjPushOtaCallback njjPushOtaCallback;
    private NjjECGCallBack njjECGCallBack;
    private NjjConfig1CallBack config1CallBack;
    private NjBtNameCallback btNameCallback;

   /* private Observable<List<NjjHeartData>> njjHearObservable;
    private Observable<List<NjjBloodOxyData>> njjOxObservable;
    private Observable<List<NjjBloodPressure>> njjBpObservable;
    private Observable<List<NjjStepData>> hourStepObservable;
    private Observable<NjjWeekSportData> weekSportObservable;
    private Observable<NjjSleepData> sleepDataObservable;
    private Observable<NjjRunDetailsInfoData> sportRecordObservable;
    private Observable<NjjDeviceInfoData> deviceConfigObservable;
    private Observable<List<NjjAlarmClockInfo>> alarmClockObservable;
    private Observable<Integer> qRObservable;
    private Observable<Integer> payObservable;*/

    private Observable<List<NjjHeartData>> njjHearObservable;
    private Observable<List<NjjBloodOxyData>> njjOxObservable;
    private Observable<List<NjjBloodPressure>> njjBpObservable;
    private Observable<List<NjjStepData>> hourStepObservable;
    private Observable<List<NjjStepData>> weekSportObservable;
    private Observable<NjjSleepInfo> sleepDataObservable;
    private Observable<NjjRunDetailsInfoData> sportRecordObservable;
    private Observable<NjjDeviceInfoData> deviceConfigObservable;
    private Observable<List<NjjAlarmClockInfo>> alarmClockObservable;
    private Observable<Integer> qRObservable;
    private Observable<Integer> payObservable;

    private Observer hearObserver;
    private Observer oxObserver;
    private Observer bpObserver;
    private Observer hourStepObserver;
    private Observer weekSportObserver;
    private Observer sleepDataObserver;
    private Observer sportRecordObserver;
    private Observer deviceConfigObserver;
    private Observer alarmClockObserver;
    private Observer qrObserver;
    private Observer payObserver;


    public NjjCmdToDeviceWrapper() {

    }

    @Override
    public void registerNotifyCallback(NjjNotifyCallback callback) {
        singleHeartOxBloodCallback = callback;
    }

    /***
     * **********************************************************************************************###
     * ****************************************具体发送命令*******************************************###
     * **********************************************************************************************###
     * @return
     */
    @Override
    public void FindDevice(NjjWriteCallback callback) {
        byte[] bytes = CmdMergeImpl.INSTANCE.commonControlWrite(CmdConstKt.EVT_TYPE_ALERT_FIND_WATCH);
        NjjBleManger.getInstance().writeData(bytes, new BleWriteResponse() {
            @Override
            public void onResponse(int i) {
                if (i == 0) {
                    callback.onWriteSuccess();
                } else {
                    callback.onWriteFail();
                }
            }
        });


    }

    @Override
    public void getDeviceInfo(NjjFirmwareCallback callback) {
        njjFirmwareCallback = callback;
        byte[] bytes = CmdMergeImpl.INSTANCE.commonControlRead(CmdConstKt.EVT_TYPE_FIRMWARE_VER);
        NjjBleManger.getInstance().writeData(bytes, new BleWriteResponse() {
            @Override
            public void onResponse(int i) {
                if (i != 0) {
                    njjFirmwareCallback.onFirmwareFail();
                }
            }
        });
    }

    @Override
    public void getUIDeviceInfo() {
        byte[] bytes = CmdMergeImpl.INSTANCE.commonControlRead(CmdConstKt.EVT_TYPE_UI_VER);
        NjjBleManger.getInstance().writeData(bytes);
    }

    @Override
    public void getTPDeviceInfo() {
        byte[] bytes = CmdMergeImpl.INSTANCE.commonControlRead(CmdConstKt.EVT_TYPE_TP_VER);
        NjjBleManger.getInstance().writeData(bytes);
    }


    @Override
    public void stopFindDevice() {
        byte[] bytes = new byte[1];
        bytes[0] = 0x00;
        NjjBleManger.getInstance().writeData(bytes);
    }

    @Override
    public void syncDeviceTime(NjjWriteCallback callback) {
        byte[] bytes = CmdMergeImpl.INSTANCE.setTime(CmdConstKt.EVT_TYPE_DATE_TIME);
        NjjBleManger.getInstance().writeData(bytes, new BleWriteResponse() {
            @Override
            public void onResponse(int i) {
                if (i == 0) {
                    callback.onWriteSuccess();
                } else {
                    callback.onWriteFail();
                }
            }
        });
    }

    @Override
    public Observable<List<NjjHeartData>> syncHeartRateData() {
        byte[] bytes = CmdMergeImpl.INSTANCE.commonControlRead(CmdConstKt.EVT_TYPE_HR_DAY);
        NjjBleManger.getInstance().writeData(bytes);
        if (njjHearObservable == null) {
            njjHearObservable = new Observable<List<NjjHeartData>>() {
                @Override
                protected void subscribeActual(Observer<? super List<NjjHeartData>> observer) {
                    hearObserver = observer;
                }
            };
        }
        return njjHearObservable;
    }

    @Override
    public Observable<List<NjjBloodOxyData>> syncOxData() {
        byte[] bytes = CmdMergeImpl.INSTANCE.commonControlRead(CmdConstKt.EVT_TYPE_BO_DAY);
        NjjBleManger.getInstance().writeData(bytes);
        if (njjOxObservable == null) {
            njjOxObservable = new Observable<List<NjjBloodOxyData>>() {
                @Override
                protected void subscribeActual(Observer<? super List<NjjBloodOxyData>> observer) {
                    oxObserver = observer;
                }
            };
        }
        return njjOxObservable;
    }

    @Override
    public Observable<List<NjjBloodPressure>> syncBloodPressure() {
        byte[] bytes = CmdMergeImpl.INSTANCE.commonControlRead(CmdConstKt.EVT_TYPE_BP_DAY);
        NjjBleManger.getInstance().writeData(bytes);
        if (njjBpObservable == null) {
            njjBpObservable = new Observable<List<NjjBloodPressure>>() {
                @Override
                protected void subscribeActual(Observer<? super List<NjjBloodPressure>> observer) {
                    bpObserver = observer;
                }
            };
        }

        return njjBpObservable;
    }


//    @Override
//    public void syncRealTimeHeartRateData() {
//        //实时心率数据
//        byte[] data = CommCmdUtil.createCmdBytes((byte) CmdConst.REAL_TIME_HEART, 8, 0);
//        writeData(data);
//    }

    @Override
    public void getBatteryLevel(NjjBatteryCallBack callBack) {
        njjBatteryCallBack = callBack;
        byte[] bytes = CmdMergeImpl.INSTANCE.commonControlRead(CmdConstKt.EVT_TYPE_BAT);
        NjjBleManger.getInstance().writeData(bytes, new BleWriteResponse() {
            @Override
            public void onResponse(int i) {
                if (i != 0) {
                    njjBatteryCallBack.onFail();
                }
            }
        });
    }


    @Override
    public Observable<List<NjjStepData>> syncHourStepData() {
        byte[] bytes = CmdMergeImpl.INSTANCE.commonControlRead(CmdConstKt.EVT_TYPE_HOUR_STEP);
        NjjBleManger.getInstance().writeData(bytes);
        if (hourStepObservable == null) {
            hourStepObservable = new Observable<List<NjjStepData>>() {
                @Override
                protected void subscribeActual(Observer<? super List<NjjStepData>> observer) {
                    hourStepObserver = observer;
                }
            };
        }

        return hourStepObservable;
    }

    @Override
    public void syncLongSitNotify(NjjLongSitEntity entity, NjjWriteCallback callback) {
        byte[] bytes = CmdMergeImpl.INSTANCE.setLongSit(CmdConstKt.EVT_TYPE_LONG_SIT, entity);
        NjjBleManger.getInstance().writeData(bytes, new BleWriteResponse() {
            @Override
            public void onResponse(int i) {
                if (i == 0) {
                    callback.onWriteSuccess();
                } else {
                    callback.onWriteFail();
                }
            }
        });
    }

    @Override
    public void synDrinkWaterNotify(NjjDrinkWaterEntity entity, NjjWriteCallback callback) {
        byte[] bytes = CmdMergeImpl.INSTANCE.setDrinkWater(CmdConstKt.EVT_TYPE_DRINK_WATER, entity);
        NjjBleManger.getInstance().writeData(bytes, new BleWriteResponse() {
            @Override
            public void onResponse(int i) {
                if (i == 0) {
                    callback.onWriteSuccess();
                } else {
                    callback.onWriteFail();
                }
            }
        });
    }

    @Override
    public void synWashNotify(NjjWashHandEntity entity, NjjWriteCallback callback) {
        byte[] bytes = CmdMergeImpl.INSTANCE.setWashHand(CmdConstKt.EVT_TYPE_WASH_HAND, entity);
        NjjBleManger.getInstance().writeData(bytes, new BleWriteResponse() {
            @Override
            public void onResponse(int i) {
                if (i == 0) {
                    callback.onWriteSuccess();
                } else {
                    callback.onWriteFail();
                }
            }
        });
    }


    @Override
    public void noDisturbSetting(NjjDisturbEntity entity, NjjWriteCallback callback) {
        byte[] bytes = CmdMergeImpl.INSTANCE.noDisturb(CmdConstKt.EVT_TYPE_DISTURB, entity);
        NjjBleManger.getInstance().writeData(bytes, new BleWriteResponse() {
            @Override
            public void onResponse(int i) {
                if (i == 0) {
                    callback.onWriteSuccess();
                } else {
                    callback.onWriteFail();
                }
            }
        });
    }

    //心率监测频率监测
    @Override
    public void syncHeartMonitor(int status, NjjWriteCallback callback) {
        byte[] bytes = CmdMergeImpl.INSTANCE.setSynsHeat(status);
        NjjBleManger.getInstance().writeData(bytes, new BleWriteResponse() {
            @Override
            public void onResponse(int i) {
                if (i == 0) {
                    callback.onWriteSuccess();
                } else {
                    callback.onWriteFail();
                }
            }
        });
    }


    @Override
    public void syncAlarmClockData(List<NjjAlarmClockInfo> info, NjjWriteCallback callback) {
        byte[] bytes = CmdMergeImpl.INSTANCE.setAlarmClock(info);
        NjjBleManger.getInstance().writeData(bytes, new BleWriteResponse() {
            @Override
            public void onResponse(int i) {
                if (i == 0) {
                    callback.onWriteSuccess();
                } else {
                    callback.onWriteFail();
                }
            }
        });
    }

    @Override
    public Observable<List<NjjAlarmClockInfo>> getAlarmClockData() {
        byte[] bytes = CmdMergeImpl.INSTANCE.getAlarmClock();
        NjjBleManger.getInstance().writeData(bytes);
        if (alarmClockObservable == null) {
            alarmClockObservable = new Observable<List<NjjAlarmClockInfo>>() {
                @Override
                protected void subscribeActual(Observer<? super List<NjjAlarmClockInfo>> observer) {
                    alarmClockObserver = observer;
                }
            };
        }

        return alarmClockObservable;
    }


    @Override
    public void UpHandleScreenOn(NjjWristScreenEntity entity, NjjWriteCallback callback) {
        byte[] bytes = CmdMergeImpl.INSTANCE.handScreenOn(CmdConstKt.EVT_TYPE_RAISE_WRIST, entity);
        NjjBleManger.getInstance().writeData(bytes, new BleWriteResponse() {
            @Override
            public void onResponse(int i) {
                if (i == 0) {
                    callback.onWriteSuccess();
                } else {
                    callback.onWriteFail();
                }
            }
        });
    }


    @Override
    public void syncTelCallStatue(String name, String phoneNumber, boolean isCall, NjjWriteCallback callback) {
        //同步来电
//        byte[] incallbyteArray = CmdUtil.getIncallbyteArray(name, phoneNumber, isCall ? 0x01 : 0x00);
        //该命令长度是57，需要分开写
//        byte[] first = new byte[20];
//        System.arraycopy(incallbyteArray, 0, first, 0, 20);
//        byte[] second = new byte[20];
//        System.arraycopy(incallbyteArray, 20, second, 0, 20);
//        byte[] third = new byte[17];
//        System.arraycopy(incallbyteArray, 40, second, 0, 17);
//        writeData(first);
//        writeData(second);
//        writeData(third);
    }

    @Override
    public Observable<NjjSleepInfo> syncSleepData() {
        byte[] bytes = CmdMergeImpl.INSTANCE.syncSleepData();
        NjjBleManger.getInstance().writeData(bytes);
        if (sleepDataObservable == null) {
            sleepDataObservable = new Observable<NjjSleepInfo>() {
                @Override
                protected void subscribeActual(Observer<? super NjjSleepInfo> observer) {
                    sleepDataObserver = observer;
                }
            };
        }

        return sleepDataObservable;

    }

    @Override
    public void syncSleepData(int weekDay) {
        byte[] bytes = CmdMergeImpl.INSTANCE.syncSleepData(weekDay);
        NjjBleManger.getInstance().writeData(bytes);
    }

    @Override
    public Observable<List<NjjStepData>> syncWeekDaySports() {
        byte[] bytes = CmdMergeImpl.INSTANCE.createWeekDaySports();
        NjjBleManger.getInstance().writeData(bytes);
        if (weekSportObservable == null) {
            weekSportObservable = new Observable<List<NjjStepData>>() {
                @Override
                protected void subscribeActual(Observer<? super List<NjjStepData>> observer) {
                    weekSportObserver = observer;
                }
            };
        }
        return weekSportObservable;
    }


    @Override
    public void syncRealTimeECG(boolean isOpen, NjjECGCallBack callBack) {
        njjECGCallBack = callBack;
        byte[] bytes = CmdMergeImpl.INSTANCE.realTimeECG(CmdConstKt.EVT_TYPE_ECG_HR, isOpen);
        NjjBleManger.getInstance().writeData(bytes);
    }

    @Override
    public void removeEcgCallBack() {
        njjECGCallBack = null;
    }

    //EVT_TYPE_SHUTDOWN

    @Override
    public void shutdown() {
        LogUtil.e("发送关机成功");
        byte[] bytes = CmdMergeImpl.INSTANCE.commonControlWrite(CmdConstKt.EVT_TYPE_SHUTDOWN);
        NjjBleManger.getInstance().writeData(bytes);
    }

    @Override
    public void reboot() {
        LogUtil.e("发送重启成功");
        byte[] bytes = CmdMergeImpl.INSTANCE.commonControlWrite(CmdConstKt.EVT_TYPE_REBOOT);
        NjjBleManger.getInstance().writeData(bytes);
    }

    @Override
    public void reset() {
        LogUtil.e("发送恢复成功");
        byte[] bytes = CmdMergeImpl.INSTANCE.commonControlWrite(CmdConstKt.EVT_TYPE_RESET);
        NjjBleManger.getInstance().writeData(bytes);
    }

//

    @Override
    public void setFemaleHealth(int mode, int menstruationAdvance, int menstruationCycle
            , int menstruationDuration, long enstruationEndDay
            , Date menstruationLatest, int OpenWomanHealth, long remindTime) {
        byte[] bytes = CmdMergeImpl.INSTANCE.setFemaleHealthCmd(mode, menstruationAdvance, menstruationCycle, menstruationDuration,
                enstruationEndDay, menstruationLatest, OpenWomanHealth, remindTime);
        NjjBleManger.getInstance().writeData(bytes);
    }


    @Override
    public void setTimeFormat(boolean is24, NjjWriteCallback callback) {
        byte[] bytes = CmdMergeImpl.INSTANCE.setHourFormat(CmdConstKt.EVT_TYPE_TIME_MODE, is24);
        NjjBleManger.getInstance().writeData(bytes, new BleWriteResponse() {
            @Override
            public void onResponse(int i) {
                if (i == 0) {
                    callback.onWriteSuccess();
                } else {
                    callback.onWriteFail();
                }
            }
        });
    }

    @Override
    public void setUnitFormat(boolean isMetricSystem, NjjWriteCallback callback) {
        byte[] bytes = CmdMergeImpl.INSTANCE.setUnitFormat(CmdConstKt.EVT_TYPE_UNIT_SYSTEM, isMetricSystem);
        NjjBleManger.getInstance().writeData(bytes, new BleWriteResponse() {
            @Override
            public void onResponse(int i) {
                if (i == 0) {
                    callback.onWriteSuccess();
                } else {
                    callback.onWriteFail();
                }
            }
        });
    }

    @Override
    public void setTempUnit(boolean isCen, NjjWriteCallback callback) {
        byte[] bytes = CmdMergeImpl.INSTANCE.setHourFormat(CmdConstKt.EVT_TYPE_TEMP_UNIT, isCen);
        NjjBleManger.getInstance().writeData(bytes, new BleWriteResponse() {
            @Override
            public void onResponse(int i) {
                if (i == 0) {
                    callback.onWriteSuccess();
                } else {
                    callback.onWriteFail();
                }
            }
        });
    }


    @Override
    public void setNotify(int messageId, String title, String Content, NjjWriteCallback callback) {
        byte[] bytes = CmdMergeImpl.INSTANCE.sendMessage(messageId, title, Content);
        if (bytes != null) {
            NjjBleManger.getInstance().writeData(bytes, new BleWriteResponse() {
                @Override
                public void onResponse(int i) {
                    if (i == 0) {
                        callback.onWriteSuccess();
                    } else {
                        callback.onWriteFail();
                    }
                }
            });
        }
    }

    @Override
    public void addEmergencyContact(EmergencyContact emergencyContact) {

    }

    @Override
    public void addEmergencyContact(List<EmergencyContact> emergencyContact) {

    }


    @Override
    public void openTakePhotoCamera(boolean isOpen, NjjWriteCallback callback) {
        byte[] bytes = CmdMergeImpl.INSTANCE.setTakePhoto(CmdConstKt.EVT_TYPE_TAKE_PHOTO, isOpen);
        NjjBleManger.getInstance().writeData(bytes, new BleWriteResponse() {
            @Override
            public void onResponse(int i) {
                if (i == 0) {
                    callback.onWriteSuccess();
                } else {
                    callback.onWriteFail();
                }
            }
        });
    }


    @Override
    public void setDeviceLanguage(int languageType) {
        byte[] bytes = CmdMergeImpl.INSTANCE.setLanguage(CmdConstKt.EVT_TYPE_LANGUAGE, languageType);
        NjjBleManger.getInstance().writeData(bytes);
        LogUtil.e("ry 设置语言..............." + languageType);
    }

    @Override
    public void modifyBtName(String btName,NjBtNameCallback tnjBtNameCallback) {
        byte[] bytes = CmdMergeImpl.INSTANCE.modifyBtName(CmdConstKt.EVT_TYPE_BT_NAME, btName);
        NjjBleManger.getInstance().writeData(bytes);
        btNameCallback=tnjBtNameCallback;
    }


    @Override
    public Observable<Integer> syncQRCode(int codeType, String content) {
        byte[] bytes = CmdMergeImpl.INSTANCE.pushQRCode(codeType, content);
        if (bytes == null) {
            return null;
        }
        NjjBleManger.getInstance().writeData(bytes);
        if (qRObservable == null) {
            qRObservable = new Observable<Integer>() {
                @Override
                protected void subscribeActual(Observer<? super Integer> observer) {
                    qrObserver = observer;
                }
            };
        }

        return qRObservable;
    }

    @Override
    public Observable<Integer> syncPayCode(int codeType, String content) {
        byte[] bytes = CmdMergeImpl.INSTANCE.pushQRCode(codeType + 10, content);
        if (bytes == null) {
            return null;
        }
        NjjBleManger.getInstance().writeData(bytes);

        if (payObservable == null) {
            payObservable = new Observable<Integer>() {
                @Override
                protected void subscribeActual(Observer<? super Integer> observer) {
                    payObserver = observer;
                }
            };
        }


        return payObservable;
    }


    @Override
    public void targetStepSet(int steps, NjjWriteCallback callback) {
        byte[] bytes = CmdMergeImpl.INSTANCE.setTargetSteps(CmdConstKt.EVT_TYPE_TARGET_STEP, steps);
        NjjBleManger.getInstance().writeData(bytes, i -> {
            if (i == 0) {
                callback.onWriteSuccess();
            } else {
                callback.onWriteFail();
            }
        });
    }

    @Override
    public void syncWeatherTypeData(NjjSyncWeatherData data, NjjWriteCallback callback) {
        byte[] bytes = CmdMergeImpl.INSTANCE.setRealWeather(CmdConstKt.EVT_TYPE_REAL_TIME_WEATHER, data);
        NjjBleManger.getInstance().writeData(bytes, new BleWriteResponse() {
            @Override
            public void onResponse(int i) {
                if (i == 0) {
                    callback.onWriteSuccess();
                } else {
                    callback.onWriteFail();
                }
            }
        });
    }


    @Override
    public void setDisplayTime(int time, NjjWriteCallback callback) {
        byte[] bytes = CmdMergeImpl.INSTANCE.setDisplayTime(CmdConstKt.EVT_TYPE_DISPLAY_TIME, time);
        NjjBleManger.getInstance().writeData(bytes, new BleWriteResponse() {
            @Override
            public void onResponse(int i) {
                if (i == 0) {
                    callback.onWriteSuccess();
                } else {
                    callback.onWriteFail();
                }
            }
        });
    }

    @Override
    public void syncHomeData(NjjHomeDataCallBack njjHomeDataCallBack) {
        byte[] bytes = CmdMergeImpl.INSTANCE.syncData();
        NjjBleManger.getInstance().writeData(bytes);
        homeDataCallBack = njjHomeDataCallBack;
    }

    @Override
    public void setPhoneType() {
        byte[] bytes = CmdMergeImpl.INSTANCE.setPhoneType();
        NjjBleManger.getInstance().writeData(bytes);
    }

    @Override
    public void getDeviceFun(NjjDeviceFunCallback njjDeviceFunCallback) {
        this.njjDeviceFunCallback = njjDeviceFunCallback;
        byte[] bytes = CmdMergeImpl.INSTANCE.commonControlRead(CmdConstKt.EVT_TYPE_DEVICE_FUN);
        NjjBleManger.getInstance().writeData(bytes, i -> {
            if (i != 0) {
                njjDeviceFunCallback.onDeviceFunFail();
            }
        });


    }

    @Override
    public Observable<NjjDeviceInfoData> getDeviceConfig() {
        byte[] bytes = CmdMergeImpl.INSTANCE.commonControlRead(CmdConstKt.EVT_TYPE_BAND_CONFIG);
        NjjBleManger.getInstance().writeData(bytes);

        if (deviceConfigObservable == null) {
            deviceConfigObservable = new Observable<NjjDeviceInfoData>() {
                @Override
                protected void subscribeActual(Observer<? super NjjDeviceInfoData> observer) {
                    deviceConfigObserver = observer;
                }
            };
        }

        return deviceConfigObservable;

    }

    @Override
    public void startPushDial(int type, byte[] buffer, NjjPushOtaCallback callback) {
        byte[] bytes = CmdMergeImpl.INSTANCE.startSendDial(buffer, type);
        njjPushOtaCallback = callback;
        NjjBleManger.getInstance().writeDailRuiYu(bytes, response);
    }

    @Override
    public void startPushBigDial(int type, byte[] buffer, NjjPushOtaCallback callback) {
        byte[] bytes = CmdMergeImpl.INSTANCE.startSendBigDial(buffer, type);
        njjPushOtaCallback = callback;
        NjjBleManger.getInstance().writeDailRuiYu(bytes, response);
    }

    @Override
    public void getDeviceConfig1(NjjConfig1CallBack callBack) {
        byte[] bytes = CmdMergeImpl.INSTANCE.commonControlRead(CmdConstKt.EVT_TYPE_BAND_CONFIG1);
        config1CallBack = callBack;
        NjjBleManger.getInstance().writeData(bytes, i -> {
            if (i == 0) {
                config1CallBack.onWriteSuccess();
            } else {
                config1CallBack.onFail();
            }
        });

    }

    @Override
    public void starDialRuiYu(byte[] command) {

        NjjBleManger.getInstance().writeDailRuiYu(command, code -> {
            int a = command[4] & 0xff;
            int b = (command[5] & 0xff) << 8;
            int position=a+b;
            if (code == Constants.REQUEST_SUCCESS) {
//                LogUtil.e("发送成功=======" +position);
                njjPushOtaCallback.onPushProgress(position);
            }else {
                LogUtil.e("发送失败=======");
            }
        });

    }

    @Override
    public void startBigDailRuiYu(byte[] command) {

        NjjBleManger.getInstance().writeDailRuiYu(command, code -> {
            int a = command[4] & 0xff;
            int b = (command[5] & 0xff) << 8;
            int c = (command[6] & 0xff) << 16;
            int d = (command[7] & 0xff) << 24;
            int position=a+b+c+d;
            if (code == Constants.REQUEST_SUCCESS) {
//                LogUtil.e("发送成功=======" +position);
                njjPushOtaCallback.onPushProgress(position);
            }else {
                LogUtil.e("发送失败=======");
            }
        });

    }


    //循环累加、或运算都可以
    public int byteArrayToIntBest(byte[] bytes) {
        return (bytes[0] & 0xff)
                | ((bytes[1] & 0xff) << 8)
                | ((bytes[2] & 0xff) << 16)
                | ((bytes[3] & 0xff) << 24);
    }


    private final BleWriteResponse response = i -> {
//        int currentIndex = CmdMergeImpl.INSTANCE.getCurrentIndex();
//        if (i == Constants.REQUEST_SUCCESS) {
//            njjPushOtaCallback.onPushProgress(currentIndex);
//        } else {
//            njjPushOtaCallback.onPushError(8, currentIndex);
//        }
    };

    @Override
    public void startPushContactDial(byte[] buffer) {
        byte[] bytes = CmdMergeImpl.INSTANCE.startSendContactDial(buffer);
        NjjBleManger.getInstance().writeData(bytes);
    }

    @Override
    public void unbind() {
        byte[] bytes = CmdMergeImpl.INSTANCE.unbind();
        NjjBleManger.getInstance().writeData(bytes);
    }

    @Override
    public void setMotionGameStatus(int type) {

        byte[] bytes = CmdMergeImpl.INSTANCE.createMotionGame(type);
        NjjBleManger.getInstance().writeData(bytes);
        LogUtil.e("ry 体感 游戏..............." + type);
    }

    @Override
    public void syncWeekWeatherTypeData(List<NJJWeatherData> data) {
        byte[] bytes = CmdMergeImpl.INSTANCE.setForecastWeather(EVT_TYPE_WEATHER_FORECAST, data);
        NjjBleManger.getInstance().writeData(bytes);
    }
    @Override
    public Observable<NjjRunDetailsInfoData> syncSportRecord() {
        byte[] bytes = CmdMergeImpl.INSTANCE.receiveSportRecord();
        NjjBleManger.getInstance().writeData(bytes);
        if (sportRecordObservable == null) {
            sportRecordObservable = new Observable<NjjRunDetailsInfoData>() {
                @Override
                protected void subscribeActual(Observer<? super NjjRunDetailsInfoData> observer) {
                    sportRecordObserver = observer;
                }
            };
        }
        return sportRecordObservable;

    }


    @Override
    public void handUpPhone(int type, NjjWriteCallback callback) {
        byte[] bytes = CmdMergeImpl.INSTANCE.handUpPhone(type);
        NjjBleManger.getInstance().writeData(bytes, new BleWriteResponse() {
            @Override
            public void onResponse(int i) {
                if (i == 0) {
                    callback.onWriteSuccess();
                } else {
                    callback.onWriteFail();
                }
            }
        });
    }

    public void openNotify(String mac) {
        NjjBleManger.getInstance().openNotify(mac, notifyResponse);
    }


    /**
     * 通知
     */
    private final BleNotifyResponse notifyResponse = new BleNotifyResponse() {
        @Override
        public void onNotify(UUID service, UUID character, byte[] value) {
            LogUtil.i("有通知数据 value 长度=" + value.length + "   value =" + ByteAndStringUtil.bytesToHexString(value));
            handleReceiveData(value);
        }

        @Override
        public void onResponse(int code) {

            if (code == Code.REQUEST_SUCCESS) {
                LogUtil.i("通知打开成功可以开始通讯了....");
                String macAddress = NjjBleManger.getInstance().getmBleDevice().getDevice().getAddress();
                NjjBleManger.getInstance().requestMtu(macAddress, 512, (i, integer) -> {
                    NjjBleManger.getInstance().discoveredServices(code,macAddress);
                    LogUtil.e("打开mtu " + i + " Integer=" + integer);
                });
                setPhoneType();
            } else {
                LogUtil.e("通知打开失败....");
                NjjBleManger.getInstance().disConnection();
            }
        }
    };


    private void handleReceiveData(byte[] value) {
        byte cmd = value[1];
        switch (cmd) {
            case CmdConstKt.EVT_TYPE_FIRMWARE_VER:
                String firmware = NjjAnalysisData.INSTANCE.parserFirmware(value);
                if (njjFirmwareCallback != null)
                    njjFirmwareCallback.onFirmwareSuccess(firmware);
                break;
            case CmdConstKt.EVT_TYPE_UNIT_SYSTEM:
                String UNIT = NjjAnalysisData.INSTANCE.parserUNIT(value);
                LogUtil.e(UNIT);
                break;
            case CmdConstKt.EVT_TYPE_DATE_TIME:
                LogUtil.e("同步成功");
                break;
            case CmdConstKt.EVT_TYPE_TIME_MODE:
                String time = NjjAnalysisData.INSTANCE.parserTimeMode(value);
                LogUtil.e(time);
                break;
            case CmdConstKt.EVT_TYPE_TEMP_UNIT:
                String TEMP = NjjAnalysisData.INSTANCE.parserTempMode(value);
                break;
            case CmdConstKt.EVT_TYPE_BAT:
                int Battery = NjjAnalysisData.INSTANCE.parsertBattery(value);
                if (njjBatteryCallBack != null)
                    njjBatteryCallBack.onSuccess(Battery);
                break;
            case CmdConstKt.EVT_TYPE_HOUR_STEP:
                List<NjjStepData> njjStepData = NjjAnalysisData.INSTANCE.parserHourStep(value);
                if (hourStepObserver != null)
                    hourStepObserver.onNext(njjStepData);
                break;
            case CmdConstKt.EVT_TYPE_HISTORY_SPORT_DATA:
                List<NjjStepData> njjStepDataList = NjjAnalysisData.INSTANCE.parserWeekSport(value);
                if (weekSportObserver != null)
                    weekSportObserver.onNext(njjStepDataList);
                break;
            case CmdConstKt.EVT_TYPE_SPORT_DATA:
                if (singleHeartOxBloodCallback != null)
                    singleHeartOxBloodCallback.onStepData(NjjAnalysisData.INSTANCE.parserSportData(value));
                break;
            case CmdConstKt.EVT_TYPE_HR:
                if (singleHeartOxBloodCallback != null)
                    singleHeartOxBloodCallback.onHeartRateData((int) value[4]);
                break;
            case CmdConstKt.EVT_TYPE_BP:
                if (singleHeartOxBloodCallback != null)
                    singleHeartOxBloodCallback.onBloodPressureData((int) value[4], (int) value[5]);
                break;
            case CmdConstKt.EVT_TYPE_BO:
                if (singleHeartOxBloodCallback != null)
                    singleHeartOxBloodCallback.onOxyData((int) value[4]);
                break;
            case CmdConstKt.EVT_TYPE_FIND_PHONE:
                if (singleHeartOxBloodCallback != null)
                    singleHeartOxBloodCallback.findPhone((int) value[4]);
                break;
            case CmdConstKt.EVT_TYPE_BO_DAY:
                List<NjjBloodOxyData> njjBloodOxyData = NjjAnalysisData.INSTANCE.parserBoDay(value);
                if (oxObserver != null)
                    oxObserver.onNext(njjBloodOxyData);
                break;
            case CmdConstKt.EVT_TYPE_HR_DAY:
                List<NjjHeartData> njjHeartData = NjjAnalysisData.INSTANCE.parserHrDay(value);
                if (hearObserver != null)
                    hearObserver.onNext(njjHeartData);
                break;
            case CmdConstKt.EVT_TYPE_BP_DAY:
                List<NjjBloodPressure> njjBloodPressures = NjjAnalysisData.INSTANCE.parserBpDay(value);
                if (bpObserver != null)
                    bpObserver.onNext(njjBloodPressures);
                break;
            case CmdConstKt.EVT_TYPE_TAKE_PHOTO:
                if (singleHeartOxBloodCallback != null)
                    singleHeartOxBloodCallback.takePhone((int) value[4]);
                break;
            case CmdConstKt.EVT_TYPE_SLEEP_DATA:
                NjjSleepInfo njjSleepInfo = NjjAnalysisData.INSTANCE.parserSleepData(value);
                if (sleepDataObserver != null)
                    sleepDataObserver.onNext(njjSleepInfo);
                break;
            case CmdConstKt.EVT_TYPE_SPORT_RECORD:
                NjjRunDetailsInfoData sportRecord = NjjAnalysisData.INSTANCE.parserSportRecord(value);
                if (sportRecordObserver != null)
                    sportRecordObserver.onNext(sportRecord);
                break;
            case CmdConstKt.EVT_TYPE_BAND_CONFIG:
                NjjDeviceInfoData njjDeviceInfoData = NjjAnalysisData.INSTANCE.parserDeviceInfo(value);
                if (deviceConfigObserver != null)
                    deviceConfigObserver.onNext(njjDeviceInfoData);
                break;
            case CmdConstKt.EVT_TYPE_ALARM:
                ArrayList<NjjAlarmClockInfo> njjAlarmClockInfos = NjjAnalysisData.INSTANCE.parserGetAlarm(value);
                if (alarmClockObserver != null)
                    alarmClockObserver.onNext(njjAlarmClockInfos);
                break;
            case CmdConstKt.EVT_TYPE_ADD_FRIEND:
                if (qrObserver != null)
                    qrObserver.onNext((int) value[3]);
                break;
            case CmdConstKt.EVT_TYPE_RECEIPT_CODE:
                if (payObserver != null)
                    payObserver.onNext((int) value[3]);
                break;
            case CmdConstKt.EVT_TYPE_ANDROID_PHONE_CTRL:
                if (singleHeartOxBloodCallback != null)
                    singleHeartOxBloodCallback.endCallPhone((int) value[4]);
                break;
            case CmdConstKt.EVT_TYPE_APP_REQUEST_SYNC:
                if (homeDataCallBack != null)
                    NjjAnalysisData.INSTANCE.parserHomeData(value, homeDataCallBack);
                break;
            case (byte) CmdConstKt. EVT_TYPE_OTA_BIG_START:
            case (byte) CmdConstKt.EVT_TYPE_OTA_START:
                if (njjPushOtaCallback != null)
                    NjjAnalysisData.INSTANCE.parserOTAStart(value, njjPushOtaCallback);
                break;
            case (byte) CmdConstKt.EVT_TYPE_OTA_END:
            case (byte) CmdConstKt.EVT_TYPE_OTA_BIG_END:
                if (njjPushOtaCallback != null)
                    NjjAnalysisData.INSTANCE.parserOTAEnd(value, njjPushOtaCallback);
                break;
            case CmdConstKt.EVT_TYPE_HR_ECG:
                if (njjECGCallBack != null)
                    NjjAnalysisData.INSTANCE.parserECGData(value, njjECGCallBack);
                break;
            case CmdConstKt.EVT_TYPE_DEVICE_FUN:
                if (njjDeviceFunCallback != null)
                    NjjAnalysisData.INSTANCE.parserDeviceFunData(value, njjDeviceFunCallback);
                break;
            case CmdConstKt.EVT_TYPE_WATCH_CALL_INFO:
                String macData = NjjAnalysisData.INSTANCE.parserMacData(value);
                Mac3CallBack.onSuccess(macData);
                NjjBleManger.getInstance().creteBond(macData);
                break;
            case CmdConstKt.EVT_TYPE_BAND_CONFIG1:
                if (config1CallBack != null)
                    NjjAnalysisData.INSTANCE.parserDeviceConfig(value, config1CallBack);
                break;
            case CmdConstKt.EVT_TYPE_MOTION_GAME:
                NjjAnalysisData.INSTANCE.parserSomatosensoryGame(value);
                break;

            case CmdConstKt.EVT_TYPE_BT_NAME:

                NjjAnalysisData.INSTANCE.parserBtName(value,btNameCallback);
                break;
        }
    }


    @Override
    public void removeHomeDataCallBack() {
        homeDataCallBack = null;
    }

    @Override
    public void removeBatteryCallBack() {
        njjBatteryCallBack = null;
    }

    public void removeSingleHeartOxBloodCallback() {
        singleHeartOxBloodCallback = null;
    }



}

