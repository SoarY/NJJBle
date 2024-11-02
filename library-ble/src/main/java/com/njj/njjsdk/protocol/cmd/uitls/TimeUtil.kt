package com.njj.njjsdk.protocol.cmd.uitls


import com.njj.njjsdk.utils.TimeFormatUtil
import java.util.*

/**
 * @ClassName TimeUtil
 * @Description TODO
 * @Author  Darcy
 * @Date 2022/3/4 15:21
 * @Version 1.0
 */
object TimeUtil {

    fun getCurrentHour(type: Int): Int {
        return if (type == 0) Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        else (Calendar.getInstance().get(Calendar.MINUTE) + 2)
    }


    /**
     * 获取当天零点开始的时间戳
     *@date  2022-3-31 13:43:14
     *@return String
     */
    fun getCurrentDate(): Int {
        val year = Calendar.getInstance().get(Calendar.YEAR)
        val month = Calendar.getInstance().get(Calendar.MONTH) + 1
        val day = Calendar.getInstance().get(Calendar.DATE)
        val time = "$year-$month-$day 00:00:00"
        return TimeFormatUtil.getTimeMill(time)
    }
}