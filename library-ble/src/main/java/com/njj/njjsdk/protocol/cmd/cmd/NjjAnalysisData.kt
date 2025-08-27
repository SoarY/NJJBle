package com.njj.njjsdk.protocol.cmd.cmd

import com.njj.njjsdk.callback.*
import com.njj.njjsdk.entity.BleBigFileEntity
import com.njj.njjsdk.entity.RecordingDataEntity
import com.njj.njjsdk.protocol.cmd.EVT_TYPE_GPS_SPORT
import com.njj.njjsdk.protocol.cmd.EVT_TYPE_SD_BOOK_LIST
import com.njj.njjsdk.protocol.cmd.EVT_TYPE_SD_MUSIC_LIST
import com.njj.njjsdk.protocol.cmd.MusicConst
import com.njj.njjsdk.protocol.cmd.TypeConstant.*
import com.njj.njjsdk.protocol.cmd.uitls.TimeUtil
import com.njj.njjsdk.protocol.entity.*
import com.njj.njjsdk.utils.*
import java.math.BigDecimal
import java.util.*

/**
 * @ClassName NjjAnalysisData
 * @Description TODO
 * @Author  Darcy
 * @Date 2022/7/25 15:36
 * @Version 1.0
 */
object NjjAnalysisData {

    fun parserFirmware(byteArray: ByteArray): String? {
        val resLength = byteArray[3].toInt() and (0xff)
        val resultByte = ByteArray(resLength)
        System.arraycopy(byteArray, 4, resultByte, 0, resLength)
        return ByteAndStringUtil.hexStringToString(
            ByteAndStringUtil.bytesToHexString(
                resultByte
            )
        )
    }

    fun parserUNIT(byteArray: ByteArray): String {
        return if (byteArray.get(4).toInt() === 1) "英制" else "公制"
    }

    fun parserTimeMode(byteArray: ByteArray): String {
        return if (byteArray[4].toInt() === 1) "12h制" else "24h制"
    }

    fun parserTempMode(byteArray: ByteArray): String {
        return if (byteArray[4].toInt() === 1) "华氏度" else "摄氏度"
    }

    fun parsertBattery(byteArray: ByteArray):Int {
        return return byteArray[4].toInt() and (0xff);
    }

    fun parserHourStep(byteArray: ByteArray): ArrayList<NjjStepData> {
        val currentDate = TimeUtil.getCurrentDate()
        var stepDataList = arrayListOf<NjjStepData>()
        for (index in 1..24) {
            val step1 = byteArray[4 * index].toInt() and (0xff)
            val step2 = (byteArray[4 * index + 1].toInt() and (0xff)).shl(8)
            val data = step1 + step2
            val cal1 = byteArray[4 * index + 2].toInt() and (0xff)
            val cal2 = (byteArray[4 * index + 3].toInt() and (0xff)).shl(8)
            val cal = BigDecimal(cal1 + cal2).divide(BigDecimal(10))
                .setScale(2, BigDecimal.ROUND_DOWN).toDouble()
            val timeMill = currentDate + (index - 1) * 60 * 60

            if (data != 0) {
                val stepData = NjjStepData()
                stepData.timeMill = timeMill
                stepData.stepNum = data.toString()
                stepData.calData = cal.toString()
                stepDataList.add(stepData)
            }
        }
        LogUtil.e("分时步数$stepDataList")
        return stepDataList
    }

    fun parserWeekSport(byteArray: ByteArray) : List<NjjStepData>? {

        var njjWeekSportData=NjjWeekSportData()
        var stepDatas= arrayListOf<NjjStepData>()
        if (byteArray.size <100) {
            LogUtil.e("七天数据不正常长度不正确...")
            return stepDatas
        } else {
            //运动数据的时间戳
            val date1 = byteArray[4].toInt() and (0xff)
            val date2 = (byteArray[5].toInt() and (0xff)).shl(8)
            val date3 = (byteArray[6].toInt() and (0xff)).shl(16)
            val date4 = (byteArray[7].toInt() and (0xff)).shl(24)
            var currentDate = date1 + date2 + date3 + date4

            val dateString = DateUtil.long2String(currentDate * 1000L, "yyyy-MM-dd")
            val timeStamp = DateUtil.String2YYLong(dateString) / 1000L
            LogUtil.e("运动时间戳=$timeStamp")

            //全天总步数
            val step1 = byteArray[8].toInt() and (0xff)
            val step2 = (byteArray[9].toInt().and(0xff)).shl(8)
            val step3 = (byteArray[10].toInt() and (0xff)).shl(16)
            val step4 = (byteArray[11].toInt().and(0xff)).shl(24)
            val steps = step1 + step2 + step3 + step4
            LogUtil.e("全天总步数=$steps")

            //全天总距离
            val dis0 = byteArray[12].toInt() and (0xff)
            val dis1 = (byteArray[13].toInt().and(0xff)).shl(8)
            val dis2 = (byteArray[14].toInt() and (0xff)).shl(16)
            val dis3 = (byteArray[15].toInt().and(0xff)).shl(24)
            val dis = (dis0 + dis1 + dis2 + dis3) / 10
            LogUtil.e("全天总距离=$dis")

            //全天总卡路里 (kcal )  10 sport_kcal = 1kcal
            val car0 = byteArray[16].toInt() and (0xff)
            val car1 = (byteArray[17].toInt().and(0xff)).shl(8)
            val car2 = (byteArray[18].toInt() and (0xff)).shl(16)
            val car3 = (byteArray[19].toInt().and(0xff)).shl(24)
            val cars = (car0 + car1 + car2 + car3) / 10
            LogUtil.e("全天总卡路里=$cars")
            //全天运动总时长
            val time0 = byteArray[20].toInt() and (0xff)
            val time1 = (byteArray[21].toInt().and(0xff)).shl(8)
            val time2 = (byteArray[22].toInt() and (0xff)).shl(16)
            val time3 = (byteArray[23].toInt().and(0xff)).shl(24)
            val times = (time0 + time1 + time2 + time3) / 10
            LogUtil.e("全天运动总时长=$times")

            njjWeekSportData.timeMill=timeStamp
            njjWeekSportData.distancce=dis
            njjWeekSportData.step=steps
            njjWeekSportData.car= cars.toLong()
            njjWeekSportData.duration=times


            for (index in 24..70 step 2) {
                val step1 = byteArray[index].toInt() and (0xff)
                val step2 = (byteArray[index + 1].toInt() and (0xff)).shl(8)
                val data = step1 + step2
                if (data != 0) {
                    val cal1 = byteArray[index + 48].toInt() and (0xff)
                    val cal2 = (byteArray[index + 1 + 48].toInt() and (0xff)).shl(8)
                    val cal = (cal1 + cal2) / 10
                    val stepData = NjjStepData()
                    val timeMill = timeStamp + (index - 23) / 2 * 60 * 60
                    stepData.timeMill = timeMill.toInt()
                    stepData.stepNum = data.toString()
                    stepData.calData = cal.toString()
                    stepData.distance = dis.toString()
                    stepDatas.add(stepData)
                }
            }


        }
         return stepDatas
    }

    fun parserBoDay(byteArray: ByteArray) :List<NjjBloodOxyData>{
        if (byteArray.size != 53) {
            LogUtil.e("全天血氧长度不正确...")
        }
        val currentDate = TimeUtil.getCurrentDate()
        var njjBloodOxyDataList = arrayListOf<NjjBloodOxyData>()
        for (index in 1..48) {
            val data = byteArray[index + 3].toInt() and (0xff)
            val timeMill = currentDate + (index - 1) * 60 * 30
            if (data != 0) {
                val oxData = NjjBloodOxyData()
                oxData.timeMill = timeMill
                oxData.bloodOxy = data.toString()
                njjBloodOxyDataList.add(oxData)
            }
        }

        return njjBloodOxyDataList
    }

    fun parserHrDay(byteArray: ByteArray) :List<NjjHeartData> {
        if (byteArray.size != 53) {
            LogUtil.e("全天心率长度不正确...")
        }
        val currentDate = TimeUtil.getCurrentDate()
        var njjHeartDataList = arrayListOf<NjjHeartData>()
        for (index in 1..48) {
            val data = byteArray[index + 3].toInt() and (0xff)
            val timeMill = currentDate + (index - 1) * 60 * 30
            if (data != 0) {
                val heartData = NjjHeartData()
                heartData.timeMill = timeMill
                heartData.heartRate = data.toString()
                njjHeartDataList.add(heartData)
            }
        }

        return njjHeartDataList
    }

    fun parserSportData(byteArray: ByteArray) :NjjStepData{
        if (byteArray.size != 21) {
            LogUtil.e("运动数据不正常")
        }
        val step1 = byteArray[4].toInt() and 0xff
        val step2 = (byteArray[5].toInt() and 0xff).shl(8)
        val step3 = (byteArray[6].toInt() and 0xff).shl(16)
        val step4 = (byteArray[7].toInt() and 0xff).shl(24)
        val totalStep = step1 + step2 + step3 + step4
        LogUtil.e("运动步数为$totalStep")

        val distance1 = byteArray[8].toInt() and 0xff
        val distance2 = (byteArray[9].toInt() and 0xff).shl(8)
        val distance3 = (byteArray[10].toInt() and 0xff).shl(16)
        val distance4 = (byteArray[11].toInt() and 0xff).shl(24)
        val totalDistance =
            BigDecimal(distance1 + distance2 + distance3 + distance4).divide(BigDecimal(10))
                .setScale(2, BigDecimal.ROUND_DOWN).toDouble()
        LogUtil.e("运动距离为$totalDistance")

        val kcal1 = byteArray[12].toInt() and 0xff
        val kcal2 = (byteArray[13].toInt() and 0xff).shl(8)
        val kcal3 = (byteArray[14].toInt() and 0xff).shl(16)
        val kcal4 = (byteArray[15].toInt() and 0xff).shl(24)
        val totalKcal = (kcal1 + kcal2 + kcal3 + kcal4) / 10
        LogUtil.e("卡路里$totalKcal")


        val time1 = byteArray[16].toInt() and 0xff
        val time2 = (byteArray[17].toInt() and 0xff).shl(8)
        val time3 = (byteArray[18].toInt() and 0xff).shl(16)
        val time4 = (byteArray[19].toInt() and 0xff).shl(24)
        val totalTime = FormateUtil.formatedDoubleNum(
            (time1 + time2 + time3 + time4).toString().toDouble() / 1000 * 30
        )


        LogUtil.e("运动时长为$totalTime")
        val stepData = NjjStepData()

        stepData.timeMill = (System.currentTimeMillis() / 1000).toInt()
        stepData.stepNum = totalStep.toString()
        stepData.distance = totalDistance.toString()
        stepData.calData = totalKcal.toString()
        stepData.duration = totalTime.toString()
        return stepData
    }

    fun parserSleepData(byteArray: ByteArray): NjjSleepInfo {

        var sleepInfo = NjjSleepInfo()

        when (val length = byteArray[3].toInt() and (0xff)) {

            1 -> {
                LogUtil.i("no sleep data")
            }

            else -> {
              /*  //清醒时长（分钟）
                val sorb = byteArray[4].toInt() and (0xff)
                val sorb1 = ((byteArray[5].toInt() and (0xff)).shl(8))
                val sorb2 = ((byteArray[6].toInt() and (0xff)).shl(16))
                val sorb3 = ((byteArray[7].toInt() and (0xff)).shl(24))
                val sorbT = sorb + sorb1 + sorb2 + sorb3

                //浅睡时长（分钟）
                val light = byteArray[8].toInt() and (0xff)
                val light1 = ((byteArray[9].toInt() and (0xff)).shl(8))
                val light2 = ((byteArray[10].toInt() and (0xff)).shl(16))
                val light3 = ((byteArray[11].toInt() and (0xff)).shl(24))
                val lightT = light + light1 + light2 + light3

                //深睡时长（分钟）
                val deep = byteArray[12].toInt() and (0xff)
                val deep1 = ((byteArray[13].toInt() and (0xff)).shl(8))
                val deep2 = ((byteArray[14].toInt() and (0xff)).shl(16))
                val deep3 = ((byteArray[15].toInt() and (0xff)).shl(24))
                val deepT = deep + deep1 + deep2 + deep3
*/
                //入睡时间戳（秒）
                val beginTime1 = byteArray[16].toInt() and (0xff)
                val beginTime2 = ((byteArray[17].toInt() and (0xff)).shl(8))
                val beginTime3 = ((byteArray[18].toInt() and (0xff)).shl(16))
                val beginTime4 = ((byteArray[19].toInt() and (0xff)).shl(24))
                val beginTime =
                    beginTime1 + beginTime2 + beginTime3 + beginTime4 - DateUtil.getOffset()
//                LogUtil.d("ry 入睡时间:  beginTime=$beginTime")

                //醒来时间戳（秒）
                val endTime1 = byteArray[20].toInt() and (0xff)
                val endTime2 = ((byteArray[21].toInt() and (0xff)).shl(8))
                val endTime3 = ((byteArray[22].toInt() and (0xff)).shl(16))
                val endTime4 = ((byteArray[23].toInt() and (0xff)).shl(24))
                val endTime = endTime1 + endTime2 + endTime3 + endTime4  - DateUtil.getOffset()
//                LogUtil.d("ry 醒来时间： endTime=${endTime}")

                //根据入睡时间和醒来时间获取睡眠时间戳
                val calendar = Calendar.getInstance(TimeZone.getDefault())
                calendar.timeInMillis = beginTime * 1000L
                val startHour = calendar.get(Calendar.HOUR_OF_DAY)
                var timeStamp = when {
                    startHour > 21 -> {
                        beginTime + 10 * 60 * 60 * 1L
                    }
                    else -> {
                        beginTime * 1L
                    }
                }
                calendar.timeInMillis = timeStamp
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)

                var sorbeTime = 0
                var lightTime = 0
                var deepTime = 0
                var start = 0L
                val detailList = arrayListOf<NjjSleepDetail>()
                val size = length + 3

                for (i in 24..size step 2) {
                    val type = byteArray[i].toInt() and (0xff)
                    val data = byteArray[i + 1].toInt() and (0xff)

                    if (start <= 0L) {
                        start = beginTime * 1L
                    }

                    when (type) {
                        0 -> {
                            //清醒
                            if(detailList.size==0){
                                continue
                            }
                            //清醒
                            sorbeTime += data
                        }
                        1 -> {
                            //浅睡
                            lightTime += data
                        }
                        2 -> {
                            //深睡
                            deepTime += data
                        }
                    }

                    val sleepDetail = NjjSleepDetail()
                    sleepDetail.startTime = start
                    sleepDetail.endTime = start + data*60
                    sleepDetail.status = type
                    sleepDetail.duration = data
                    sleepDetail.timeMill = timeStamp.toInt()
                    start += data * 60

                    detailList.add(sleepDetail)
                    if (sleepDetail.endTime >= endTime) {
                        break
                    }

                }

                sleepInfo.beginTime = beginTime
                sleepInfo.endTime = endTime

                var long2String = DateUtil.long2String(endTime * 1000L, "yyyy-MM-dd")
                sleepInfo.date = DateUtil.String2long(long2String, "yyyy-MM-dd").toInt().toString()

                sleepInfo.deepSleepTime = deepTime
                sleepInfo.lightSleepTime = lightTime
                sleepInfo.weekTimes = sorbeTime
                sleepInfo.timeMill = timeStamp.toInt()
                sleepInfo.sleepTime = deepTime + lightTime
                sleepInfo.sleepDetailList=detailList
            }
        }

        return sleepInfo;
    }

    fun parserSportRecord(byteArray: ByteArray): NjjRunDetailsInfoData {
        var  njySportData = NjjRunDetailsInfoData()
        when (byteArray[3].toInt() and (0xff)) {
            1 -> {
                //无运动数据
                LogUtil.i("no Sport data！")

            }
            else -> {
                val index = 4
                //运动类型
                val sportType = byteArray[index]

                val sportHr = byteArray[index + 1] .toInt() and (0xff)

                val sportAvgHr = byteArray[index + 2]

                val sportCustom = byteArray[index + 3]

                val startTime = byteArray[index + 4].toInt() and 0xff
                val startTime2 = (byteArray[index + 5].toInt() and 0xff).shl(8)
                val startTime3 = (byteArray[index + 6].toInt() and 0xff).shl(16)
                val startTime4 = (byteArray[index + 7].toInt() and 0xff).shl(24)
                val startTimeStamp = startTime + startTime2 + startTime3 + startTime4

                val endTime = (byteArray[index + 8].toInt() and 0xff)
                val endTime2 = (byteArray[index + 9].toInt() and 0xff).shl(8)
                val endTime3 = (byteArray[index + 10].toInt() and 0xff).shl(16)
                val endTime4 = (byteArray[index + 11].toInt() and 0xff).shl(24)
                val endTimeStamp = (endTime + endTime2 + endTime3 + endTime4) - 8 * 60 * 60


                val sportTime = (byteArray[index + 12].toInt() and 0xff)
                val sportTime2 = (byteArray[index + 13].toInt() and 0xff).shl(8)
                val sportTime3 = (byteArray[index + 14].toInt() and 0xff).shl(16)
                val sportTime4 = (byteArray[index + 15].toInt() and 0xff).shl(24)
                val sportTimeDuration = sportTime + sportTime2 + sportTime3 + sportTime4

                val sportStep = (byteArray[index + 16].toInt() and 0xff)
                val sportStep2 = (byteArray[index + 17].toInt() and 0xff).shl(8)
                val sportStep3 = (byteArray[index + 18].toInt() and 0xff).shl(16)
                val sportStep4 = (byteArray[index + 19].toInt() and 0xff).shl(24)
                val sportSteps = sportStep + sportStep2 + sportStep3 + sportStep4

                val sportCal = (byteArray[index + 20].toInt() and 0xff)
                val sportCal2 = (byteArray[index + 21].toInt() and 0xff).shl(8)
                val sportCal3 = (byteArray[index + 22].toInt() and 0xff).shl(16)
                val sportCal4 = (byteArray[index + 23].toInt() and 0xff).shl(24)
                val sportCals = (sportCal + sportCal2 + sportCal3 + sportCal4) / 10

                val sportDistance = (byteArray[index + 24].toInt() and 0xff)
                val sportDistance2 = (byteArray[index + 25].toInt() and 0xff).shl(8)
                val sportDistance3 = (byteArray[index + 26].toInt() and 0xff).shl(16)
                val sportDistance4 = (byteArray[index + 27].toInt() and 0xff).shl(24)
                val sportDistances =
                    (sportDistance + sportDistance2 + sportDistance3 + sportDistance4) / 10

                njySportData.heartRate = sportHr
                njySportData.stepNum = sportSteps
                njySportData.kcal = sportCals
                njySportData.distance = sportDistances
                njySportData.duration = sportTimeDuration
                njySportData.date = DateUtil.long2String(
                    endTimeStamp * 1000L,
                    "yyyy-MM-dd HH:mm:ss"
                )
                njySportData.timeMill = endTimeStamp
                LogUtil.i(
                    "Neo 数据解析：（model：" + sportType.toInt()
                            + ",xl:" + njySportData.heartRate + ",bs:" + njySportData.stepNum + ",kcal:"
                            + njySportData.kcal + ",lc:" + njySportData.distance + ",hs:" + njySportData.duration
                            + ",date:" + njySportData.date + ",timeMill:" + njySportData.timeMill + ")"
                )
                // add by Neo 2022-5-21 17:31:30
                // njy自己定义的运动类型定义，CmdConst的类型是历史数据类型，NjySportConst为嵌入端定义的数据类型，为了兼容历史版本，所以有以下代码
                //为兼容查询运动数据
                njySportData.model=sportType.toInt()
            }
        }
        return njySportData
    }

    fun parserBpDay(byteArray: ByteArray) :List<NjjBloodPressure> {
        val currentDate = TimeUtil.getCurrentDate()
        var njjBloodOxyDataList = arrayListOf<NjjBloodPressure>()
        for (index in 1..48) {
            val diastolicPressure = byteArray[2 * index + 2].toInt() and (0xff)
            val systolicPressure = byteArray[2 * index + 3].toInt() and (0xff)
            val timeMill = currentDate + (index - 1) * 60 * 30
            if (diastolicPressure != 0 && systolicPressure != 0) {
                val bloodPressure = NjjBloodPressure()
                bloodPressure.timeMill = timeMill
                bloodPressure.diastolicPressure = diastolicPressure.toString()
                bloodPressure.systolicPressure = systolicPressure.toString()
                njjBloodOxyDataList.add(bloodPressure)
            }
        }
        LogUtil.d("全天血压${njjBloodOxyDataList}")
        return njjBloodOxyDataList
    }

    fun parserDeviceInfo(byteArray: ByteArray): NjjDeviceInfoData {
        val timeStamp1 = (byteArray[4].toInt() and 0xff)
        val timeStamp2 = (byteArray[5].toInt() and 0xff).shl(8)
        val timeStamp3 = (byteArray[6].toInt() and 0xff).shl(16)
        val timeStamp4 = (byteArray[7].toInt() and 0xff).shl(24)
        val timeStamp = timeStamp1 + timeStamp2 + timeStamp3 + timeStamp4


        val sex = (byteArray[8].toInt() and 0xff)
        val height = (byteArray[9].toInt() and 0xff)
        val weight = (byteArray[10].toInt() and 0xff)
        val age = (byteArray[11].toInt() and 0xff)
        val day = (byteArray[12].toInt() and 0xff)
        val month = (byteArray[13].toInt() and 0xff)

        val year1 = (byteArray[14].toInt() and 0xff)
        val year2 = (byteArray[15].toInt() and 0xff).shl(8)
        var year=year1+year2

        val isBind = (byteArray[16].toInt() and 0xff)
        val resetFlagCount  = (byteArray[17].toInt() and 0xff)
        val languageId = byteArray[18].toInt() and (0xff)
        val currentDialID = byteArray[19].toInt() and (0xff)
        val totalDialCount = byteArray[20].toInt() and (0xff)
        val timeFormat = byteArray[22].toInt() and (0xff)
        val unitFormat = byteArray[23].toInt() and (0xff)
        val tempFormat = byteArray[24].toInt() and (0xff)
        val displayTime = byteArray[25].toInt() and (0xff)
        val vibrationLevel = byteArray[26].toInt() and (0xff)
        val powerSave = byteArray[27].toInt() and (0xff)
        val weather = byteArray[28].toInt() and (0xff)
        val allDayHr = byteArray[29].toInt() and (0xff)
        val allDayBo2 = byteArray[30].toInt() and (0xff)
        val allDayTemp = byteArray[31].toInt() and (0xff)

        //32->35  //消息和电话通知(每个 Bit 代表一个开关，bit0 代表电话通知)
        //36->39  //密码
        val appSystemType = byteArray[40].toInt() and (0xff)
        val byte = ByteArray(4)
        System.arraycopy(byteArray,32,byte,0,4)
//        var passWard=ByteAndStringUtil.bytesToHexString(byte)

        var njjDeviceInfoData=NjjDeviceInfoData()
        njjDeviceInfoData.timeStamp=timeStamp.toString()
        njjDeviceInfoData.sex=sex
        njjDeviceInfoData.height=height
        njjDeviceInfoData.weight=weight
        njjDeviceInfoData.age=age
        njjDeviceInfoData.day=day
        njjDeviceInfoData.month=month
        njjDeviceInfoData.year=year
        njjDeviceInfoData.isBind=isBind
        njjDeviceInfoData.resetFlagCount=resetFlagCount

        njjDeviceInfoData.language=languageId
        njjDeviceInfoData.currentDialID=currentDialID
        njjDeviceInfoData.totalDialCount=totalDialCount
        njjDeviceInfoData.timeFormat=timeFormat
        njjDeviceInfoData.unitFormat=unitFormat
        njjDeviceInfoData.tempFormat=tempFormat
        njjDeviceInfoData.displayTime=displayTime
        njjDeviceInfoData.vibrationLevel=vibrationLevel
        njjDeviceInfoData.powerSave=powerSave
        njjDeviceInfoData.weather=weather
        njjDeviceInfoData.allDayHr=allDayHr
        njjDeviceInfoData.allDayBo2=allDayBo2
        njjDeviceInfoData.allDayTemp=allDayTemp
//        njjDeviceInfoData.passward=passWard

        return njjDeviceInfoData

    }

    fun parserGetAlarm(byteArray: ByteArray): java.util.ArrayList<NjjAlarmClockInfo> {
        val data = ArrayList<NjjAlarmClockInfo>(4)
        if (byteArray.size != 26) {
//                    RxNjjBleManger.getInstance().getAlarmClockInfo()
            LogUtil.e("ry 闹钟数据长度不对...")
            return data
        }

        for (i in 1..4) {
            val info = NjjAlarmClockInfo()
            info.isAlarmState = byteArray[4 * i + i].toInt() == 1
            info.alarmFlag = (i - 1).toString()
            info.type = byteArray[4 * i + i + 1].toInt()
            info.alarmCycle = byteArray[4 * i + i + 2].toInt() and (0xff)
            val i1 = (byteArray[4 * i + i + 3].toInt() and (0xff)) * 60
            val i2 = (byteArray[4 * i + i + 4].toInt()) and (0xff)
            info.alarmTime = (i1 + i2) * 60
            info.isDeleteFlag = (byteArray[4 * i + i + 1].toInt() and (0xff)) == 0

            data.add(info)
        }
        return data
    }

    fun parserHomeData(byteArray: ByteArray,njjHomeDataCallBack: NjjHomeDataCallBack) {
        val data1 = byteArray[4].toInt() and (0xff)
        val data2 = (byteArray[5].toInt() and (0xff)).shl(8)
        val data3 = (byteArray[6].toInt() and (0xff)).shl(16)
        val data4 = (byteArray[6].toInt() and (0xff)).shl(16)
        val steps = data1 + data2 + data3 + data4

        val cal0 = byteArray[8].toInt() and (0xff)
        val cal1 = (byteArray[9].toInt().and(0xff)).shl(8)
        val cal2 = (byteArray[10].toInt() and (0xff)).shl(16)
        val cal3 = (byteArray[11].toInt().and(0xff)).shl(24)
        val calories = (cal0 + cal1 + cal2 + cal3) / 10
        val dis0 = byteArray[12].toInt() and (0xff)
        val dis1 = (byteArray[13].toInt().and(0xff)).shl(8)
        val dis2 = (byteArray[14].toInt() and (0xff)).shl(16)
        val dis3 = (byteArray[15].toInt().and(0xff)).shl(24)
        val dis = (dis0 + dis1 + dis2 + dis3) / 10

        val heart = byteArray[16].toInt() and (0xff)
        val oxData = byteArray[17].toInt() and (0xff)
        val systolicPressure = byteArray[18].toInt() and (0xff)
        val diastolicPressure = byteArray[19].toInt() and (0xff)

        //首页心电
        val ecg = byteArray[20].toInt() and (0xff)
        var time = (System.currentTimeMillis() / 1000).toInt()
        val ecgData= NjjEcgData()
        ecgData.timeMill=time
        ecgData.testTimeMill=time
        ecgData.ecgval=ecg.toString()

        //首页心率
        val heartData = NjjHeartData()
        heartData.heartRate = heart.toString()
        heartData.timeMill = (System.currentTimeMillis() / 1000).toInt()


        val bloodOxyData = NjjBloodOxyData()
        bloodOxyData.bloodOxy = oxData.toString()
        bloodOxyData.timeMill = (System.currentTimeMillis() / 1000).toInt()

        val bloodPressure = NjjBloodPressure()
        bloodPressure.diastolicPressure = diastolicPressure.toString()
        bloodPressure.systolicPressure = systolicPressure.toString()
        bloodPressure.timeMill = (System.currentTimeMillis() / 1000).toInt()

        val stepData = NjjStepData()
        stepData.calData = calories.toString()
        stepData.stepNum = steps.toString()
        stepData.distance = dis.toString()
        stepData.duration =
            FormateUtil.formatedDoubleNum(dis.toString().toDouble() / 1000 * 15 + 0.5)
        stepData.timeMill = (System.currentTimeMillis() / 1000).toInt()

        njjHomeDataCallBack.homeDataCallBack(ecgData,heartData,stepData,bloodPressure,bloodOxyData)

    }

    fun parserOTAEnd(byteArray: ByteArray, homeDataCallBack: NjjPushOtaCallback) {
        val data = byteArray[4].toInt() and (0xff)
        LogUtil.e("mPkg=：$data")
        when (data) {
            0 -> homeDataCallBack.onPushSuccess()
            1 -> {
                var date1 = byteArray[5].toInt() and (0xff)
                var date2 = (byteArray[6].toInt() and (0xff)).shl(8)
                var pkg=date1+date2
                homeDataCallBack.onPushError(data, pkg)
                LogUtil.e("mPkg=${pkg}")
                LogUtil.e("mPkg= " + ByteAndStringUtil.bytesToHexString(byteArray))
            }
            else -> {
                homeDataCallBack.onPushError(data, 0)
            }
        }
    }

    fun parserOTAStart(byteArray: ByteArray, njjPushOtaCallback: NjjPushOtaCallback) {

        when (byteArray[4].toInt() and (0xff)) {
            0x00 -> {//自定义表盘
                LogUtil.e("开始写入自定义表盘")
            }
            //表盘校验成功，开始推送表盘数据
            0x01 -> {//表盘
                LogUtil.e("开始写入表盘")
            }

            0x02 -> {//联系人
                LogUtil.e("开始写入联系人")
            }

            0x03 -> {//固件UI升级
                LogUtil.e("开始UI表盘")
            }
            0x04->{
                LogUtil.e("开始写入游戏")
            }
            0x05->{  //核酸码
                LogUtil.e("开始写入联系人")
            }
            0x07->{
                LogUtil.e("开始写入Logo")
            }
            0x09->{  //字体
                LogUtil.e("开始写入字体")
            }
            0x0A->{
                LogUtil.e("开始写入杰里二代ota")
            }
        }

        njjPushOtaCallback.onPushStart(byteArray[4].toInt() and (0xff))

    }

    fun parserECGData(byteArray: ByteArray, callBack: NjjECGCallBack) {
        var type=byteArray[4].toInt()
        var heart=byteArray[5].toInt()
        var currentTimeMillis = System.currentTimeMillis()/1000L
        // type ：0 : 测量中 1:未佩戴  2: 测量完成结果 3.固件主动停止测量
        //BC 78 03 02 00 52 52
        when (type) {
            0 -> {
                // 0 : 测量中
                callBack.onReceivePPGData(type, currentTimeMillis.toInt(),heart)
            }
            1 -> {
                callBack.onECGReceiveEnd(type)
            }
            2 -> {
                callBack.onReceivePPGData(type, currentTimeMillis.toInt(),heart)
            }
            3 -> {
                callBack.onECGReceiveEnd(type)
            }
        }
    }

    fun parserMacData(value: ByteArray): String {
        //BC7A03100F5DE3870BEB57617463682043616C6C5F

        var mac1 = ByteAndStringUtil.bytesToHexString(value[9])
        var mac2 = ByteAndStringUtil.bytesToHexString(value[8])
        var mac3 = ByteAndStringUtil.bytesToHexString(value[7])
        var mac4 = ByteAndStringUtil.bytesToHexString(value[6])
        var mac5 = ByteAndStringUtil.bytesToHexString(value[5])
        var mac6 = ByteAndStringUtil.bytesToHexString(value[4])

        return "$mac6:$mac5:$mac4:$mac3:$mac2:$mac1"
    }

    fun parserBtName(byteArray: ByteArray, btNameCallback: NjBtNameCallback) {
        val resLength = byteArray[3].toInt() and (0xff)
        val resultByte = ByteArray(resLength)
        System.arraycopy(byteArray, 4, resultByte, 0, resLength)
        val result: String = String(resultByte)
        btNameCallback.onBtNameSuccess(result)
    }

    fun parserDeviceFunData(
        byteArray: ByteArray,
        njjDeviceFunCallback: NjjDeviceFunCallback
    ) {
        val bleDeviceFun= BleDeviceFun()
        var lcdHeight  =byteArray[4].toInt().and(0xff) +byteArray[5].toInt().and(0xff).shl(8)
        var lcdWidth  =byteArray[6].toInt().and(0xff) +byteArray[7].toInt().and(0xff).shl(8)

        var previewHeight  =byteArray[8].toInt().and(0xff) +byteArray[9].toInt().and(0xff).shl(8)
        var previewWidth  =byteArray[10].toInt().and(0xff) +byteArray[11].toInt().and(0xff).shl(8)

        var  lcdType=byteArray[12]
        var  lcdRadian=byteArray[13].toInt().and(0xff)

        var videoByte = byteArray[18]
        var isNoTimeByte = byteArray[19]
        var supportCustomDial=byteArray[20]
        var supportDialLibrary=byteArray[21]
        var supportBtPhone=byteArray[22]
        var supportFindPhone=byteArray[23]
        var supportFindWatch=byteArray[24]
        var supportHr=byteArray[25]
        var supportBp=byteArray[26]
        var supportBo2=byteArray[27]
        var supportEcg=byteArray[28]
        var supportTemp=byteArray[29]
        var supportAlarm=byteArray[30]  //0:不支持  非0:最多闹钟个数
        var supportSchedule=byteArray[31] //0:不支持  非0:最多日程个数
        var supportLongSit=byteArray[32]
        var supportDrinkWater=byteArray[33]
        var supportWashHand=byteArray[34]
        var supportWeather=byteArray[35]
        var supportWomenHealth=byteArray[36]
        var supportMedic=byteArray[37]
        var healthQrcode=byteArray[38]

        if (byteArray.size>39){
            var supportGpsSport=byteArray[39]
            bleDeviceFun.isSupportGpsSport= supportGpsSport.toInt() != 0
        }

        if (byteArray.size>44){
            var games= arrayListOf<Int>()
            for(i in 44 .. byteArray.size-2){
                games.add(byteArray[i].toInt())
            }
            bleDeviceFun.games=games
        }

        bleDeviceFun.previewHeight=previewHeight
        bleDeviceFun.previewWidth=previewWidth

        bleDeviceFun.lcdHeight=lcdHeight
        bleDeviceFun.lcdWidth=lcdWidth

        bleDeviceFun.lcdType=lcdType.toInt()
        bleDeviceFun.lcdRadian=lcdRadian

        bleDeviceFun.isVideo= videoByte.toInt() != 0
        bleDeviceFun.isNoTime= isNoTimeByte.toInt() != 0
        bleDeviceFun.isSupportCustomDial= supportCustomDial.toInt() != 0
        bleDeviceFun.isSupportDialLibrary= supportDialLibrary.toInt() != 0
        bleDeviceFun.isSupportBtPhone= supportBtPhone.toInt() != 0
        bleDeviceFun.isSupportFindPhone= supportFindPhone.toInt() != 0
        bleDeviceFun.isSupportFindWatch= supportFindWatch.toInt() != 0

        bleDeviceFun.supportHr=supportHr.toInt()
        bleDeviceFun.supportBp=supportBp.toInt()
        bleDeviceFun.supportBo2=supportBo2.toInt()
        bleDeviceFun.supportEcg=supportEcg.toInt()
        bleDeviceFun.supportTemp=supportTemp.toInt()
        bleDeviceFun.supportAlarm=supportAlarm.toInt()
        bleDeviceFun.supportSchedule=supportSchedule.toInt()


        bleDeviceFun.isSupportLongSit= supportLongSit.toInt() != 0
        bleDeviceFun.isSupportDrinkWater= supportDrinkWater.toInt() != 0
        bleDeviceFun.isSupportWashHand= supportWashHand.toInt() != 0
        bleDeviceFun.isSupportWeather= supportWeather.toInt() != 0
        bleDeviceFun.isSupportWomenHealth= supportWomenHealth.toInt() != 0
        bleDeviceFun.isSupportMedic= supportMedic.toInt() != 0
        bleDeviceFun.isHealthQrcode= healthQrcode.toInt() != 0
        njjDeviceFunCallback.onDeviceFunSuccess(bleDeviceFun)


//        BLESDKLocalData.getInstance().keyIsNoTime = byte1.toInt()
//        BLESDKLocalData.getInstance().setIsPhotoVedio(byte2.toInt())
    }


    fun parserDeviceConfig(byteArray: ByteArray,njjConfig1CallBack: NjjConfig1CallBack){
        LogUtil.e("数据=="+ByteAndStringUtil.bytesToHexString(byteArray))
        val resLength = byteArray[3].toInt() and (0xff)
        val resultByte = ByteArray(resLength)
        System.arraycopy(byteArray, 4, resultByte, 0, resLength)
        //久坐提醒
        val longSitStatus=resultByte[88]
        val longSitInterval=resultByte[89]
        val longSitBeginHour=resultByte[90]
        val longSitBeginMin=resultByte[91]
        val longSitEndHour=resultByte[92]
        val longSitEndMin=resultByte[93]
        var longSitEntity=NjjLongSitEntity()
        longSitEntity.longSitStatus=longSitStatus.toInt()
        longSitEntity.longSitInterval=longSitInterval.toInt()
        longSitEntity.longSitBeginTime=longSitBeginHour*60+longSitBeginMin
        longSitEntity.longSitEndTime=longSitEndHour*60+longSitEndMin

        njjConfig1CallBack.onLongSitEntity(longSitEntity)
        //96->103 喝水提醒
        val drinkWaterStatus=resultByte[96]
        val drinkWaterInterval=resultByte[97]
        val drinkWaterBeginHour=resultByte[98]
        val drinkWaterBeginMin=resultByte[99]
        val drinkWaterEndHour=resultByte[100]
        val drinkWaterEndMin=resultByte[101]
        var drinkWaterEntity= NjjDrinkWaterEntity()
        drinkWaterEntity.drinkWaterStatus=drinkWaterStatus.toInt()
        drinkWaterEntity.drinkWaterInterval=drinkWaterInterval.toInt()
        drinkWaterEntity.drinkWaterBeginTime=drinkWaterBeginHour*60+drinkWaterBeginMin
        drinkWaterEntity.drinkWaterEndTime=drinkWaterEndHour*60+drinkWaterEndMin
        njjConfig1CallBack.onNjjDrinkWaterEntity(drinkWaterEntity)

        //104->111 洗手提醒
        val washHandStatus=resultByte[104]
        val washHandInterval=resultByte[105]
        val washHandBeginHour=resultByte[106]
        val washHandBeginMin=resultByte[107]
        val washHandEndHour=resultByte[108]
        val washHandEndMin=resultByte[109]
        var washHandEntity= NjjWashHandEntity()
        washHandEntity.washHandStatus=washHandStatus.toInt()
        washHandEntity.washHandInterval=washHandInterval.toInt()
        washHandEntity.washHandBeginTime=washHandBeginHour*60+washHandBeginMin
        washHandEntity.washHandEndTime=washHandEndHour*60+washHandEndMin
        njjConfig1CallBack.onNjjWashHandEntity(washHandEntity)


        //112->117 翻腕亮屏
        val wristScreenStatus=resultByte[112]
        val wristScreenInterval=resultByte[113]
        val wristScreenBeginHour=resultByte[114]
        val wristScreenBeginMin=resultByte[115]
        val wristScreenEndHour=resultByte[116]
        val wristScreenEndMin=resultByte[117]
        var wristScreenEntity= NjjWristScreenEntity()
        wristScreenEntity.wristScreenStatus=wristScreenStatus.toInt()
        wristScreenEntity.wristScreenInterval=wristScreenInterval.toInt()
        wristScreenEntity.wristScreenBeginTime=wristScreenBeginHour*60+wristScreenBeginMin
        wristScreenEntity.wristScreenEndTime=wristScreenEndHour*60+wristScreenEndMin
        njjConfig1CallBack.onNjjWristScreenEntity(wristScreenEntity)


        //118-> 123 勿扰模式
        val disturbStatus=resultByte[118]
        val disturbInterval=resultByte[119]
        val disturbBeginHour=resultByte[120]
        val disturbBeginMin=resultByte[121]
        val disturbEndHour=resultByte[122]
        val disturbEndMin=resultByte[123]
        var disturbEntity= NjjDisturbEntity()
        disturbEntity.disturbStatus=disturbStatus.toInt()
        disturbEntity.disturbInterval=disturbInterval.toInt()
        disturbEntity.disturbBeginTime=disturbBeginHour*60+disturbBeginMin
        disturbEntity.disturbEndTime=disturbEndHour*60+disturbEndMin
        njjConfig1CallBack.onNjjDisturbEntity(disturbEntity)


        //124-131  吃药提醒
        val medicineStatus=resultByte[124]
        val medicineInterval=resultByte[125]

        val medicineHour1=resultByte[126]
        val medicineMin1=resultByte[127]

        val medicineHour2=resultByte[128]
        val medicineMin2=resultByte[129]

        val medicineHour3=resultByte[130]
        val medicineMin3=resultByte[131]

        var medicineEntity=NjjMedicineEntity()
        medicineEntity.medicineStatus=medicineStatus.toInt()
        medicineEntity.medicineWeek=medicineInterval.toInt().and (0xff)
        medicineEntity.medicineTime1=medicineHour1*60+medicineMin1
        medicineEntity.medicineTime2=medicineHour2*60+medicineMin2
        medicineEntity.medicineTime3=medicineHour3*60+medicineMin3
        njjConfig1CallBack.onNjjMedicineEntity(medicineEntity)

    }

    fun parserSomatosensoryGame(byteArray: ByteArray) {

        when (byteArray[4].toInt()) {
            0x00 -> {
                // 0 游戏开始
                SomatosensoryGameCallback.onReceiveStatus(0)
            }
            0x01 -> {
                // 1：游戏结束
                SomatosensoryGameCallback.onReceiveStatus(1)
            }

            0x02 -> {
                //	2：游戏暂停
                SomatosensoryGameCallback.onReceiveStatus(2)
            }

            0x03 -> {
                // 	3：传输游戏数据


                val resLength = byteArray[3].toInt() and (0xff)
                val resultByte = ByteArray(resLength)
                System.arraycopy(byteArray, 4, resultByte, 0, resLength)

                // x
                val x1 = resultByte[1].toInt() and 0xff
                val x2 = (resultByte[2].toInt() and 0xff).shl(8)
                val x=x1+x2

                //y
                val y1 = resultByte[3].toInt() and 0xff
                val y2 = (resultByte[4].toInt() and 0xff).shl(8)
                val y=y1+y2

                //speed
                val speed1 = resultByte[5].toInt() and 0xff
                val speed2 = (resultByte[6].toInt() and 0xff).shl(8)
                val speed=speed1+speed2

                //xThrow
                val xThrow1 = resultByte[7].toInt() and 0xff
                val xThrow2 = (resultByte[8].toInt() and 0xff).shl(8)
                val xThrow=xThrow1+xThrow2

                //yThrow
                val yThrow1 = resultByte[9].toInt() and 0xff
                val yThrow2 = (resultByte[10].toInt() and 0xff).shl(8)
                val yThrow=yThrow1+yThrow2

                //speedThrow
                val speedThrow1 = resultByte[11].toInt() and 0xff
                val speedThrow2 = (resultByte[12].toInt() and 0xff).shl(8)
                val speedThrow=speedThrow1+speedThrow2

                //countThrow
                val countThrow = resultByte[13].toInt() and 0xff

                //test
                val test = resultByte[14].toInt() and 0xff

                //xMoveDistance
                val xMoveDistance1 = resultByte[15].toInt() and 0xff
                val xMoveDistance2 = (resultByte[16].toInt() and 0xff).shl(8)
                val xMoveDistance=xMoveDistance1+xMoveDistance2

                //yMoveDistancee
                val yMoveDistancee1 = resultByte[17].toInt() and 0xff
                val yMoveDistancee2 = (resultByte[18].toInt() and 0xff).shl(8)
                val yMoveDistancee=yMoveDistancee1+yMoveDistancee2

                //xGravity
                val xGravity1 = resultByte[19].toInt() and 0xff
                val xGravity2 = (resultByte[20].toInt() and 0xff).shl(8)
                val xGravity=xGravity1+xGravity2

                //yGravity
                val yGravity1 = resultByte[21].toInt() and 0xff
                val yGravity2 = (resultByte[22].toInt() and 0xff).shl(8)
                val yGravity=yGravity1+yGravity2

                //zGravity
                val zGravity1 = resultByte[23].toInt() and 0xff
                val zGravity2 = (resultByte[24].toInt() and 0xff).shl(8)
                val zGravity=zGravity1+zGravity2

                var  somatosensoryGame=SomatosensoryGame()
                somatosensoryGame.x= x.toShort()
                somatosensoryGame.y= y.toShort()
                somatosensoryGame.Speed= speed.toShort()
                somatosensoryGame.X_Throw= xThrow.toShort()
                somatosensoryGame.Y_Throw= yThrow.toShort()
                somatosensoryGame.Speed_Throw= speedThrow.toShort()
                somatosensoryGame.Count_Throw= countThrow.toShort()
                somatosensoryGame.test= test.toShort()
                somatosensoryGame.X_Move_Distance=xMoveDistance.toShort()
                somatosensoryGame.Y_Move_Distance=yMoveDistancee.toShort()
                somatosensoryGame.X_gravity=xGravity.toShort()
                somatosensoryGame.Y_gravity=yGravity.toShort()
                somatosensoryGame.Z_gravity=zGravity.toShort()

//                LogUtil.e("somatosensoryGame==$somatosensoryGame")
                SomatosensoryGameCallback.onReceiveData(somatosensoryGame)
            }
        }
    }

    fun parserCtrlMusic(byteArray: ByteArray) {
        var type = byteArray[4].toInt()
        when (type) {
            MusicConst.MediaCtrlType.BLE_MEDIA_CTRL_PAUSE -> {
                MusicCallback.onPause()
            }
            MusicConst.MediaCtrlType.BLE_MEDIA_CTRL_RESUME -> {
                MusicCallback.onResume()
            }
            MusicConst.MediaCtrlType.BLE_MEDIA_CTRL_NEXT -> {
                MusicCallback.onNext()
            }
            MusicConst.MediaCtrlType.BLE_MEDIA_CTRL_PREVIOUS -> {
                MusicCallback.onPrevious()
            }
            MusicConst.MediaCtrlType.BLE_MEDIA_CTRL_VOLUME -> {
                var volume = byteArray[5].toInt()
                MusicCallback.onVolume(volume)
            }
        }
    }
    fun parserGPS(byteArray: ByteArray) {
        val byte = byteArray[1].toInt() and (0xff)

        val resLength = byteArray[3].toInt() and (0xff)
        val resultByte = ByteArray(resLength)
        System.arraycopy(byteArray, 4, resultByte, 0, resLength)
        when (resultByte[0].toInt()) {

            //请求gps定位权限
            GPS_CMD_GPS -> {
                GPSCallback.onGPSPermission()
            }
            //请求开始倒计时
            GPS_CMD_COUNTDOWN -> {
                GPSCallback.onGPSCountdown(resultByte[1].toInt() and 0xff)
            }

            //请求gps运动开始
            GPS_CMD_START -> {
                GPSCallback.onGPSStart(resultByte[1].toInt() and 0xff)
            }

            GPS_CMD_SYN -> {
                var sportType = resultByte[2].toInt() and 0xff
                val hr = resultByte[4].toInt() and 0xff

                val time1 = resultByte[9].toInt() and 0xff
                val time2 = (resultByte[10].toInt() and 0xff).shl(8)
                val time3 = (resultByte[11].toInt() and 0xff).shl(16)
                val time4 = (resultByte[12].toInt() and 0xff).shl(24)
                val time = time1 + time2 + time3 + time4

                val steps1 = resultByte[13].toInt() and 0xff
                val steps2 = (resultByte[14].toInt() and 0xff).shl(8)
                val steps3 = (resultByte[15].toInt() and 0xff).shl(16)
                val steps4 = (resultByte[16].toInt() and 0xff).shl(24)
                val steps = steps1 + steps2 + steps3 + steps4

                val kcal1 = resultByte[17].toInt() and 0xff
                val kcal2 = (resultByte[18].toInt() and 0xff).shl(8)
                val kcal3 = (resultByte[19].toInt() and 0xff).shl(16)
                val kcal4 = (resultByte[20].toInt() and 0xff).shl(24)
                val kcal = kcal1 + kcal2 + kcal3 + kcal4

                val distance1 = resultByte[21].toInt() and 0xff
                val distance2 = (resultByte[22].toInt() and 0xff).shl(8)
                val distance3 = (resultByte[23].toInt() and 0xff).shl(16)
                val distance4 = (resultByte[24].toInt() and 0xff).shl(24)
                val distance = distance1 + distance2 + distance3 + distance4

                val speed1 = resultByte[21].toInt() and 0xff
                val speed2 = (resultByte[22].toInt() and 0xff).shl(8)
                val speed3 = (resultByte[23].toInt() and 0xff).shl(16)
                val speed4 = (resultByte[24].toInt() and 0xff).shl(24)
                val speed = speed1 + speed2 + speed3 + speed4

                var gpsSportEntity = NJJGPSSportEntity()
                gpsSportEntity.sportType = sportType
                gpsSportEntity.sportHr = hr
                gpsSportEntity.sportTime = time
                gpsSportEntity.sportSteps = steps
                gpsSportEntity.sportKcal = kcal
                gpsSportEntity.sportDistance = distance
                gpsSportEntity.sportSpeed = speed
                GPSCallback.onGPSSync(gpsSportEntity)
            }

            GPS_CMD_PAUSE -> {
                GPSCallback.onGPSPause(resultByte[1].toInt() and 0xff)
            }

            GPS_CMD_CONTINUE -> {
                GPSCallback.onGPSContinue(resultByte[1].toInt() and 0xff)
            }

            GPS_CMD_END -> {
                GPSCallback.onGPSEnd(resultByte[1].toInt() and 0xff)
            }

        }
    }

    fun parserLocalMusic(byteArray: ByteArray) {
        val resLength = byteArray[3].toInt() and (0xff)
        val resultByte = ByteArray(resLength)

        var curId1 = resultByte[0].toInt() and 0xff
        var curId2 = (resultByte[1].toInt() and 0xff).shl(8)
        var curId = curId1 + curId2

        var totalId1 = resultByte[2].toInt() and 0xff
        var totalId2 = (resultByte[3].toInt() and 0xff).shl(8)
        var totalId = totalId1 + totalId2

        val nameByte = ByteArray(byteArray[3] - 4)
        System.arraycopy(resultByte, 4, nameByte, 0, nameByte.size)

        var name = nameByte.toString(Charsets.UTF_16LE)

        var bleBigFileEntity = BleBigFileEntity()

        bleBigFileEntity.curId = curId.toInt()
        bleBigFileEntity.totalId = totalId.toInt()
        bleBigFileEntity.name = name

        MusicAndBookCallback.onReceivedData(EVT_TYPE_SD_MUSIC_LIST, bleBigFileEntity)
    }

    fun parserEBook(byteArray: ByteArray) {
        val resLength = byteArray[3].toInt() and (0xff)
        val resultByte = ByteArray(resLength)

        var curId1 = resultByte[0].toInt() and 0xff
        var curId2 = (resultByte[1].toInt() and 0xff).shl(8)
        var curId = curId1 + curId2

        var totalId1 = resultByte[2].toInt() and 0xff
        var totalId2 = (resultByte[3].toInt() and 0xff).shl(8)
        var totalId = totalId1 + totalId2

        val nameByte = ByteArray(byteArray[3] - 4)
        System.arraycopy(resultByte, 4, nameByte, 0, nameByte.size)
        var name = nameByte.toString(Charsets.UTF_16LE)

        var bleBigFileEntity = BleBigFileEntity()

        bleBigFileEntity.curId = curId.toInt()
        bleBigFileEntity.totalId = totalId.toInt()
        bleBigFileEntity.name = name

        MusicAndBookCallback.onReceivedData(EVT_TYPE_SD_BOOK_LIST, bleBigFileEntity)
    }

    fun parserSDFreeSpace(byteArray: ByteArray) {
        val resLength = byteArray[3].toInt() and (0xff)
        val resultByte = ByteArray(resLength)
        var size1 = resultByte[0].toInt().and(0xff)
        var size2 = resultByte[1].toInt().and(0xff).shl(8)
        var size3 = resultByte[2].toInt().and(0xff).shl(16)
        var size4 = resultByte[3].toInt().and(0xff).shl(24)
        var size = size1 + size2 + size3 + size4

        SDCardCallback.onReceiveData(size)
    }

    fun parserRecording(byteArray: ByteArray): RecordingDataEntity {
        val resLength = byteArray[3].toInt() and (0xff)
        val resultByte = ByteArray(resLength)
        System.arraycopy(byteArray, 4, resultByte, 0, resLength)

        var recordingDataEntity = RecordingDataEntity()
        recordingDataEntity.type = resultByte[0].toInt()
        when (resultByte[0].toInt()) {
            //TYPE:0:手表获取 APP 是否支持文心一言
            0 -> {
                //0:支持
            }
            //TYPE:5:手表获取 APP 是否支持翻译
            5 -> {

                var lang1 = resultByte[1].toInt()
                var lang2 = resultByte[2].toInt()

                recordingDataEntity.lang1 = lang1
                recordingDataEntity.lang2 = lang2
            }
            //TYPE: 1:手表端语音传输控制“
            //DATA: 0:开始1: 结束
            1 -> {
                recordingDataEntity.status = resultByte[1].toInt()
                recordingDataEntity.session = resultByte[2].toInt()
            }

//                    TYPE: 2:语音传输数据
//                    ID:数据包是否拆分-0:单一数据包·不为 0:拆分的 D号，需要将数据包合并
            2 -> {
                recordingDataEntity.id = resultByte[1].toInt()
                /* if (recordingDataEntity.id!=0){
                     var voiceBytes=ByteArray(resultByte.size-3);
                     System.arraycopy(resultByte,2,voiceBytes,0,resultByte.size-1)
                     recordingDataEntity.voiceData=resultByte;
                 }*/

                var voiceBytes = ByteArray(resultByte.size - 2);
                System.arraycopy(resultByte, 2, voiceBytes, 0, voiceBytes.size)
                recordingDataEntity.voiceData = voiceBytes

            }

        }
        return recordingDataEntity
    }

}