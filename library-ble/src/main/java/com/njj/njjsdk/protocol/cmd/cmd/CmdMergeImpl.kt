package com.njj.njjsdk.protocol.cmd.cmd

import android.text.TextUtils
import com.njj.njjsdk.protocol.cmd.*


import com.njj.njjsdk.protocol.cmd.uitls.AlarmCycleImpl
import com.njj.njjsdk.protocol.entity.*

import com.njj.njjsdk.utils.LogUtil

import java.util.*

/**
 * @ClassName CmdMergeImpl
 * @Description TODO
 * @Date 2022-7-16 12:08:29
 * @Version 1.0
 */
object CmdMergeImpl {


    fun setDisplayTime(cmdType: Int, time: Int): ByteArray {
        val cmdData = createBaseCmdByte(2, cmdType, BLE_CTRL_WRITE)
        cmdData[4] = (time and (0xff)).toByte()
        cmdData[5] = cmdData[4]
        return cmdData
    }

    fun syncSleepData(weekDay: Int): ByteArray {
        val commonControlSync = commonControlRead(EVT_TYPE_SLEEP_DATA)
        commonControlSync[4] = weekDay.toByte()
        commonControlSync[5] = commonControlSync[4]
        return commonControlSync
    }

    fun syncSleepData(): ByteArray {
        val commonControlSync = commonControlRead(EVT_TYPE_SLEEP_DATA)
        commonControlSync[4] = 0x00.toByte()
        commonControlSync[5] = commonControlSync[4]
        return commonControlSync
    }


    //解绑
    fun unbind():ByteArray{
        val unbind = commonControlWrite(EVT_TYPE_UNBIND)
        unbind[4] = 0x01.toByte()
        unbind[5] = unbind[4]
        return unbind
    }

    //测试用自定义表盘的e4007f
    fun getDefaultByte(
        pos: Int,
        color: String,
        needWidth: Int,
        needHeight: Int,
        smallNeedWidth: Int,
        smallNeedHeight: Int
    ): ByteArray {
        LogUtil.e("color=$color")
        val red = Integer.parseInt(color.substring(0, 2), 16)
        val green = Integer.parseInt(color.substring(2, 4), 16)
        val blue = Integer.parseInt(color.substring(4, 6), 16)


        val byte9= (needHeight and (0xff)).toByte()
        val byte10 = ((needHeight.shr(8) and (0xff))).toByte()

        val byte11= (needWidth and (0xff)).toByte()
        val byte12 = ((needWidth.shr(8) and (0xff))).toByte()


        val byte13= (smallNeedHeight and (0xff)).toByte()
        val byte14 = ((smallNeedHeight.shr(8) and (0xff))).toByte()

        val byte15= (smallNeedWidth and (0xff)).toByte()
        val byte16 = ((smallNeedWidth.shr(8) and (0xff))).toByte()

        val byteArrayOf = byteArrayOf(
            0x00,
            0x00,
            0x00,
            red.toByte(),
            green.toByte(),
            blue.toByte(),
            0x00,
            0x00,
            byte9,
            byte10,
            byte11,
            byte12,
            byte13,
            byte14,
            byte15,
            byte16,

            /*0x1b,
            0x01,
            (0xf0).toByte(),
            0x00,
            (0xbd).toByte(),
            0x00,
            (0xa0).toByte(),
            0x00*/
        )
        byteArrayOf[0] = if (pos == 1) 0x0 else 0x01

        return byteArrayOf;
    }


    fun setPhoneType(): ByteArray {
        val cmdData = createBaseCmdByte(2, EVT_TYPE_PHONE_SYSTEM_TYPE, BLE_CTRL_WRITE)
        cmdData[4] = 0x02
        cmdData[5] = cmdData[4]
        return cmdData
    }

    //首页同步数据
    fun syncData(): ByteArray =
        commonControlRead(EVT_TYPE_APP_REQUEST_SYNC)

    fun setSynsHeat(status: Int):ByteArray{
        val cmdData = createBaseCmdByte(3, EVT_TYPE_ALL_DAY_FALG, BLE_CTRL_WRITE)
        cmdData[4] = 0x01
        cmdData[5] = status.toByte()
        cmdData[6]= (cmdData[4]+cmdData[5]).toByte()
        return cmdData
    }


    fun realTimeECG(cmdType: Int, isOpen: Boolean): ByteArray{
        val cmdData = createBaseCmdByte(2, cmdType, BLE_CTRL_WRITE)
        cmdData[4] =  (if (isOpen) 0x01 else 0x00).toByte()
        cmdData[5] =  cmdData[4]
        return cmdData
    }

    fun getPhotoByte(
        pos: Int,
        color: String,
        needWidth: Int,
        needHeight: Int,
        smallNeedWidth: Int,
        smallNeedHeight: Int,
        size: Int
    ): ByteArray {
        LogUtil.e("color=$color")
        val red = Integer.parseInt(color.substring(0, 2), 16)
        val green = Integer.parseInt(color.substring(2, 4), 16)
        val blue = Integer.parseInt(color.substring(4, 6), 16)


        var byte9= (needHeight and (0xff)).toByte()
        var byte10 = ((needHeight.shr(8) and (0xff))).toByte()

        var byte11= (needWidth and (0xff)).toByte()
        var byte12 = ((needWidth.shr(8) and (0xff))).toByte()


        var byte13= (smallNeedHeight and (0xff)).toByte()
        var byte14 = ((smallNeedHeight.shr(8) and (0xff))).toByte()

        var byte15= (smallNeedWidth and (0xff)).toByte()
        var byte16 = ((smallNeedWidth.shr(8) and (0xff))).toByte()

        var byte7=0x10+size
        val byteArrayOf = byteArrayOf(
            0x00,
            0x00,
            0x00,
            red.toByte(),
            green.toByte(),
            blue.toByte(),
            byte7.toByte(),
            0x00,
            byte9,
            byte10,
            byte11,
            byte12,
            byte13,
            byte14,
            byte15,
            byte16,
        )
        byteArrayOf[0] = if (pos == 1) 0x0 else if (pos == 0) 0x02 else 0x01

        return byteArrayOf;
    }

    fun getVideoByte(
        pos: Int,
        color: String,
        needWidth: Int,
        needHeight: Int,
        smallNeedWidth: Int,
        smallNeedHeight: Int,
        size: Int
    ): ByteArray {
        LogUtil.e("color=$color")
        val red = Integer.parseInt(color.substring(0, 2), 16)
        val green = Integer.parseInt(color.substring(2, 4), 16)
        val blue = Integer.parseInt(color.substring(4, 6), 16)


        var byte9= (needHeight and (0xff)).toByte()
        var byte10 = ((needHeight.shr(8) and (0xff))).toByte()

        var byte11= (needWidth and (0xff)).toByte()
        var byte12 = ((needWidth.shr(8) and (0xff))).toByte()


        var byte13= (smallNeedHeight and (0xff)).toByte()
        var byte14 = ((smallNeedHeight.shr(8) and (0xff))).toByte()

        var byte15= (smallNeedWidth and (0xff)).toByte()
        var byte16 = ((smallNeedWidth.shr(8) and (0xff))).toByte()

        var byte7=size
        val byteArrayOf = byteArrayOf(
            0x00,
            0x00,
            0x00,
            red.toByte(),
            green.toByte(),
            blue.toByte(),
            byte7.toByte(),
            0x00,
            byte9,
            byte10,
            byte11,
            byte12,
            byte13,
            byte14,
            byte15,
            byte16,
        )
        byteArrayOf[0] = if (pos == 1) 0x0 else if (pos == 0) 0x02 else 0x01

        return byteArrayOf;
    }



    ///////////////////////////////////////////////表盘相关///////////////////////////////////////////
    // 屏幕尺寸 ： 240*283
    //缩略图尺寸：160*189
    //自定义表盘传输
    var binArray: ByteArray? = null

    @Volatile
    var currentIndex = 0

    fun endSendDialData(type: Int): ByteArray {
        val cmdData = createBaseCmdByte(2, EVT_TYPE_OTA_END, BLE_CTRL_WRITE)
        cmdData[4] = type.toByte()
        cmdData[5] = cmdData[4]
        return cmdData
    }

    fun endSendBigDialData(type: Int): ByteArray {
        val cmdData = createBaseCmdByte(2, EVT_TYPE_OTA_BIG_END, BLE_CTRL_WRITE)
        cmdData[4] = type.toByte()
        cmdData[5] = cmdData[4]
        return cmdData
    }



    /**
     * 女性健康
     * @param mode mode=0 关闭  1：经期 2：备孕 3：孕中
     * @param menstruationAdvance  月经提前
     * @param menstruationCycle     月经周期
     * @param menstruationDuration   经期
     * @param enstruationEndDay     设定月经结束日
     * @param menstruationLatest    最新月经记录
     * @param setPregnancyRemindType   孕母精型
     * @param remindTime
     */
    fun setFemaleHealthCmd(mode: Int,menstruationAdvance:Int,menstruationCycle:Int,menstruationDuration:Int,
                           enstruationEndDay:Long,menstruationLatest:Date,OpenWomanHealth:Int,remindTime:Long): ByteArray {
        val cmdData = createBaseCmdByte(15, EVT_TYPE_WOMEN_HEALTH, BLE_CTRL_WRITE)


        cmdData[4] = mode.toByte()
        cmdData[5] = 0x01

        cmdData[6] = menstruationAdvance.toByte()
        cmdData[7] = OpenWomanHealth.toByte()

        cmdData[8] = menstruationDuration.toByte()
        cmdData[9] = menstruationCycle.toByte()

//        var endDayString = ByteAndStringUtil.long2HexStr(enstruationEndDay)
//        var remindTimeString = ByteAndStringUtil.long2HexStr(remindTime)
//
//        var hexStringToByte = ByteAndStringUtil.hexStringToByte(endDayString)
//        var remindTimeByte = ByteAndStringUtil.hexStringToByte(remindTimeString)


        var tz = TimeZone.getDefault()
        var cal = GregorianCalendar.getInstance(tz)
        var offset = tz.getOffset(cal.timeInMillis).toLong()/1000

        var endDay =enstruationEndDay+offset

        cmdData[10] = (endDay and (0xff)).toByte()
        cmdData[11] = ((endDay.shr(8) and (0xff))).toByte()
        cmdData[12] = ((endDay.shr(16) and (0xff))).toByte()
        cmdData[13] = ((endDay.shr(24) and (0xff))).toByte()

        cmdData[14] = (remindTime and (0xff)).toByte()
        cmdData[15] =(remindTime.shr(8) and (0xff)).toByte()
        cmdData[16] =(remindTime.shr(16) and (0xff)).toByte()
        cmdData[17] =(remindTime.shr(24) and (0xff)).toByte()

        cmdData[18]=(cmdData[4]+cmdData[5]+cmdData[6]+cmdData[7]+cmdData[8]+
                cmdData[9]+cmdData[10]+cmdData[11]+cmdData[12]+cmdData[13]+
                cmdData[14]+cmdData[15]+cmdData[16]+cmdData[17]).toByte()
        return cmdData
    }

    private const val dataLength = 230
    fun sendDialData(isAdd: Int, mPkg: Int): ByteArray? {

        when (isAdd) {
//            0 -> {
//                LogUtil.e("发送数据包$currentIndex")
//            }
            1 -> {
                currentIndex=mPkg+1
                LogUtil.e("发送修复包$currentIndex")
            }
//            else -> {
//                currentIndex=mPkg
//                LogUtil.e("发送本地修复包$currentIndex")
//            }
        }


        //最大的满包数
        val maxData = binArray!!.size / dataLength
        var cmdData: ByteArray? = null
        if (binArray!!.size % dataLength != 0) {
            if (currentIndex - maxData == 1) {
                return null
            }
            if (currentIndex - maxData == 0) {//最后一包，且不满足 dataLength 的长度
                val endLength = binArray!!.size - maxData * dataLength
//                 cmdData = createBaseCmdByte(endLength + 1, EVT_TYPE_OTA_DATA, BLE_CTRL_WRITE)
                cmdData = createSendDialData(endLength + 3, currentIndex)
                System.arraycopy(binArray!!, dataLength * currentIndex, cmdData, 6, endLength)
                var checkData = 0
                for (index in cmdData.indices) {
                    if (index >= 4)
                        checkData += cmdData[index].toInt()
                }
                cmdData[endLength + 6] = checkData.toByte()
                currentIndex++
                return cmdData
            } else {
                //cmdData = createBaseCmdByte(226, EVT_TYPE_OTA_DATA, BLE_CTRL_WRITE)
                cmdData = createSendDialData(dataLength+3, currentIndex)
                System.arraycopy(binArray!!, dataLength * currentIndex, cmdData, 6, dataLength)
                var checkData = 0
                for (index in cmdData.indices) {
                    if (index >= 4)
                        checkData += cmdData[index].toInt()
                }
                cmdData[dataLength+6] = checkData.toByte()
                currentIndex++
                return cmdData
            }
        } else {
            if (currentIndex == maxData) {
                LogUtil.d("------fa送完毕")
                return null
            }
            cmdData = createSendDialData(dataLength+3, currentIndex)
            //cmdData = createBaseCmdByte(226, EVT_TYPE_OTA_DATA, BLE_CTRL_WRITE)
            System.arraycopy(binArray!!, dataLength * currentIndex, cmdData, 6, dataLength)
            var checkData = 0
            for (index in cmdData.indices) {
                if (index >= 4)
                    checkData += cmdData[index].toInt()
            }
            cmdData[dataLength+6] = checkData.toByte()
            currentIndex++
            return cmdData
        }
    }

    fun sendDialBigData(isAdd: Int, mPkg: Int): ByteArray? {

        when (isAdd) {
//            0 -> {
//                LogUtil.e("发送数据包$currentIndex")
//            }
            1 -> {
                currentIndex=mPkg+1
                LogUtil.e("发送修复包$currentIndex")
            }
//            else -> {
//                currentIndex=mPkg
//                LogUtil.e("发送本地修复包$currentIndex")
//            }
        }


        //最大的满包数
        val maxData = binArray!!.size / dataLength
        var cmdData: ByteArray? = null
        if (binArray!!.size % dataLength != 0) {
            if (currentIndex - maxData == 1) {
                return null
            }
            if (currentIndex - maxData == 0) {//最后一包，且不满足 dataLength 的长度
                val endLength = binArray!!.size - maxData * dataLength
//                cmdData = createSendDialData(endLength + 3, currentIndex)
                cmdData = createSendDialBigData(endLength + 5, currentIndex)
                System.arraycopy(binArray!!, dataLength * currentIndex, cmdData, 8, endLength)
                var checkData = 0
                for (index in cmdData.indices) {
                    if (index >= 4)
                        checkData += cmdData[index].toInt()
                }
                cmdData[endLength + 8] = checkData.toByte()
                currentIndex++
                return cmdData
            } else {
                //cmdData = createBaseCmdByte(226, EVT_TYPE_OTA_DATA, BLE_CTRL_WRITE)
                cmdData = createSendDialBigData(dataLength + 5, currentIndex)

                System.arraycopy(binArray!!, dataLength * currentIndex, cmdData, 8, dataLength)
                var checkData = 0
                for (index in cmdData.indices) {
                    if (index >= 4)
                        checkData += cmdData[index].toInt()
                }
                cmdData[dataLength+8] = checkData.toByte()
                currentIndex++
                return cmdData
            }
        } else {
            if (currentIndex == maxData) {
                LogUtil.d("------fa送完毕")
                return null
            }
            cmdData = createSendDialBigData(dataLength + 5, currentIndex)
            //cmdData = createBaseCmdByte(226, EVT_TYPE_OTA_DATA, BLE_CTRL_WRITE)
            System.arraycopy(binArray!!, dataLength * currentIndex, cmdData, 8, dataLength)
            var checkData = 0
            for (index in cmdData.indices) {
                if (index >= 4)
                    checkData += cmdData[index].toInt()
            }
            cmdData[dataLength+8] = checkData.toByte()
            currentIndex++
            return cmdData
        }
    }

   /* fun sendContactDialData(): ByteArray? {
        //最大的满包数
        val maxData = binArray!!.size / dataLength
        var cmdData: ByteArray? = null
        if (binArray!!.size % dataLength != 0) {
            if (currentIndex - maxData == 1) {

                return null
            }
            if (currentIndex - maxData == 0) {//最后一包，且不满足 dataLength 的长度
                val endLength = binArray!!.size - maxData * dataLength
//                 cmdData = createBaseCmdByte(endLength + 1, EVT_TYPE_OTA_DATA, BLE_CTRL_WRITE)
                cmdData = createSendDialData(endLength +3, currentIndex)
                System.arraycopy(binArray!!, dataLength * currentIndex, cmdData, 4, endLength)
                var checkData = 0
                for (index in cmdData.indices) {
                    if (index >= 4)
                        checkData += cmdData[index].toInt()
                }
                cmdData[endLength + 4] = checkData.toByte()
                currentIndex++
                return cmdData
            } else {
//                cmdData = createBaseCmdByte(224, EVT_TYPE_OTA_DATA, BLE_CTRL_WRITE)
                cmdData = createSendDialData(223, currentIndex)
                System.arraycopy(binArray!!, dataLength * currentIndex, cmdData, 4, 220)
                var checkData = 0
                for (index in cmdData.indices) {
                    if (index >= 4)
                        checkData += cmdData[index].toInt()
                }
                cmdData[224] = checkData.toByte()
                currentIndex++
                return cmdData
            }
        } else {
            if (currentIndex == maxData) {
                LogUtil.d("------fa送完毕")
                return null
            }
//            cmdData = createSendDialData(223, currentIndex)
            cmdData = createBaseCmdByte(224, EVT_TYPE_OTA_DATA, BLE_CTRL_WRITE)
            System.arraycopy(binArray!!, dataLength * currentIndex, cmdData, 6, 220)
            var checkData = 0
            for (index in cmdData.indices) {
                if (index >= 4)
                    checkData += cmdData[index].toInt()
            }
            cmdData[224] = checkData.toByte()
            currentIndex++
            return cmdData
        }
    }*/

    //拼接发送表盘数据的字节数组
    private fun createSendDialData(cmdDataLength: Int, id: Int): ByteArray {
        val createBaseCmdByte = createBaseCmdByte(cmdDataLength, EVT_TYPE_OTA_DATA, BLE_CTRL_WRITE)
        createBaseCmdByte[4] = ((id and (0xff))).toByte()
        createBaseCmdByte[5] = ((id.shr(8) and (0xff))).toByte()

        return createBaseCmdByte
    }

    //拼接发送表盘数据的字节数组
    private fun createSendDialBigData(cmdDataLength: Int, id: Int): ByteArray {
        val createBaseCmdByte = createBaseCmdByte(cmdDataLength, EVT_TYPE_OTA_BIG_DATA, BLE_CTRL_WRITE)
        createBaseCmdByte[4] = ((id and (0xff))).toByte()
        createBaseCmdByte[5] = ((id.shr(8) and (0xff))).toByte()
        createBaseCmdByte[6] = ((id.shr(16) and (0xff))).toByte()
        createBaseCmdByte[7] = ((id.shr(24) and (0xff))).toByte()
        return createBaseCmdByte
    }


    //开始发送表盘的指令
    fun startSendDial(binArray: ByteArray): ByteArray {
        return startSendDial(binArray, 0x01)
    }

    //开始发送联系人的指令
    fun startSendContactDial(binArray: ByteArray): ByteArray {
        return startSendDial(binArray,0x02)
    }

    fun startSendDial(binArray: ByteArray, type: Int): ByteArray {
        CmdMergeImpl.binArray = binArray
        currentIndex = 0
        val cmdData = createBaseCmdByte(8, EVT_TYPE_OTA_START, BLE_CTRL_WRITE)
        cmdData[4] = type.toByte()
        val totalSize = binArray.size
        cmdData[5] = ((totalSize and (0xff))).toByte()
        cmdData[6] = ((totalSize.shr(8) and (0xff))).toByte()
        cmdData[7] = ((totalSize.shr(16) and (0xff))).toByte()
        cmdData[8] = ((totalSize.shr(24) and (0xff))).toByte()
        var packageCount = totalSize / dataLength
        if (totalSize % dataLength != 0)
            packageCount += 1

        cmdData[9] = ((packageCount and (0xff))).toByte()
        cmdData[10] = ((packageCount.shr(8) and (0xff))).toByte()
        cmdData[11] =
            (cmdData[4] + cmdData[5] + cmdData[6] + cmdData[7] + cmdData[8]
                    + cmdData[9] + cmdData[10]).toByte()
        return cmdData
    }

    fun startSendBigDial(binArray: ByteArray, type: Int): ByteArray {
        CmdMergeImpl.binArray = binArray
        currentIndex = 0
        val cmdData = createBaseCmdByte(10, EVT_TYPE_OTA_BIG_START, BLE_CTRL_WRITE)
        cmdData[4] = type.toByte()
        val totalSize = binArray.size
        cmdData[5] = ((totalSize and (0xff))).toByte()
        cmdData[6] = ((totalSize.shr(8) and (0xff))).toByte()
        cmdData[7] = ((totalSize.shr(16) and (0xff))).toByte()
        cmdData[8] = ((totalSize.shr(24) and (0xff))).toByte()
        var packageCount = totalSize / dataLength
        if (totalSize % dataLength != 0)
            packageCount += 1

        cmdData[9] = ((packageCount and (0xff))).toByte()
        cmdData[10] = ((packageCount.shr(8) and (0xff))).toByte()
        cmdData[11] = ((packageCount.shr(16) and (0xff))).toByte()
        cmdData[12] = ((packageCount.shr(24) and (0xff))).toByte()
        cmdData[13] =
            (cmdData[4] + cmdData[5] + cmdData[6] + cmdData[7] + cmdData[8]
                    + cmdData[9] + cmdData[10]+ cmdData[11]+ cmdData[12]).toByte()
        return cmdData
    }
    ///////////////////////////////////////////////表盘相关///////////////////////////////////////////


/*    fun endSendContacts(): ByteArray {
        return endSendContacts(EVT_TYPE_OTA_END)
    }*/

    private fun endSendContacts(cmdType: Int): ByteArray {
        val cmdData = createBaseCmdByte(2, cmdType, BLE_CTRL_WRITE)
        cmdData[4] = 0x01
        cmdData[5] = cmdData[4]
        return cmdData
    }
/*
    fun sendContacts(): ByteArray {
        return sendContacts(EVT_TYPE_OTA_DATA)
    }*/

    private fun sendContacts(cmdType: Int): ByteArray {
        val cmdData = createBaseCmdByte(41, cmdType, BLE_CTRL_WRITE)
        val toByteArray = ("今晚打老虎").toByteArray()
        val toByteArray1 = ("12345678901").toByteArray()
        val nameArray = ByteArray(20)
        nameArray[0] = toByteArray.size.toByte()
        System.arraycopy(toByteArray, 0, nameArray, 1, toByteArray.size)
        System.arraycopy(nameArray, 0, cmdData, 4, nameArray.size)
        val phoneArray = ByteArray(20)
        phoneArray[0] = toByteArray1.size.toByte()
        System.arraycopy(toByteArray1, 0, phoneArray, 1, toByteArray1.size)
        System.arraycopy(phoneArray, 0, cmdData, 24, phoneArray.size)
        var checkData = 0
        for (element in nameArray) {
            checkData += element.toInt()
        }
        for (index in phoneArray) {
            checkData += index.toInt()
        }
        cmdData[44] = checkData.toByte()
        return cmdData
    }

    /*fun startSendContacts(): ByteArray {
        val cmdData = createBaseCmdByte(8, EVT_TYPE_OTA_START, BLE_CTRL_WRITE)
        cmdData[4] = 0x02
//        总长度:4个byte  total size
//        val toByteArray = ("今晚打老虎").toByteArray()
//        val toByteArray1 = ("12345678901").toByteArray()
        val totalSize = 40
        cmdData[5] = ((totalSize and (0xff))).toByte()
        cmdData[6] = ((totalSize.shr(8) and (0xff))).toByte()
        cmdData[7] = ((totalSize.shr(16) and (0xff))).toByte()
        cmdData[8] = ((totalSize.shr(24) and (0xff))).toByte()
        cmdData[9] = (cmdData[4] + cmdData[5] + cmdData[6] + cmdData[7] + cmdData[8]).toByte()
        return cmdData
    }
*/
    //通知消息
    //BC0B02060002013A20207D
    fun sendMessage(msgType: Int, title: String, content: String): ByteArray? {
        val tTitleArray: ByteArray?
        val mTitleArray: ByteArray?

        val toLongArray:ByteArray?
        val toLongArray1: ByteArray?
        LogUtil.d("-----------------content= $content")
        if (TextUtils.isEmpty((content.trim().replace(" ", "")))) {
            tTitleArray = (": ").toByteArray()
            toLongArray = (" ").toByteArray()
            return null
        } else {
            tTitleArray = if (TextUtils.isEmpty(title)) (": ").toByteArray()
            else ("$title: ").toByteArray()

            toLongArray = if (TextUtils.isEmpty(content)) (" ").toByteArray()
            else (content).toByteArray()
        }

        //消息推送最长长度120个字节
        if(toLongArray.size > 120){
            toLongArray1= ByteArray(120)
            System.arraycopy(toLongArray,0,toLongArray1,0,toLongArray1.size)
        }else{
            toLongArray1 = toLongArray
        }

        if (tTitleArray.size>120){
            mTitleArray= ByteArray(120)
            System.arraycopy(toLongArray,0,mTitleArray,0,mTitleArray.size)
        }else{
            mTitleArray = tTitleArray
        }

        val length = mTitleArray.size + toLongArray1.size + 4
        val cmdData = createBaseCmdByte(length, EVT_TYPE_ALERT_MSG, BLE_CTRL_WRITE)
        cmdData[4] = msgType.toByte()
        cmdData[5] = (mTitleArray.size-2).toByte()
        cmdData[6] = (toLongArray1.size).toByte()
        var checkData: Int = cmdData[4] + cmdData[5] + cmdData[6]
        for (element in mTitleArray) {
            checkData += element
        }
        for (index in toLongArray1) {
            checkData += index
        }

        System.arraycopy(tTitleArray, 0, cmdData, 7, mTitleArray.size)
        System.arraycopy(toLongArray1, 0, cmdData, (7 + mTitleArray.size), toLongArray1.size)
        cmdData[length + 3] = checkData.toByte()
        LogUtil.d("消息标题: ${mTitleArray.size} 消息内容：${toLongArray1.size}")
        return cmdData
    }

    fun setTakePhoto(cmdType: Int, isOpen: Boolean): ByteArray {
        val cmdData = createBaseCmdByte(2, cmdType, BLE_CTRL_WRITE)
        cmdData[4] = if (isOpen) 2 else 0
        cmdData[5] = cmdData[4]
        return cmdData
    }

    fun setSingleData(cmdType: Int): ByteArray {
        val cmdData = createBaseCmdByte(5, cmdType, BLE_CTRL_WRITE)
        cmdData[4] = 0x4b
        cmdData[5] = 0x62
        cmdData[6] = 0x6e
        cmdData[7] = 0x46
        cmdData[8] =
            (cmdData[4] + cmdData[5] + cmdData[6] + cmdData[7]).toByte()

        return cmdData
    }

    fun setAllDayTest(cmdType: Int, control: Int, isOpen: Boolean): ByteArray {
        val cmdData = createBaseCmdByte(3, cmdType, BLE_CTRL_WRITE)
        cmdData[4] = control.toByte()
        cmdData[5] = if (isOpen) 1 else 0
        cmdData[6] = (cmdData[4] + cmdData[5]).toByte()
        return cmdData
    }

    //获取闹钟
    fun getAlarmClock(): ByteArray {
        val commonControlRead = commonControlRead(EVT_TYPE_ALARM)
        commonControlRead[4] = (0xff).toByte()
        commonControlRead[5] = (0xff).toByte()
        return commonControlRead
    }


  /*  //设置闹钟
    //type：闹钟类型 0：没有 1: 单次，2：每天，3自定义
    fun setAlarmClock(infoNjj: NjjAlarmClockInfo, index: Int): ByteArray {
        val cmdData = createBaseCmdByte(7, EVT_TYPE_ALARM, BLE_CTRL_WRITE)
        cmdData[4] = index.toByte()
        cmdData[5] = if (infoNjj.isDeleteFlag) 0 else {
            (if (infoNjj.isAlarmState) 1 else 0).toByte()
        }
        cmdData[6] = if (infoNjj.isDeleteFlag) 0 else {
            (if (infoNjj.alarmCycle == 0) 1 else 3).toByte()
        }
        cmdData[7] = AlarmCycleImpl.getRYAlarmCycle(infoNjj.alarmCycle).toByte()
        cmdData[8] = (infoNjj.alarmTime / (60 * 60)).toByte()
        cmdData[9] = (infoNjj.alarmTime / 60 % 60).toByte()
        cmdData[10] =
            (cmdData[4] + cmdData[5] + cmdData[6] + cmdData[7] + cmdData[8] + cmdData[9]).toByte()
        return cmdData
    }*/

    fun setAlarmClock(infoNjj:List<NjjAlarmClockInfo>):ByteArray{
        val cmdData = createBaseCmdByte(20+2, EVT_TYPE_ALARM, BLE_CTRL_WRITE)
        cmdData[4] = 0xFF.toByte()
        var check=0xFF
        for(index in 0..3){
            val i = (index+1)*5
            if(index < infoNjj.size) {
                cmdData[i] = if (infoNjj[index].isDeleteFlag) 0 else {
                    (if (infoNjj[index].isAlarmState) 1 else 0).toByte()
                }
                cmdData[i+1] = if (infoNjj[index].isDeleteFlag) 0 else {
                    (if (infoNjj[index].alarmCycle == 0) 1 else 3).toByte()
                }
                cmdData[i+2] = AlarmCycleImpl.getRYAlarmCycle(infoNjj[index].alarmCycle).toByte()
                cmdData[i+3] = (infoNjj[index].alarmTime / (60 * 60)).toByte()
                cmdData[i+4] = (infoNjj[index].alarmTime / 60 % 60).toByte()
                check += cmdData[i]+cmdData[i+1]+cmdData[i+2]+cmdData[i+3]+cmdData[i+4]
            }else{
                cmdData[i] = 0x00.toByte()
                cmdData[i+1] = 0x00.toByte()
                cmdData[i+2] = 0x00.toByte()
                cmdData[i+3] = 0x00.toByte()
                cmdData[i+4] = 0x00.toByte()
            }
        }
        cmdData[cmdData.size-1] = check.toByte()
        return cmdData
    }


    //久坐
    fun setLongSit(cmdType: Int, en: NjjLongSitEntity): ByteArray {
        val cmdData = createBaseCmdByte(7, cmdType, BLE_CTRL_WRITE)
        cmdData[4] = (en.longSitStatus).toByte()
        cmdData[5] = (en.longSitInterval).toByte()
        cmdData[6] = (en.longSitBeginTime / 60).toByte()
        cmdData[7] = (en.longSitBeginTime % 60).toByte()
        cmdData[8] = (en.longSitEndTime / 60).toByte()
        cmdData[9] = (en.longSitEndTime % 60).toByte()
        cmdData[10] =
            (cmdData[4] + cmdData[5] + cmdData[6] + cmdData[7] + cmdData[8] + cmdData[9]).toByte()
        return cmdData
    }

    //喝水
    fun setDrinkWater(cmdType: Int, en: NjjDrinkWaterEntity): ByteArray {
        val cmdData = createBaseCmdByte(7, cmdType, BLE_CTRL_WRITE)
        cmdData[4] = (en.drinkWaterStatus).toByte()
        cmdData[5] = (en.drinkWaterInterval).toByte()
        cmdData[6] = (en.drinkWaterBeginTime / 60).toByte()
        cmdData[7] = (en.drinkWaterBeginTime % 60).toByte()
        cmdData[8] = (en.drinkWaterEndTime / 60).toByte()
        cmdData[9] = (en.drinkWaterEndTime % 60).toByte()
        cmdData[10] =
            (cmdData[4] + cmdData[5] + cmdData[6] + cmdData[7] + cmdData[8] + cmdData[9]).toByte()
        return cmdData
    }

    //洗手
    fun setWashHand(cmdType: Int, en: NjjWashHandEntity): ByteArray {
        val cmdData = createBaseCmdByte(7, cmdType, BLE_CTRL_WRITE)
        cmdData[4] = (en.washHandStatus).toByte()
        cmdData[5] = (en.washHandInterval).toByte()
        cmdData[6] = (en.washHandBeginTime / 60).toByte()
        cmdData[7] = (en.washHandBeginTime % 60).toByte()
        cmdData[8] = (en.washHandEndTime / 60).toByte()
        cmdData[9] = (en.washHandEndTime % 60).toByte()
        cmdData[10] =
            (cmdData[4] + cmdData[5] + cmdData[6] + cmdData[7] + cmdData[8] + cmdData[9]).toByte()
        return cmdData
    }

    fun noDisturb(cmdType: Int, en: NjjDisturbEntity): ByteArray {
        val cmdData = createBaseCmdByte(6, cmdType, BLE_CTRL_WRITE)
        cmdData[4] = en.disturbStatus.toByte()
        cmdData[5] = (en.disturbBeginTime / 60).toByte()
        cmdData[6] = (en.disturbBeginTime % 60).toByte()
        cmdData[7] =  (en.disturbEndTime / 60).toByte()
        cmdData[8] =  (en.disturbEndTime % 60).toByte()
        cmdData[9] = (cmdData[4] + cmdData[5] + cmdData[6] + cmdData[7] + cmdData[8]).toByte()
        return cmdData
    }


    fun handScreenOn(cmdType: Int, en: NjjWristScreenEntity): ByteArray {
        val cmdData = createBaseCmdByte(6, cmdType, BLE_CTRL_WRITE)
        cmdData[4] = en.wristScreenStatus.toByte()
        cmdData[5] = (en.wristScreenBeginTime / 60).toByte()
        cmdData[6] = (en.wristScreenBeginTime % 60).toByte()
        cmdData[7] =  (en.wristScreenEndTime / 60).toByte()
        cmdData[8] =  (en.wristScreenEndTime % 60).toByte()
        cmdData[9] = (cmdData[4] + cmdData[5] + cmdData[6] + cmdData[7] + cmdData[8]).toByte()
        return cmdData
    }

    fun setRealWeather(cmdType: Int, weatherNjj: NjjSyncWeatherData): ByteArray {
        val cmdData = createBaseCmdByte(3, cmdType, BLE_CTRL_WRITE)
        cmdData[4] = weatherNjj.weatherType.toByte()
        cmdData[5] = weatherNjj.tempData.toByte()
        cmdData[6] = (cmdData[4] + cmdData[5]).toByte()
        return cmdData
    }

    fun setWeatherForecast(cmdType: Int): ByteArray {
        val cmdData = createBaseCmdByte(29, cmdType, BLE_CTRL_WRITE)
        var endByte: Byte = 0
        for (i in 1..7) {
            cmdData[i + (i * 3)] = i.toByte()
            cmdData[i + (i * 3) + 1] = 0x04
            cmdData[i + (i * 3) + 2] = 0x28
            cmdData[i + (i * 3) + 3] = 0x05

            endByte =
                (endByte + (cmdData[i + (i * 3)] + cmdData[i + (i * 3) + 1] + cmdData[i + (i * 3) + 2] + cmdData[i + (i * 3) + 3]).toByte()).toByte()
        }
        cmdData[32] = endByte
        return cmdData
    }

    fun setSportData(cmdType: Int): ByteArray {
        val cmdData = createBaseCmdByte(17, cmdType, BLE_CTRL_WRITE)
        val steps = 20000
        cmdData[4] = (steps and (0xff)).toByte()
        cmdData[5] = ((steps.shr(8) and (0xff))).toByte()
        cmdData[6] = ((steps.shr(16) and (0xff))).toByte()
        cmdData[7] = ((steps.shr(24) and (0xff))).toByte()
        val distance = 15000
        cmdData[8] = (distance and (0xff)).toByte()
        cmdData[9] = ((distance.shr(8) and (0xff))).toByte()
        cmdData[10] = ((distance.shr(16) and (0xff))).toByte()
        cmdData[11] = ((distance.shr(24) and (0xff))).toByte()
        val cals = 50000
        cmdData[12] = (cals and (0xff)).toByte()
        cmdData[13] = ((cals.shr(8) and (0xff))).toByte()
        cmdData[14] = ((cals.shr(16) and (0xff))).toByte()
        cmdData[15] = ((cals.shr(24) and (0xff))).toByte()
        val time = 1646373651 + 8 * 60 * 60  //2022-03-04 14:00:51
        cmdData[16] = (time and (0xff)).toByte()
        cmdData[17] = ((time.shr(8) and (0xff))).toByte()
        cmdData[18] = ((time.shr(16) and (0xff))).toByte()
        cmdData[19] = ((time.shr(24) and (0xff))).toByte()

        cmdData[20] = (cmdData[4] + cmdData[5] + cmdData[6] + cmdData[7] + cmdData[8] +
                cmdData[9] + cmdData[10] + cmdData[11] + cmdData[12] + cmdData[13] + cmdData[14] +
                cmdData[15] + cmdData[16] + cmdData[17] + cmdData[18] + cmdData[19]).toByte()
        return cmdData
    }


    fun setTargetSteps(cmdType: Int, steps: Int): ByteArray {
        val cmdData = createBaseCmdByte(5, cmdType, BLE_CTRL_WRITE)
        cmdData[4] = (steps and (0xff)).toByte()
        cmdData[5] = ((steps.shr(8) and (0xff))).toByte()
        cmdData[6] = ((steps.shr(16) and (0xff))).toByte()
        cmdData[7] = ((steps.shr(24) and (0xff))).toByte()
        cmdData[8] = (cmdData[4] + cmdData[5] + cmdData[6] + cmdData[7]).toByte()
        return cmdData
    }


    //设置语言/设置表盘id/亮屏时长
    fun setLanguage(cmdType: Int, pos: Int): ByteArray {
        val cmdData = createBaseCmdByte(2, cmdType, BLE_CTRL_WRITE)
        cmdData[4] = pos.toByte()
        cmdData[5] = cmdData[4]
        return cmdData
    }

    fun setTime(cmdType: Int): ByteArray {
        val cmdData = createBaseCmdByte(5, cmdType, BLE_CTRL_WRITE)
//        val calendar = Calendar.getInstance()
//        val zoneOffset: Int = calendar.get(Calendar.ZONE_OFFSET)

        val tz = TimeZone.getDefault()
        val cal = GregorianCalendar.getInstance(tz)
        val offsetInMillis = tz.getOffset(cal.timeInMillis)
        val time = (System.currentTimeMillis() + offsetInMillis) / 1000

        var hour=offsetInMillis/1000/60/60
        LogUtil.d("时间戳：$time  offsetInMillis=$offsetInMillis  hour=$hour")
        cmdData[4] = (time and (0xff)).toByte()
        cmdData[5] = ((time.shr(8) and (0xff))).toByte()
        cmdData[6] = ((time.shr(16) and (0xff))).toByte()
        cmdData[7] = ((time.shr(24) and (0xff))).toByte()
        cmdData[8] = (cmdData[4] + cmdData[5] + cmdData[6] + cmdData[7]).toByte()
        return cmdData
    }

    //时间和温度单位的
    fun setHourFormat(cmdType: Int, is24: Boolean): ByteArray {
        val cmdData = createBaseCmdByte(2, cmdType, BLE_CTRL_WRITE)
        cmdData[4] = if (is24) 0 else 1
        cmdData[5] = cmdData[4]
        return cmdData
    }


    fun setUnitFormat(cmdType: Int, isMetric: Boolean): ByteArray {
        val cmdData = createBaseCmdByte(2, cmdType, BLE_CTRL_WRITE)
        cmdData[4] = if (isMetric) 0 else 1
        cmdData[5] = cmdData[4]
        return cmdData
    }


    fun setUserInfo(cmdType: Int): ByteArray {
        val cmdData = createBaseCmdByte(9, cmdType, BLE_CTRL_WRITE)
        cmdData[4] = 0
        cmdData[5] = 0xAA.toByte()
        cmdData[6] = 0x3c.toByte()
        cmdData[7] = 0x14
        cmdData[8] = 0x03
        cmdData[9] = 0x03
        cmdData[10] = 0x7e
        cmdData[11] = 0x06
        cmdData[12] =
            (cmdData[5] + cmdData[6] + cmdData[7] + cmdData[8] + cmdData[9] + cmdData[10] + cmdData[11]).toByte()
        return cmdData
    }

    fun commonControlSync(cmdType: Int): ByteArray {
        val cmdData = createBaseCmdByte(2, cmdType, BLE_CTRL_SYNC)
        cmdData[4] = 0
        cmdData[5] = 0
        return cmdData
    }

    fun commonControlRead(cmdType: Int): ByteArray {
        val cmdData = createBaseCmdByte(2, cmdType, BLE_CTRL_READ)
        cmdData[4] = 0
        cmdData[5] = 0
        return cmdData
    }

    fun commonControlWrite(cmdType: Int): ByteArray {
        val cmdData = createBaseCmdByte(2, cmdType, BLE_CTRL_WRITE)
        cmdData[4] = 0
        cmdData[5] = 0
        return cmdData
    }

    fun modifyBtName(cmdType: Int, btName: String): ByteArray {
        val data = btName.toByteArray()
        val cmdData = createBaseCmdByte(data.size+1, cmdType, BLE_CTRL_WRITE)
        System.arraycopy(data, 0, cmdData, 4, data.size)
        var checkData:Int=0
        for (value in data) {
            checkData += value
        }
        cmdData[cmdData.size-1]= checkData.toByte()
        return cmdData
    }

    fun pushQRCode(codeType:Int,content:String): ByteArray? {

        var code = codeType

        val data = content.toByteArray()
        LogUtil.i("data size ==  ${data.size}")
        LogUtil.i("content ==  $content")
        LogUtil.i("codeType ==  $codeType")
        if(data.size > 230){
            return null
        }
        val qrData: ByteArray = if (code < 10) {
            //名片type从0~9
            createBaseCmdByte(data.size+2, EVT_TYPE_ADD_FRIEND, BLE_CTRL_WRITE)
        }else{
            code-=10 //支付type从0~4
            createBaseCmdByte(data.size+2, EVT_TYPE_RECEIPT_CODE, BLE_CTRL_WRITE)
        }
        qrData[4]=code.toByte()
        var checkData:Int=code
        for (value in data) {
            checkData += value
        }
        System.arraycopy(data, 0, qrData, 5, data.size)
        qrData[qrData.size-1] = checkData.toByte()
        return qrData;
    }


    /***
     * APP->Device 挂断或接听电话
     * state：挂断 0；接听 1
     */
    fun handUpPhone(state:Int):ByteArray{
        val handup = createBaseCmdByte(2, EVT_TYPE_ANDROID_PHONE_CTRL, BLE_CTRL_WRITE)
        handup[4] = state.toByte()
        handup[5] = handup[4]
        return handup;
    }

    fun receiveSportRecord():ByteArray{
        val sportRecord = createBaseCmdByte(2, EVT_TYPE_SPORT_RECORD, BLE_CTRL_READ);
        sportRecord[4] = 0x00.toByte()
        sportRecord[5] = 0x00.toByte()
        return sportRecord
    }

    fun createWeekDaySports():ByteArray{
        val byteArray= ByteArray(5)
        byteArray[0] = 0xbc.toByte()
        byteArray[1]= EVT_TYPE_HISTORY_SPORT_DATA.toByte()
        byteArray[2]= BLE_CTRL_READ.toByte()
        byteArray[3]=0x01
        byteArray[4]=0x00
        return byteArray
    }

    /**
     * 创建基础指令类型
     *@date  2022-3-2 15:31:21
     *@param cmdLength 指令的长度，不包含命令头,包含包尾校验位
     *@param controlType 指令的控制类型
     *@param cmdType 指令类型
     *@return ByteArray
     */
    private fun createBaseCmdByte(cmdLength: Int, cmdType: Int, controlType: Int): ByteArray {
        val data = ByteArray(cmdLength + 4)
        data[0] = 0xbc.toByte()
        data[1] = cmdType.toByte()
        data[2] = controlType.toByte()
        data[3] = (cmdLength - 1).toByte()
        return data
    }

    fun createMotionGame(type: Int): ByteArray {
        val byteArray = ByteArray(6)
        byteArray[0] = 0xbc.toByte()
        byteArray[1] = EVT_TYPE_MOTION_GAME.toByte()
        byteArray[2] = 0x02
        byteArray[3] = BLE_CTRL_READ.toByte()
        byteArray[4] = type.toByte()
        byteArray[5] = type.toByte()
        return byteArray
    }




}

