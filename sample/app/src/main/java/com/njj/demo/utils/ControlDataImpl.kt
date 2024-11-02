package com.njj.demo.utils

import com.njj.demo.entity.ControlInfo
import com.njj.njjsdk.protocol.cmd.*



/**
 * @ClassName ControlDataImpl
 * @Description TODO
 * @Author  Darcy
 * @Date 2022/3/1 19:05
 * @Version 1.0
 */
object ControlDataImpl {

    fun getControlData(): MutableList<ControlInfo> {
        return mutableListOf(
            ControlInfo(EVT_TYPE_ALERT_FIND_WATCH, "找手表"),
//            ControlInfo(EVT_TYPE_TP_VER, "TP版本"),
            ControlInfo(EVT_TYPE_FIRMWARE_VER, "固件版本"),
//            ControlInfo(EVT_TYPE_UI_VER, "UI版本"),
//            ControlInfo(EVT_TYPE_GSENSOR_XYZ, "gsensor"),
//            ControlInfo(EVT_TYPE_USER_INFO, "用户信息"),
            ControlInfo(EVT_TYPE_UNIT_SYSTEM, "公制"),
            ControlInfo(EVT_TYPE_UNIT_SYSTEM, "英制"),
//            ControlInfo(EVT_TYPE_UNIT_SYSTEM, "手表制式"),
            ControlInfo(EVT_TYPE_DATE_TIME, "设置时间"),
            ControlInfo(EVT_TYPE_TIME_MODE, "12h制"),
            ControlInfo(EVT_TYPE_TIME_MODE, "24h制"),
//            ControlInfo(EVT_TYPE_TIME_MODE, "时间制式"),
            ControlInfo(EVT_TYPE_TEMP_UNIT, "摄氏度"),
            ControlInfo(EVT_TYPE_TEMP_UNIT, "华氏度"),
//
            ControlInfo(EVT_TYPE_LANGUAGE, "语言设置"),
//            ControlInfo(EVT_TYPE_TIME_STYLE, "set表盘id"),
//            ControlInfo(EVT_TYPE_TIME_STYLE, "get表盘id"),
            ControlInfo(EVT_TYPE_BAT, "获取电量"),
            ControlInfo(EVT_TYPE_TARGET_STEP, "目标步数"),
            ControlInfo(EVT_TYPE_HOUR_STEP, "分时计步"),
            ControlInfo(EVT_TYPE_HISTORY_SPORT_DATA, "七天数据"),
            ControlInfo(EVT_TYPE_NOTIFICATIONS, "消息通知"),
            ControlInfo(EVT_TYPE_DISPLAY_TIME, "亮屏时长"),
            ControlInfo(EVT_TYPE_TEMP_UNIT, "温度制式"),
            ControlInfo(EVT_TYPE_SLEEP_DATA, "睡眠数据"),
            ControlInfo(EVT_TYPE_SPORT_RECORD, "运动记录"),
            ControlInfo(EVT_TYPE_BAND_CONFIG, "手环配置"),
//            ControlInfo(EVT_TYPE_SPORT_DATA, "运动数据"),
//            ControlInfo(EVT_TYPE_WEATHER_FORECAST, "天气预报"),
            ControlInfo(EVT_TYPE_REAL_TIME_WEATHER, "实时天气"),
            ControlInfo(EVT_TYPE_RAISE_WRIST, "打开抬腕亮屏"),
            ControlInfo(EVT_TYPE_RAISE_WRIST, "关闭抬腕亮屏"),
            ControlInfo(EVT_TYPE_DISTURB, "打开勿扰模式"),
            ControlInfo(EVT_TYPE_DISTURB, "关闭勿扰模式"),

            ControlInfo(EVT_TYPE_LONG_SIT, "久坐提醒"),
            ControlInfo(EVT_TYPE_DRINK_WATER, "喝水提醒"),
            ControlInfo(EVT_TYPE_WASH_HAND, "洗手提醒"),
            ControlInfo(EVT_TYPE_SCHEDULE, "日程提醒"),
            ControlInfo(EVT_TYPE_ALARM, "设置闹钟"),
/*
            ControlInfo(EVT_TYPE_TEMP, "单次体温"),
            ControlInfo(EVT_TYPE_HR, "单次心率"),
            ControlInfo(EVT_TYPE_BP, "单次血压"),
            ControlInfo(EVT_TYPE_BO, "单次血氧"),*/

            ControlInfo(EVT_TYPE_HR_DAY, "全天心率"),
            ControlInfo(EVT_TYPE_BO_DAY, "全天血氧"),
            ControlInfo(EVT_TYPE_BP_DAY, "全天血压"),


            ControlInfo(EVT_TYPE_ALL_DAY_FALG, "打开全天测开关"),
            ControlInfo(EVT_TYPE_ALL_DAY_FALG, "关闭全天测开关"),
//            ControlInfo(EVT_TYPE_HR_BP_BO2, "设置单次数据"),

            ControlInfo(EVT_TYPE_TAKE_PHOTO, "打开拍照"),
            ControlInfo(EVT_TYPE_TAKE_PHOTO, "关闭拍照"),
            ControlInfo(EVT_TYPE_CTRL_MUSIC, "音乐控制"),

            ControlInfo(EVT_TYPE_ALARM, "获取闹钟"),
            //EVT_TYPE_CTRL_MUSIC
            ControlInfo(-1, "表盘推送"),
            ControlInfo(EVT_TYPE_OTA_START, "联系人推送"),
            ControlInfo(EVT_TYPE_ALERT_MSG, "消息推送"),

            ControlInfo(EVT_TYPE_ADD_FRIEND, "推送名片"),
            ControlInfo(EVT_TYPE_RECEIPT_CODE, "推送收款码"),
            ControlInfo(EVT_TYPE_ANDROID_PHONE_CTRL, "接听电话"),
            ControlInfo(EVT_TYPE_ANDROID_PHONE_CTRL, "挂断电话"),
            ControlInfo(EVT_TYPE_APP_REQUEST_SYNC, "同步基础数据"),
            ControlInfo(EVT_TYPE_WOMEN_HEALTH, "女性健康"),
            ControlInfo(EVT_TYPE_ECG_HR, "心电测试"),
            ControlInfo(EVT_TYPE_DEVICE_FUN, "设备信息"),
            ControlInfo(EVT_TYPE_BAND_CONFIG1, "设备配置"),





        )

    }
}