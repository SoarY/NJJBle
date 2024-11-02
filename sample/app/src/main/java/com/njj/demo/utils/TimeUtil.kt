package com.darcy.liang.kotlindemo.utils

import java.util.*

/**
 * @ClassName TimeUtil
 * @Description TODO
 * @Author  Darcy
 * @Date 2022/3/4 15:21
 * @Version 1.0
 */
object TimeUtil {

    fun getCurrentHour(type:Int):Int{
       return if (type == 0) Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        else  (Calendar.getInstance().get(Calendar.MINUTE)+2)
    }

}