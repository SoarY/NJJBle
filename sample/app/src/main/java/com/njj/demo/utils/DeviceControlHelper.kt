package com.njj.demo.utils


import android.util.Log
import android.widget.TextView
import com.njj.demo.entity.ControlInfo
import com.njj.njjsdk.callback.*
import com.njj.njjsdk.manger.NjjProtocolHelper
import com.njj.njjsdk.protocol.cmd.*
import com.njj.njjsdk.protocol.entity.*
import com.njj.njjsdk.utils.LogUtil
import java.util.*

/**
 * 跟页面交互的逻辑，真正的实现逻辑不在这里
 * @ClassName DeviceControlHelper
 * @Description TODO
 * @Version 1.0
 */
class DeviceControlHelper() {


    fun userClickItem(info: ControlInfo, pos: Int, text: TextView) {

        when (info.type) {
            //表盘推送
            -1 -> {

            }

            EVT_TYPE_ALERT_FIND_WATCH -> {

                NjjProtocolHelper.getInstance().findMe(object :
                    NjjWriteCallback {
                    override fun onWriteSuccess() {
                        LogUtil.e("查找手表成功")
                        text.text = "查找手表成功"
                    }

                    override fun onWriteFail() {
                        LogUtil.e("查找手表失败")
                        text.text = "查找手表失败"
                    }

                })
            }
            EVT_TYPE_TP_VER -> {
                NjjProtocolHelper.getInstance().getTPDeviceInfo()
            }
            EVT_TYPE_FIRMWARE_VER -> {
                NjjProtocolHelper.getInstance().getDeviceInfo(object : NjjFirmwareCallback {
                    override fun onFirmwareSuccess(firmware: String) {
                        LogUtil.e("固件版本$firmware")
                        text.text = "固件版本$firmware"
                    }

                    override fun onFirmwareFail() {
                        LogUtil.e("获取失败")
                        text.text = "获取设备信息失败"
                    }
                })
            }

            EVT_TYPE_UI_VER -> {
                NjjProtocolHelper.getInstance().getUIDeviceInfo()
            }
            EVT_TYPE_BAT -> {
                NjjProtocolHelper.getInstance().getBatteryLevel(object : NjjBatteryCallBack {
                    override fun onSuccess(batteryLevel: Int) {
                        LogUtil.e("电量$batteryLevel")
                        text.text = "电量$batteryLevel"
                    }

                    override fun onFail() {
                        LogUtil.e("获取电量失败")
                    }
                })

            }
            EVT_TYPE_HOUR_STEP -> {
                NjjProtocolHelper.getInstance().syncHourStep().subscribe {
                    LogUtil.e("获取分时计步成功")
                    var stringBuffer = StringBuffer()
                    it.forEach {
                        stringBuffer.append("步数${it.stepNum}  卡路里${it.calData}")
                    }
                    text.text = stringBuffer.toString()
                }
            }
            EVT_TYPE_HISTORY_SPORT_DATA -> {
                NjjProtocolHelper.getInstance().syncWeekDaySports().subscribe {
                    LogUtil.e("获取七天数据成功")
                    text.text = "获取七天数据成功 数量== ${it.size}"
                }
            }
            EVT_TYPE_BAND_CONFIG -> {
                NjjProtocolHelper.getInstance().deviceConfig.subscribe {
                    LogUtil.e("手环信息$it")
                    text.text =it.toString()
                }
            }

            EVT_TYPE_SLEEP_DATA -> {
                NjjProtocolHelper.getInstance().syncSleepData().subscribe {
                    LogUtil.e("睡眠数据回调成功")
                    var sleepDetailList = it.sleepDetailList
                    var sleepTime = it.sleepTime
//                    if (it!=null){
//                        text.text = "睡眠数据回调成功 数量=${it.size}"
//                        var stringBuffer = StringBuffer()
//                        it.forEach {
//                            stringBuffer.append("睡眠时间${it.duration}")
//                        }
//                        text.text=stringBuffer
//                    }else{
//                        text.text="没有睡眠数据"
//                    }


                }
            }

            EVT_TYPE_SPORT_RECORD -> {
                NjjProtocolHelper.getInstance().syncSportRecord().subscribe {
                    LogUtil.e("运动数据")
                    text.text = "睡眠数据回调成功 日期 =${it.date} 步数==${it.stepNum} "

                }
            }

            EVT_TYPE_UNIT_SYSTEM -> {
                when (pos) {

                    2 -> NjjProtocolHelper.getInstance().setUnitFormat(true, object :
                        NjjWriteCallback {
                        override fun onWriteSuccess() {
                            LogUtil.e("指令发送成功")
                            text.text = "设置单位成功"
                        }

                        override fun onWriteFail() {
                            text.text = "设置单位失败"
                        }

                    })

                    3 -> NjjProtocolHelper.getInstance().setUnitFormat(false, object :
                        NjjWriteCallback {
                        override fun onWriteSuccess() {
                            LogUtil.e("指令发送成功")
                            text.text = "设置单位成功"
                        }

                        override fun onWriteFail() {
                            text.text = "设置单位失败"
                        }

                    })
                }
            }

            EVT_TYPE_TIME_MODE -> {
                when (pos) {
                    5 -> NjjProtocolHelper.getInstance().setTimeFormat(false, object :
                        NjjWriteCallback {
                        override fun onWriteSuccess() {
                            LogUtil.e("指令发送成功")
                            text.text = "设置时间格式成功"
                        }

                        override fun onWriteFail() {
                            text.text = "设置时间格式失败"

                        }

                    })

                    6 -> NjjProtocolHelper.getInstance().setTimeFormat(true, object :
                        NjjWriteCallback {
                        override fun onWriteSuccess() {
                            text.text = "设置时间格式成功"
                        }

                        override fun onWriteFail() {
                            text.text = "设置时间格式失败"
                        }

                    })
                }
            }

            EVT_TYPE_TEMP_UNIT -> {
                when (pos) {
                    7 -> NjjProtocolHelper.getInstance().setTempUnit(false, object :
                        NjjWriteCallback {
                        override fun onWriteSuccess() {
                            LogUtil.e("指令发送成功")
                            text.text = "指令发送成功"
                        }

                        override fun onWriteFail() {
                            text.text = "指令发送失败"
                        }

                    })
                    8 -> NjjProtocolHelper.getInstance().setTempUnit(true, object :
                        NjjWriteCallback {
                        override fun onWriteSuccess() {
                            LogUtil.e("指令发送成功")
                            text.text = "指令发送成功"
                        }

                        override fun onWriteFail() {
                            text.text = "指令发送失败"
                        }

                    })
                }
            }

            EVT_TYPE_DATE_TIME -> {
                NjjProtocolHelper.getInstance().syncTime(object :
                    NjjWriteCallback {
                    override fun onWriteSuccess() {
                        LogUtil.e("指令发送成功")
                        text.text = "时间同步成功"
                    }

                    override fun onWriteFail() {
                        text.text = "时间同步失败"
                    }

                })
            }

//            EVT_TYPE_TIME_STYLE -> {
//
//
//            }

            EVT_TYPE_TARGET_STEP -> {
                NjjProtocolHelper.getInstance().setTargetStep(800, object :
                    NjjWriteCallback {
                    override fun onWriteSuccess() {
                        LogUtil.e("指令发送成功")
                        text.text = "设置目标步数成功"
                    }

                    override fun onWriteFail() {
                        text.text = "设置目标步数失败"
                    }

                })
            }
            EVT_TYPE_DISPLAY_TIME -> {
                NjjProtocolHelper.getInstance().setDisplayTime(10, object :
                    NjjWriteCallback {
                    override fun onWriteSuccess() {
                        LogUtil.e("指令发送成功")
                        text.text = "设置亮屏时长成功"
                    }

                    override fun onWriteFail() {
                        text.text = "设置亮屏时长失败"
                    }

                })
            }
//            EVT_TYPE_SPORT_DATA -> {
//
//            }

//            EVT_TYPE_WEATHER_FORECAST -> {
//
//            }

            EVT_TYPE_REAL_TIME_WEATHER -> {
                var syncWeatherData = NjjSyncWeatherData()

                syncWeatherData.tempData = 22
                syncWeatherData.weatherType = 5

                NjjProtocolHelper.getInstance().syncWeatherTypeData(syncWeatherData, object :
                    NjjWriteCallback {
                    override fun onWriteSuccess() {
                        LogUtil.e("指令发送成功")
                        text.text = "设置天气成功"
                    }

                    override fun onWriteFail() {
                        text.text = "设置天气失败"
                    }

                })
            }

            EVT_TYPE_RAISE_WRIST -> {
                var entity =
                    NjjWristScreenEntity()
                entity.wristScreenStatus = 1
                entity.wristScreenBeginTime=510
                entity.wristScreenEndTime=1120
                NjjProtocolHelper.getInstance().upHandleScreenOn(entity, object :
                    NjjWriteCallback {
                    override fun onWriteSuccess() {
                        LogUtil.e("指令发送成功$entity")
                        text.text = "设置抬手亮屏成功"
                    }

                    override fun onWriteFail() {
                        text.text = "设置抬手亮屏失败"
                    }

                })
            }

            EVT_TYPE_DISTURB -> {
                var entity =
                    NjjDisturbEntity()
                entity.disturbStatus = 1
                entity.disturbInterval=5
                entity.disturbBeginTime=510
                entity.disturbEndTime=1120
                NjjProtocolHelper.getInstance().syncNoDisturbSet(entity, object :
                    NjjWriteCallback {
                    override fun onWriteSuccess() {
                        LogUtil.e("指令发送成功$entity")
                        text.text = "设置勿扰成功"
                    }

                    override fun onWriteFail() {
                        text.text = "设置勿扰失败"
                    }

                })
            }
            EVT_TYPE_DRINK_WATER -> {
                var entity =
                    NjjDrinkWaterEntity()

                entity.drinkWaterStatus = 1
                entity.drinkWaterInterval=5
                entity.drinkWaterBeginTime=510
                entity.drinkWaterEndTime=1120
                NjjProtocolHelper.getInstance().syncWaterNotify(entity, object :
                    NjjWriteCallback {
                    override fun onWriteSuccess() {
                        LogUtil.e("指令发送成功$entity")
                        LogUtil.e("指令发送成功")
                        text.text = "设置喝水成功"
                    }

                    override fun onWriteFail() {
                        text.text = "指令发送失败"
                        text.text = "设置喝水失败"
                    }

                })
            }
            EVT_TYPE_WASH_HAND -> {
                var njjWashHandEntity =
                    NjjWashHandEntity()
                njjWashHandEntity.washHandStatus = 1
                njjWashHandEntity.washHandInterval=5
                njjWashHandEntity.washHandBeginTime=510
                njjWashHandEntity.washHandEndTime=1120
                NjjProtocolHelper.getInstance().syncWashNotify(njjWashHandEntity, object :
                    NjjWriteCallback {
                    override fun onWriteSuccess() {
                        LogUtil.e("指令发送成功$njjWashHandEntity")

                        text.text = "设置免打扰成功"
                    }

                    override fun onWriteFail() {

                        text.text = "设置免打扰失败"
                    }

                })
            }

            EVT_TYPE_LONG_SIT -> {
                var commonSetEntity = NjjLongSitEntity()
                commonSetEntity.longSitStatus = 1
                commonSetEntity.longSitInterval=5
                commonSetEntity.longSitBeginTime=510
                commonSetEntity.longSitEndTime=1120
                NjjProtocolHelper.getInstance().syncLongSitNotify(commonSetEntity, object :
                    NjjWriteCallback {
                    override fun onWriteSuccess() {
                        LogUtil.e("指令发送成功=$commonSetEntity")
                        text.text = "指令发送成功"
                    }

                    override fun onWriteFail() {
                        text.text = "指令发送失败"
                    }

                })
            }

            EVT_TYPE_ALARM -> {
                when (pos) {
                    38 -> {
                        NjjProtocolHelper.getInstance().getAlarmClockInfo().subscribe {
                            LogUtil.e("获取闹钟数据成功")
                            text.text = "获取闹钟数据成功 ${it.forEach { it -> it.alarmCycle }}"
                        }
                    }
                    else -> {
                        //如果是空添加默认数据
                        val infos: MutableList<NjjAlarmClockInfo> = ArrayList<NjjAlarmClockInfo>()
                        val timeArr = intArrayOf(
                            6 * 3600,
                            95 * 360,
                            12 * 3600,
                            18 * 3600,
                            225 * 360,
                            12 * 3600,
                            18 * 3600,
                            225 * 360
                        )
                        val cycleArr = intArrayOf(62, 65, 127, 65, 127, 127, 65, 127)
                        var count = 4

                        for (i in 0 until count) {
                            infos.add(
                                NjjAlarmClockInfo(
                                    i.toString(), timeArr[i], cycleArr[i], false, true, 0
                                )
                            )
                        }
                        NjjProtocolHelper.getInstance().syncAlarmClockInfo(infos, object :
                            NjjWriteCallback {
                            override fun onWriteSuccess() {
                                LogUtil.e("指令发送成功")
                                text.text = "闹钟设置成功"
                            }

                            override fun onWriteFail() {
                                text.text = "闹钟设置失败"
                            }

                        });
                    }
                }


            }

            /*   EVT_TYPE_TEMP,

               EVT_TYPE_HR->{
                   NjjBleHelper.getInstance().syncHeartData()
               }*/
            EVT_TYPE_BP_DAY -> {
                NjjProtocolHelper.getInstance().syncBloodPressure().subscribe {
                    LogUtil.e("全天血压回调成功")
                    var stringBuffer = StringBuffer()
                    stringBuffer.append("全天血压=")
                    it.forEach { njj ->
                        (njj.diastolicPressure)
                        stringBuffer.append("${njj.diastolicPressure} ")
                    }
                    text.text=stringBuffer
                }
            }
            EVT_TYPE_HR_DAY -> {
                NjjProtocolHelper.getInstance().syncHeartData().subscribe { it ->
                    LogUtil.e("全天心率回调成功")
                    var stringBuffer = StringBuffer()
                    stringBuffer.append("全天心率=")
                    it.forEach { njj ->
                        stringBuffer.append("${njj.heartRate} ")
                    }
                    text.text=stringBuffer
                }

                /* NjjBleHelper.getInstance().syncBloodPressure().subscribe {
                     LogUtil.e("全天血压回调成功")
                     it.forEach { njj ->
                         LogUtil.e(njj.diastolicPressure)
                     }
                 }

                 NjjBleHelper.getInstance().syncOxData().subscribe {
                     LogUtil.e("全天血氧回调成功")
                     it.forEach { njj ->
                         LogUtil.e(njj.bloodOxy)
                     }
                 }*/
            }
            EVT_TYPE_BO_DAY -> {
                NjjProtocolHelper.getInstance().syncOxData().subscribe {


                    var stringBuffer = StringBuffer()
                    stringBuffer.append("全天血氧=")
                    it.forEach { njj ->
                        stringBuffer.append("${njj.bloodOxy} ")
                    }
                    text.text=stringBuffer
                }


            }


            EVT_TYPE_ALL_DAY_FALG -> {
                NjjProtocolHelper.getInstance().syncHeartMonitor(1, object :
                    NjjWriteCallback {
                    override fun onWriteSuccess() {
                        LogUtil.e("指令发送成功")
                        text.text = "指令发送成功"
                    }

                    override fun onWriteFail() {
                        text.text = "指令发送失败"
                    }

                });
            }


            EVT_TYPE_TAKE_PHOTO -> {
                when (pos) {
                    35 -> NjjProtocolHelper.getInstance().openTakePhotoCamera(true, object :
                        NjjWriteCallback {
                        override fun onWriteSuccess() {
                            LogUtil.e("指令发送成功")
                            text.text = "指令发送成功"
                        }

                        override fun onWriteFail() {
                            text.text = "指令发送失败"
                        }

                    });
                    36 -> NjjProtocolHelper.getInstance().openTakePhotoCamera(false, object :
                        NjjWriteCallback {
                        override fun onWriteSuccess() {
                            LogUtil.e("指令发送成功")
                            text.text = "指令发送成功"
                        }

                        override fun onWriteFail() {
                            text.text = "指令发送失败"
                        }

                    });
                }

            }

            //消息推送
            EVT_TYPE_ALERT_MSG -> {
                NjjProtocolHelper.getInstance().setNotify(4, "你好", "我是否会", object :
                    NjjWriteCallback {
                    override fun onWriteSuccess() {
                        LogUtil.e("指令发送成功")
                        text.text = "指令发送成功"
                    }

                    override fun onWriteFail() {
                        text.text = "指令发送失败"
                    }

                });
            }

            //联系人推送
            EVT_TYPE_OTA_START -> {

            }

            EVT_TYPE_ADD_FRIEND -> {
                NjjProtocolHelper.getInstance().pushQRCode(0, "https://blog.csdn.net").subscribe {
                    if (it == 1) {
                        LogUtil.e("推送成功")
                        text.text = "推送成功"
                    }
                }
            }

            EVT_TYPE_RECEIPT_CODE -> {
                NjjProtocolHelper.getInstance().pushPayCode(12, "https://blog.csdn.net").subscribe {
                    LogUtil.e("推送成功")
                    text.text = "推送成功"
                }
            }
            EVT_TYPE_ANDROID_PHONE_CTRL -> {
                when (pos) {
                    43 -> NjjProtocolHelper.getInstance().handUpPhone(0, object :
                        NjjWriteCallback {
                        override fun onWriteSuccess() {
                            LogUtil.e("指令发送成功")
                            text.text = "指令发送成功"
                        }

                        override fun onWriteFail() {
                            LogUtil.e("指令发送失败")
                            text.text = "指令发送失败"
                        }

                    });
                    44 -> NjjProtocolHelper.getInstance().handUpPhone(1, object :
                        NjjWriteCallback {
                        override fun onWriteSuccess() {
                            LogUtil.e("指令发送成功")
                            text.text = "指令发送成功"
                        }

                        override fun onWriteFail() {
                            LogUtil.e("指令发送失败")
                            text.text = "指令发送失败"
                        }

                    })
                }
            }

            EVT_TYPE_APP_REQUEST_SYNC -> {
                NjjProtocolHelper.getInstance().syncHomeData(object : NjjHomeDataCallBack {
                    override fun homeDataCallBack(
                        njjEcgData: NjjEcgData,
                        njjHeartData: NjjHeartData,
                        njjStepData: NjjStepData,
                        njjBloodPressure: NjjBloodPressure,
                        njjBloodOxyData: NjjBloodOxyData
                    ) {
                        LogUtil.e("njjStepData={${njjStepData.stepNum}}")
                    }
                })
            }

            EVT_TYPE_WOMEN_HEALTH -> {
                var time = System.currentTimeMillis() - 1000
                var date = Date()
                date.time = System.currentTimeMillis()
                NjjProtocolHelper.getInstance().syncFemaleHealth(1, 3, 29, 7, time, date, 1, time)

                NjjProtocolHelper.getInstance().syncRealTimeECG(false, object : NjjECGCallBack {
                    override fun onReceivePPGData(type: Int, time: Int, heart: Int) {
                        LogUtil.e("type=$type  heart$heart")
                    }

                    override fun onECGReceiveEnd(type: Int) {
                        LogUtil.e("type=$type")
                    }

                })
            }

            EVT_TYPE_ECG_HR -> {
                NjjProtocolHelper.getInstance().syncRealTimeECG(true, object : NjjECGCallBack {
                    override fun onReceivePPGData(type: Int, time: Int, heart: Int) {
                        LogUtil.e("type=$type  heart$heart")
                    }

                    override fun onECGReceiveEnd(type: Int) {
                        LogUtil.e("type=$type")
                    }

                })
            }
            EVT_TYPE_DEVICE_FUN->{
                NjjProtocolHelper.getInstance().getDeviceFun(object :NjjDeviceFunCallback{
                    override fun onDeviceFunSuccess(bleDeviceFun: BleDeviceFun) {
                        Log.e("fan","fan$bleDeviceFun")
                    }

                    override fun onDeviceFunFail() {

                    }

                })
            }
            EVT_TYPE_BAND_CONFIG1->{
                NjjProtocolHelper.getInstance().getDeviceConfig1(object : NjjConfig1CallBack{
                    override fun onWriteSuccess() {

                    }

                    override fun onLongSitEntity(njjLongSitEntity: NjjLongSitEntity) {
                         LogUtil.e(njjLongSitEntity.toString())
                    }

                    override fun onNjjDrinkWaterEntity(njjDrinkWaterEntity: NjjDrinkWaterEntity) {
                        LogUtil.e(njjDrinkWaterEntity.toString())
                    }

                    override fun onNjjWashHandEntity(njjWashHandEntity: NjjWashHandEntity) {
                        LogUtil.e(njjWashHandEntity.toString())
                    }

                    override fun onNjjWristScreenEntity(njjWristScreenEntity: NjjWristScreenEntity) {
                        LogUtil.e(njjWristScreenEntity.toString())
                    }

                    override fun onNjjDisturbEntity(njjDisturbEntity: NjjDisturbEntity) {
                        LogUtil.e(njjDisturbEntity.toString())
                    }

                    override fun onNjjMedicineEntity(njjMedicineEntity: NjjMedicineEntity) {
                        LogUtil.e(njjMedicineEntity.toString())
                    }

                    override fun onFail() {

                    }

                })
            }
        }
    }

    fun userSpinnerSelect(pos: Int, cmdType: Int) {
        when (cmdType) {
            EVT_TYPE_LANGUAGE -> {
//                NjjBleHelper.getInstance().setDeviceLanguage(pos)
//                NjjBleHelper.getInstance().setDeviceLanguage()
                // BLEDeviceManager.get().writeData(data)
                //LogUtil.d("userSelect")
            }
        }
    }

}