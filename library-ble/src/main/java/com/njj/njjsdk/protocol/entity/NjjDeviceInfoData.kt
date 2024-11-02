package com.njj.njjsdk.protocol.entity

/**
 * @ClassName NjjDeviceInfoData
 * @Description TODO
 * @Author  Darcy
 * @Date 2022/7/27 13:53
 * @Version 1.0
 */
class NjjDeviceInfoData {
    var timeStamp: String? = null;                    //时间戳(app 转换时区后的)
    var sex = 0//性别

    var height = 0//身高

    var weight = 0//体重

    var age = 0//年龄

    var day = 0 //日

    var month = 0//月

    var year = 0 //年

    var isBind = 0;                        //绑定状态  (暂时预留)
    var resetFlagCount = 0;                //异常重启次数

    //当前语言ID
    var language = 0
    var currentDialID = 0            //当前表盘号
    var totalDialCount = 0            //总共表盘个数（除自定义表盘）
    var backLightLevel = 0                //背光等级  (1~6,6个等级)
    var timeFormat = 0                //时间格式（24小时/12小时）
    var unitFormat = 0                    //单位格式（0：公制或 1：英制）
    var tempFormat = 0                    //温度格式（0：摄氏度:1：华氏度）
    var displayTime = 0                    //亮屏时长（最大 255 秒）
    var vibrationLevel = 0;                //震动等级 0:不震动 1:弱 2:强
    var powerSave = 0;                    //省电模式（震动 + 背光 + 亮屏时长）
    var weather = 0;                  //天气开关 （0：关 1：开）
    var allDayHr = 0;                    //全天HR开关（0：关 1：开）
    var allDayBo2 = 0;                    //全天BO2开关 （0：关 1：开）
    var allDayTemp = 0;                    //全天TEMP开关 （0：关 1：开）
    var msgNotifyFlag = 0;                //消息和电话通知(每个 Bit 代表一个开关，bit0 代表电话通知)
    var passward :String?= null;                  //密码
    var appSystemType = 1             //系统类型  1 ：ios  2 ：安卓
    var appBtMac: String? = null              //手机系统蓝牙 MAC
    override fun toString(): String {
        return "NjjDeviceInfoData(timeStamp=$timeStamp, sex=$sex, height=$height, weight=$weight, age=$age, day=$day, month=$month, year=$year, isBind=$isBind, resetFlagCount=$resetFlagCount, language=$language, currentDialID=$currentDialID, totalDialCount=$totalDialCount, backLightLevel=$backLightLevel, timeFormat=$timeFormat, unitFormat=$unitFormat, tempFormat=$tempFormat, displayTime=$displayTime, vibrationLevel=$vibrationLevel, powerSave=$powerSave, weather=$weather, allDayHr=$allDayHr, allDayBo2=$allDayBo2, allDayTemp=$allDayTemp, msgNotifyFlag=$msgNotifyFlag, passward=$passward, appSystemType=$appSystemType, appBtMac=$appBtMac)"
    }


}