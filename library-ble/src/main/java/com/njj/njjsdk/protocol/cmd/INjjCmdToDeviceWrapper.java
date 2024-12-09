package com.njj.njjsdk.protocol.cmd;


import com.njj.njjsdk.callback.NjBtNameCallback;
import com.njj.njjsdk.callback.NjjBatteryCallBack;
import com.njj.njjsdk.callback.NjjConfig1CallBack;
import com.njj.njjsdk.callback.NjjDeviceFunCallback;
import com.njj.njjsdk.callback.NjjECGCallBack;
import com.njj.njjsdk.callback.NjjFirmwareCallback;
import com.njj.njjsdk.callback.NjjHomeDataCallBack;
import com.njj.njjsdk.callback.NjjNotifyCallback;
import com.njj.njjsdk.callback.NjjPushOtaCallback;
import com.njj.njjsdk.callback.NjjWriteCallback;
import com.njj.njjsdk.protocol.entity.EmergencyContact;
import com.njj.njjsdk.protocol.entity.NJJGPSSportEntity;
import com.njj.njjsdk.protocol.entity.NJJWeatherData;
import com.njj.njjsdk.protocol.entity.NjjAlarmClockInfo;
import com.njj.njjsdk.protocol.entity.NjjBloodOxyData;
import com.njj.njjsdk.protocol.entity.NjjBloodPressure;
import com.njj.njjsdk.protocol.entity.NjjDeviceInfoData;
import com.njj.njjsdk.protocol.entity.NjjDisturbEntity;
import com.njj.njjsdk.protocol.entity.NjjDrinkWaterEntity;
import com.njj.njjsdk.protocol.entity.NjjHeartData;
import com.njj.njjsdk.protocol.entity.NjjLongSitEntity;
import com.njj.njjsdk.protocol.entity.NjjRunDetailsInfoData;
import com.njj.njjsdk.protocol.entity.NjjSleepInfo;
import com.njj.njjsdk.protocol.entity.NjjStepData;
import com.njj.njjsdk.protocol.entity.NjjSyncWeatherData;
import com.njj.njjsdk.protocol.entity.NjjWashHandEntity;
import com.njj.njjsdk.protocol.entity.NjjWristScreenEntity;

import java.util.Date;
import java.util.List;

import io.reactivex.Observable;

/**
 * 定义要发命令的抽象接口
 *
 * <b>
 * 所有与设备进行交互的操作都抽象到这里
 * </b>
 *
 * @ClassName IAppSendCmdToDeviceWrapper
 * @Description TODO
 * @Author Darcy
 * @Date 2021/3/22 19:57
 * @Version 1.0
 */
public interface INjjCmdToDeviceWrapper {


    /**
     * 设备响铃 find me
     *
     * @date 2021-4-2 17:12:11
     * @return
     */
    void FindDevice(NjjWriteCallback callback);


    /**
     * 获取硬件信息
     *
     * @date 2021-4-15 20:04:42
     * @param callback
     */
    void getDeviceInfo(NjjFirmwareCallback callback);


    /**
     * 获取UI版本
     *
     * @date 2021-4-15 20:04:42
     */

    void getUIDeviceInfo();


    /**
     * 获取TP版本
     *
     * @date 2021-4-15 20:04:42
     */
    void getTPDeviceInfo();
    /**
     * 停止响铃（震动） find me
     *
     * @date 2021-4-2 17:12:41
     */
    void stopFindDevice();

    /**
     * 同步时间
     *
     * @param  callback
     */
    void syncDeviceTime(NjjWriteCallback callback);

    /**
     * 获取心率数据
     *
     * @date 2021-4-2 17:14:27
     * @return
     */
    Observable<List<NjjHeartData>> syncHeartRateData();

    /**
     * 获取血氧数据
     *
     * @date 2022-3-31 14:52:36
     * @return
     */
    Observable<List<NjjBloodOxyData>> syncOxData();

    /**
     * 获取血压数据
     *
     * @date 2022-3-31 14:52:36
     * @return
     */
    Observable<List<NjjBloodPressure>> syncBloodPressure();

/*    *//**
     * 获取实时心率数据
     *
     * @date 2021-5-10 15:44:27
     *//*
    void syncRealTimeHeartRateData();*/

    /**
     * 获取电池电量
     *
     * @param callBack
     */
    void getBatteryLevel(NjjBatteryCallBack callBack);


    void setDisplayTime(int time, NjjWriteCallback callback);
 /*   *//**
     * 获取实时计步数据、
     *
     * @date 2021-5-10 15:44:01
     *//*
    void syncRealTimeStepData();*/

    /**
     * 获取计步数据
     *
     * @date 2021-4-12 10:05:41
     * @return
     */
    Observable<List<NjjStepData>> syncHourStepData();

    /**
     * 抬手亮屏
     */
    void UpHandleScreenOn(NjjWristScreenEntity entity, NjjWriteCallback callback);

    /**
     * 久坐提醒
     */
    void syncLongSitNotify(NjjLongSitEntity entity, NjjWriteCallback callback);

    /**
     * 饮水提醒
     */
    void synDrinkWaterNotify(NjjDrinkWaterEntity entity, NjjWriteCallback callback);

    void synWashNotify(NjjWashHandEntity entity, NjjWriteCallback callback);

    /**
     * 勿扰模式设置
     */
    void noDisturbSetting(NjjDisturbEntity entity, NjjWriteCallback callback);

    /**
     * 心率监测频率设置
     */
    void syncHeartMonitor(int status, NjjWriteCallback callback);

    /**
     * 同步闹钟信息
     */
    void syncAlarmClockData(List<NjjAlarmClockInfo> info, NjjWriteCallback callback);

    /**
     * 获取闹钟的信息
     * @return
     */
    Observable<List<NjjAlarmClockInfo>> getAlarmClockData();


    /**
     * 来电提醒
     */
    void syncTelCallStatue(String name, String phoneNumber, boolean isCall, NjjWriteCallback callback);

    /**
     * 同步睡眠
     * @return
     */
    Observable<NjjSleepInfo> syncSleepData();

    /**
     * 同步睡眠
     * week_day (0~6),一个字节
     * 0：(周日 ~周一)的睡眠数据
     * 1：(周1 ~周2)的睡眠数据
     * 2：(周2 ~周3)的睡眠数据
     * 3：(周3 ~周4)的睡眠数据
     * 4：(周4~周5)的睡眠数据
     * 5：(周5~周6)的睡眠数据
     * 6：(周6~周日)的睡眠数据
     */
    void syncSleepData(int weekDay);


    Observable<List<NjjStepData>> syncWeekDaySports();


    /**
     * 实时获取ecg数据
     */
    void syncRealTimeECG(boolean isOpen, NjjECGCallBack callBack);

    /**
     * 关机
     */
    void shutdown( );
    void reboot( );


 /*   */ void reset();

    /**
     * 向设备同步用户信息
     *//*
    void setUserInfo(SyncUserInfo info);*/

//    /**
//     * 女性健康
//     *
//     * @param isOpen 是否开启功能
//     * @param type   类型 0：生理期 1：排卵期 2：安全期
//     */
    void setFemaleHealth(int mode, int menstruationAdvance, int menstruationCycle
            , int menstruationDuration, long enstruationEndDay
            , Date menstruationLatest, int OpenWomanHealth, long remindTime);

/*
    *//**
     * 同步天气类型和温度值
     * 7天
     *//*
    void syncWeatherTypeData(List<SyncWeatherData> data);*/


    /**
     * 时间制格式
     *
     * @param is24 true: 24小时制  false ：12小时制
     * @param callback
     */
    void setTimeFormat(boolean is24, NjjWriteCallback callback);


    /**
     * 单位制式
     *
     * @param isMetricSystem true:公制  false 英制
     * @param callback
     */
    void setUnitFormat(boolean isMetricSystem, NjjWriteCallback callback);




    /**
     * watch 7 消息通知(瑞昱平台用通知)
     *
     * @param messageId 参考协议 MessageTypeConst    package com.example.blesdk.protocol.cmd
     * @param title     标题
     * @param Content   内容
     * @param callback
     */
    void setNotify(int messageId, String title, String Content, NjjWriteCallback callback);

    /**
     * 添加紧急联系人
     * 最多添加8个
     *
     * @param emergencyContact 紧急联系人实体类
     */
    void addEmergencyContact(EmergencyContact emergencyContact);

    /**
     * 添加紧急联系人
     * 最多添加8个
     *
     * @param emergencyContact 紧急联系人实体类
     */
    void addEmergencyContact(List<EmergencyContact> emergencyContact);


    /**
     * 打开蓝牙拍照
     */
    void openTakePhotoCamera(boolean isOpen, NjjWriteCallback callback);



    /**
     * 设置设备的language
     *
     * @param languageType 语言类型 在LanguageConst查看对应语言类型
     */
    void setDeviceLanguage(int languageType);


    void modifyBtName(String name, NjBtNameCallback njBtNameCallback);

    /**
     * 推送收付款码
     *  @param codeType 类型 参考 QRCodeConst
     * @param content  内容
     * @return
     */
    Observable<Integer> syncQRCode(int codeType, String content);


    /**
     * 目标步数
     * @param steps
     * @param callback
     */
    void targetStepSet(int steps, NjjWriteCallback callback);


    /**
     * 设置手表实时天气
     * @param data
     * @param callback
     */
    void syncWeatherTypeData(NjjSyncWeatherData data, NjjWriteCallback callback);


    /***
     *
     * 用于首页下拉刷新
     *
     * @param njjHomeDataCallBack*/
    void syncHomeData(NjjHomeDataCallBack njjHomeDataCallBack);


    /**
     * 设置当前手机系统类型
     */
    void setPhoneType();

    /**
     * 获取设备功能
     */
    void getDeviceFun(NjjDeviceFunCallback njjDeviceFunCallback);

    /**
     * 获取设备的配置信息
     * 基础信息，设备的时间格式，温度格式等等
     * @return
     */
    Observable<NjjDeviceInfoData> getDeviceConfig();


    void setTempUnit(boolean isCen, NjjWriteCallback callback);


    /**
     * 开始推送表盘,瑞昱的
     *
     * @return void
     * @date 2022-4-19 14:31:01
     */
    void startPushDial(int type, byte[] buffer, NjjPushOtaCallback callback);

    void startPushBigDial(int type, byte[] buffer, NjjPushOtaCallback callback);

    /**
     * 获取设备功能信息
     * @param callBack
     */
    void getDeviceConfig1(NjjConfig1CallBack callBack);

    /**
     * 开始推送联系人
     *
     * @return void
     * @date 2022-4-19 14:31:01
     */
    void startPushContactDial(byte[] buffer);

    /***
     * 解除绑定
     */
    void unbind();

    Observable<NjjRunDetailsInfoData> syncSportRecord();


    void handUpPhone(int type, NjjWriteCallback callback);

    void openNotify(String mac);



    void registerNotifyCallback(NjjNotifyCallback singleHeartOxBloodCallback);

    Observable<Integer> syncPayCode(int type, String content);

    void starDialRuiYu(byte[] bytes);
    void startBigDailRuiYu(byte[] bytes);

    void removeEcgCallBack();
    void removeHomeDataCallBack();
    void removeBatteryCallBack();
    void removeSingleHeartOxBloodCallback();

    void setMotionGameStatus(int type);

    void syncWeekWeatherTypeData(List<NJJWeatherData> data);

    void startGPSStatus(int type, int status);

    void syncGPSStatus(NJJGPSSportEntity gpsSportEntity);

    void sendGPSStatus(int type, int sportId);

    void startGPSData(byte[] buffer);

    void sendStock(int count,int id,String code,String companyName,String currentPrice,String changePercent);
}
