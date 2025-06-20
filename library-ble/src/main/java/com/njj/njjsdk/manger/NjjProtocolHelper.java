package com.njj.njjsdk.manger;


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
import com.njj.njjsdk.protocol.cmd.INjjCmdToDeviceWrapper;
import com.njj.njjsdk.protocol.cmd.MusicConst;
import com.njj.njjsdk.protocol.cmd.NjjCmdToDeviceWrapper;
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

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.List;

import io.reactivex.Observable;

/**
 * @ClassName NjjBleHelper
 * @Description TODO
 * @Author Darcy
 * @Date 2022/7/20 20:17
 * @Version 1.0
 */
public class NjjProtocolHelper {

    private static NjjProtocolHelper instance;
    /**
     * 协议处理相关类
     */
    private INjjCmdToDeviceWrapper njjCmdToDeviceWrapper;


    public void  registerSingleHeartOxBloodCallback(NjjNotifyCallback njjNotifyCallback) {
        njjCmdToDeviceWrapper.registerNotifyCallback(njjNotifyCallback);
    }

    public static NjjProtocolHelper getInstance() {
        if (instance == null) {
            synchronized (NjjProtocolHelper.class) {
                if (instance == null) {
                    instance = new NjjProtocolHelper();
                }
            }
        }
        return instance;
    }

    public void init() {
        njjCmdToDeviceWrapper = new NjjCmdToDeviceWrapper();
    }


    /**
     * 同步时间
     */
    public void syncTime(NjjWriteCallback callback) {

        njjCmdToDeviceWrapper.syncDeviceTime(callback);
    }

    public void getUIDeviceInfo() {
        njjCmdToDeviceWrapper.getUIDeviceInfo();
    }

    /**
     * 获取设备信息*
     */
    public void getDeviceInfo(NjjFirmwareCallback callback) {
        njjCmdToDeviceWrapper.getDeviceInfo(callback);
    }

    public void getTPDeviceInfo() {
        njjCmdToDeviceWrapper.getTPDeviceInfo();
    }


    public Observable<List<NjjStepData>> syncWeekDaySports() {
        return njjCmdToDeviceWrapper.syncWeekDaySports();
    }

    /**
     * 查找设备
     *
     * @return
     */
    public void findMe(NjjWriteCallback callback) {
        njjCmdToDeviceWrapper.FindDevice(callback);
    }

    /**
     * 停止查找设备
     */
    public void stopMe() {
        njjCmdToDeviceWrapper.stopFindDevice();
    }


    /**
     * 获取电量
     */
    public void getBatteryLevel(NjjBatteryCallBack callBack) {
        njjCmdToDeviceWrapper.getBatteryLevel(callBack);
    }

    public Observable<NjjRunDetailsInfoData> syncSportRecord() {
        return njjCmdToDeviceWrapper.syncSportRecord();

    }

    /**
     * 久坐提醒
     */
    public void syncLongSitNotify(NjjLongSitEntity entity, NjjWriteCallback callback) {
        njjCmdToDeviceWrapper.syncLongSitNotify(entity, callback);
    }

    /**
     * 抬手亮屏
     */
    public void upHandleScreenOn(NjjWristScreenEntity entity, NjjWriteCallback callback) {
        njjCmdToDeviceWrapper.UpHandleScreenOn(entity, callback);
    }

    /**
     * 饮水提醒
     */
    public void syncWaterNotify(NjjDrinkWaterEntity entity, NjjWriteCallback callback) {
        njjCmdToDeviceWrapper.synDrinkWaterNotify(entity, callback);
    }

    /**
     * 饮水提醒
     */
    public void syncWashNotify(NjjWashHandEntity entity, NjjWriteCallback callback) {
        njjCmdToDeviceWrapper.synWashNotify(entity, callback);
    }


    /**
     * 勿扰设置
     */
    public void syncNoDisturbSet(NjjDisturbEntity entity, NjjWriteCallback callback) {
        njjCmdToDeviceWrapper.noDisturbSetting(entity, callback);
    }

    /**
     * 心率监测，设置监测频率
     */
    public void syncHeartMonitor(int status, NjjWriteCallback callback) {
        njjCmdToDeviceWrapper.syncHeartMonitor(status, callback);
    }

    /**
     * 同步闹钟信息
     */
    public void syncAlarmClockInfo(List<NjjAlarmClockInfo> infos, NjjWriteCallback callback) {
        njjCmdToDeviceWrapper.syncAlarmClockData(infos, callback);
    }

    /**
     * 同步来电提醒
     *
     * @param isCall true:来电  false:关电话
     */
    public void syncTelCallStatue(String name, String phoneNumber, boolean isCall, NjjWriteCallback callback) {
        njjCmdToDeviceWrapper.syncTelCallStatue(name, phoneNumber, isCall, callback);
    }

    /**
     * 是否接受心实时电数据
     *
     * @param isOpen true：接收  false：停止接收
     */
    public void syncRealTimeECG(boolean isOpen, NjjECGCallBack callBack) {
        njjCmdToDeviceWrapper.syncRealTimeECG(isOpen, callBack);
    }

    public void removeEcgCallBack(){
        njjCmdToDeviceWrapper.removeEcgCallBack();
    }


    public void removeHomeDataCallBack(){
        njjCmdToDeviceWrapper.removeHomeDataCallBack();
    }


    public void removeBatteryCallBack(){
        njjCmdToDeviceWrapper.removeBatteryCallBack();
    }

    public void removeSingleHeartOxBloodCallback(){
        njjCmdToDeviceWrapper.removeSingleHeartOxBloodCallback();
    }


    /**
     * 关机
     *
     * @param
     */
    public void shutdown() {
        njjCmdToDeviceWrapper.shutdown();
    }

    public void reboot(){
        njjCmdToDeviceWrapper.reboot();
    }
    /**
     * 关机
     *
     * @param
     */
    public void reset() {
        njjCmdToDeviceWrapper.reset();
    }
    //shutdow

    /**
     * 女性健康
     *
     * @param mode                 mode=0 关闭  1：经期 2：备孕 3：孕中
     * @param menstruationAdvance  月经提前
     * @param menstruationCycle    月经周期
     * @param menstruationDuration 经期
     * @param enstruationEndDay    设定月经结束日
     * @param menstruationLatest   最新月经记录
     * @param OpenWomanHealth      提醒开关
     * @param remindTime
     */
     /* MenstruationAdvance setMenstruationCycle  setMenstruationDuration   setMenstruationEndDay
        setMenstruationLatest  setPregnancyRemindType
        月经提前         月经周期        经期        设定月经结束日
        最新月经记录         孕母精型*/
    public void syncFemaleHealth(int mode, int menstruationAdvance, int menstruationCycle
            , int menstruationDuration, long enstruationEndDay
            , Date menstruationLatest, int OpenWomanHealth, long remindTime) {
        njjCmdToDeviceWrapper.setFemaleHealth(mode, menstruationAdvance, menstruationCycle, menstruationDuration,
                enstruationEndDay, menstruationLatest, OpenWomanHealth, remindTime);
    }

    /*   *//**
     * 设置用户信息（包含体温单位，距离单位)
     *//*
    public void setUserInfo(SyncUserInfo info) {
        sendCmdToDeviceWrapper.setUserInfo(info);
    }
*/
    /* *//**
     * 同步七天天气信息
     *//*
    public void setWeatherInfo(List<SyncWeatherData> data) {
        sendCmdToDeviceWrapper.syncWeatherTypeData(data);
    }*/


    /**
     * 设置时间格式
     */
    public void setTimeFormat(boolean is24, NjjWriteCallback callback) {
        njjCmdToDeviceWrapper.setTimeFormat(is24, callback);
    }

    /**
     * 单位制式
     */
    public void setUnitFormat(boolean isMetricSystem, NjjWriteCallback callback) {
        njjCmdToDeviceWrapper.setUnitFormat(isMetricSystem, callback);
    }


    /**
     * 设置通知
     * 瑞昱平台的
     */
    public void setNotify(int messageId, String title, String value, NjjWriteCallback callback) {
        njjCmdToDeviceWrapper.setNotify(messageId, title, value, callback);
    }

    /**
     * 添加联系人
     */
    public void addEmergencyContact(EmergencyContact emergencyContact) {
        njjCmdToDeviceWrapper.addEmergencyContact(emergencyContact);
    }

    /**
     * 添加联系人数组
     */
    public void addEmergencyContact(List<EmergencyContact> emergencyContact) {
        njjCmdToDeviceWrapper.addEmergencyContact(emergencyContact);
    }


    /**
     * watch 拍照相关
     */
    public void openTakePhotoCamera(boolean isOpen, NjjWriteCallback callback) {
        njjCmdToDeviceWrapper.openTakePhotoCamera(isOpen, callback);
    }

    /***
     *###################################################################################################
     *#####################################同步运动数据相关###############################################
     *###################################################################################################
     * */

    /**
     * 同步心率数据
     *
     * @return
     */
    public Observable<List<NjjHeartData>> syncHeartData() {
        return njjCmdToDeviceWrapper.syncHeartRateData();
    }


    /**
     * 获取计步数据
     *
     * @return
     */
    public Observable<List<NjjStepData>> syncHourStep() {
        return njjCmdToDeviceWrapper.syncHourStepData();
    }


    /***
     * 同步血氧数据
     *
     * @return*/
    public Observable<List<NjjBloodOxyData>> syncOxData() {
        return njjCmdToDeviceWrapper.syncOxData();
    }


    /***
     * 同步血压数据
     *
     * @return*/
    public Observable<List<NjjBloodPressure>> syncBloodPressure() {
        return njjCmdToDeviceWrapper.syncBloodPressure();
    }


    /**
     * 睡眠
     *
     * @return
     */
    public Observable<NjjSleepInfo> syncSleepData() {
        return njjCmdToDeviceWrapper.syncSleepData();
    }

    /**
     * 睡眠(瑞昱)
     */
    public void syncSleepData(int weekDay) {
        njjCmdToDeviceWrapper.syncSleepData(weekDay);
    }


    /**
     * 设置设备语言
     */
    public void setDeviceLanguage(int languageType) {
        njjCmdToDeviceWrapper.setDeviceLanguage(languageType);
    }

    public void modifyBtName(String btName, NjBtNameCallback njBtNameCallback) {
        njjCmdToDeviceWrapper.modifyBtName(btName,njBtNameCallback);
    }

    /**
     * 推送交友码
     *
     * @param type APP_WECHAT = 0,
     *             APP_QQ=1
     *             APP_SKYPE=2
     *             APP_FACEBOOK=3
     *             APP_TWITTER=4
     *             APP_WHATS=5
     *             APP_LINE=6
     *             APP_INS=7
     *             APP_MESSENGER=8
     *             APP_SNAPCHAT=9
     * @return     二维码内容
     */
    public Observable<Integer> pushQRCode(int type, String content) {
        return njjCmdToDeviceWrapper.syncQRCode(type, content);
    }

    /**
     * 推送支付码
     *
     * @param type    PAYMENT_WEPAY = 10,
     *                PAYMENT_ALIPAY=11
     *                PAYMENT_QQ=12
     *                PAYMENT_PAYPAL=13
     * @param content 二维码内容
     * @return
     */
    public Observable<Integer> pushPayCode(int type, String content) {
        return njjCmdToDeviceWrapper.syncPayCode(type, content);
    }

    public void handUpPhone(int type, NjjWriteCallback callback) {
        njjCmdToDeviceWrapper.handUpPhone(type, callback);
    }

    /**
     * 同步手机系统
     */
    public void setPhoneType() {
        njjCmdToDeviceWrapper.setPhoneType();
    }

    /**
     * 手表配置信息
     *
     * @return
     */
    public Observable<NjjDeviceInfoData> getDeviceConfig() {
        return njjCmdToDeviceWrapper.getDeviceConfig();
    }

    /**
     * 设置目标步数
     */
    public void setTargetStep(int step, NjjWriteCallback callback) {
        njjCmdToDeviceWrapper.targetStepSet(step, callback);
    }


    /**
     * 设置亮屏时长
     */
    public void setDisplayTime(int time, NjjWriteCallback callback) {
        njjCmdToDeviceWrapper.setDisplayTime(time, callback);
    }

    /**
     * 温度格式
     */
    public void setTempUnit(boolean isCen, NjjWriteCallback callback) {
        njjCmdToDeviceWrapper.setTempUnit(isCen, callback);
    }

    /**
     * 开始写入大文件
     *
     * @param type
     * @param data 要写入的大文件
     */
    public void startSendBigData(int type, byte[] data, NjjPushOtaCallback callback) {
        njjCmdToDeviceWrapper.startPushDial(type, data, callback);
    }

    /**
     * 开始写入大文件
     *
     * @param type
     * @param data 要写入的大文件
     */
    public void startSendSuperBigData(int type, byte[] data, NjjPushOtaCallback callback) {
        njjCmdToDeviceWrapper.startPushBigDial(type, data, callback);
    }

    public void startGPSStatus(int type,int status){
        njjCmdToDeviceWrapper.startGPSStatus(type,status);
    }

    public void syncGPSData(NJJGPSSportEntity gpsSportEntity){
        njjCmdToDeviceWrapper.syncGPSStatus(gpsSportEntity);
    }


    public void sendGPSStatus(int type,int sportId){
        njjCmdToDeviceWrapper.sendGPSStatus(type,sportId);
    }


    public void startGPSData(byte[] buffer) {
        njjCmdToDeviceWrapper.startGPSData(buffer);
    }
    /**
     * 获取闹钟信息
     *
     * @return
     */
    public Observable<List<NjjAlarmClockInfo>> getAlarmClockInfo() {
        return njjCmdToDeviceWrapper.getAlarmClockData();
    }

    public void openNotify(String mac) {
        njjCmdToDeviceWrapper.openNotify(mac);
    }

    public void syncWeatherTypeData(NjjSyncWeatherData data, NjjWriteCallback callback) {
        njjCmdToDeviceWrapper.syncWeatherTypeData(data, callback);
    }

    public void syncHomeData(NjjHomeDataCallBack njjHomeDataCallBack) {
        njjCmdToDeviceWrapper.syncHomeData(njjHomeDataCallBack);
    }

    public void startDailRuiYu(byte[] bytes) {
        njjCmdToDeviceWrapper.starDialRuiYu(bytes);
    }

    public void startBigDailRuiYu(byte[] bytes) {
        njjCmdToDeviceWrapper.startBigDailRuiYu(bytes);
    }

    public void getDeviceConfig1(NjjConfig1CallBack callBack) {
        njjCmdToDeviceWrapper.getDeviceConfig1(callBack);
    }

    public void getDeviceFun(@NotNull NjjDeviceFunCallback callback) {
        njjCmdToDeviceWrapper.getDeviceFun(callback);
    }

    /**
     * 	0 开始游戏
     *  1  结束游戏
     *  2 暂停游戏
     * @param type
     */
    public void setMotionGameStatus(int type){
        njjCmdToDeviceWrapper.setMotionGameStatus(type);
    }

    /**
     * 一周天气
     * @param data
     */
    public void syncWeekWeatherTypeData(List<NJJWeatherData>  data){
        njjCmdToDeviceWrapper.syncWeekWeatherTypeData(data);
    }

    public void sendStock(int count,int id,String code,String companyName,String currentPrice,String changePercent) {
        if (njjCmdToDeviceWrapper!=null)
            njjCmdToDeviceWrapper.sendStock(count,id,code,companyName,currentPrice,changePercent);
    }

    public void sendLocationAddress(byte[] result) {
        if (njjCmdToDeviceWrapper!=null)
            njjCmdToDeviceWrapper.sendLocationAddress(result);
    }

    public void sendIsSupport(int isSupport) {
        if (njjCmdToDeviceWrapper!=null)
            njjCmdToDeviceWrapper.sendIsSupport(isSupport);
    }

    public void sendTranslateContent(byte[] result) {
        if (njjCmdToDeviceWrapper!=null)
            njjCmdToDeviceWrapper.sendTranslateContent(result);
    }

    public void sendSpeechRecognitionContent(byte[] result,int id,int count) {
        if (njjCmdToDeviceWrapper!=null)
            njjCmdToDeviceWrapper.sendSpeechRecognitionContent(result,id,count);
    }

    /**
     *
     * @param playType 0 暂停  1播放
     */
    public void sendPlaying(int playType) {
        if (njjCmdToDeviceWrapper!=null)
            njjCmdToDeviceWrapper.sendPlaying(MusicConst.MusicType.BLE_MEDIA_REC_CMD_STATE,playType);
    }

    public void sendMusicVolume(int volume,int maxVolume) {
        if (njjCmdToDeviceWrapper!=null)
            njjCmdToDeviceWrapper.sendMusicVolume(MusicConst.MusicType.BLE_MEDIA_REC_CMD_VOLUME,volume,maxVolume);
    }

    public void sendMusicLyrics(String lyrics) {
        if (njjCmdToDeviceWrapper!=null)
            njjCmdToDeviceWrapper.sendMusicLyrics(MusicConst.MusicType.BLE_MEDIA_REC_CMD_LYRICS,lyrics);
    }

    public void sendMusicName(String name) {
        if (njjCmdToDeviceWrapper!=null)
            njjCmdToDeviceWrapper.sendMusicName(MusicConst.MusicType.BLE_MEDIA_REC_CMD_SONG_NAME,name);
    }
}
