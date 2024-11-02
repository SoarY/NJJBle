package com.njj.njjsdk.protocol.cmd.uitls


import com.njj.njjsdk.utils.NjjUtils
import com.njj.njjsdk.utils.NjjUtils.getCycleChar
import com.njj.njjsdk.utils.LogUtil
import java.util.*

/**
 * 闹钟循环处理类
 * @ClassName AlarmCycleImpl
 * @Description TODO
 * @Author  Darcy
 * @Date 2022/4/21 15:33
 * @Version 1.0
 */
object AlarmCycleImpl {

    /**
     *@date  2022-4-21 15:34:20
     * 1234567
     * 6543217
     *
     *@param cycle 用户选择的一般性循环 int
     *@return int 瑞昱需要的cycle
     */
    fun getRYAlarmCycle(alarmCycle: Int): Int {
        val cycleChar: CharArray =
                getCycleChar(Integer.toBinaryString(alarmCycle))
        LogUtil.e("-------${cycleChar.contentToString()}")
        val cycle = CharArray(8)
        cycle[0] = '0'
        for (index in cycleChar.indices) {
            if (index == 6) {
                cycle[7] = cycleChar[index]
            } else {
                cycle[6 - index] = cycleChar[index]
            }
        }
        LogUtil.e("------$alarmCycle--------------${NjjUtils.getCycleInt(String(cycle))}-----------${cycle.contentToString()}")
        return NjjUtils.getCycleInt(String(cycle))
    }


    fun setRYAlarmCycleToDisplayCycle(alarmCycle: Int): CharArray {
//        val cycleChar: CharArray = getCycleChar(Integer.toBinaryString(alarmCycle))
        val charr:CharArray= Integer.toBinaryString(alarmCycle).toCharArray()

        val cycle:CharArray = CharArray(7)
        for(index in cycle.indices){
            cycle[index]='0'
        }
        for (index in charr.indices){
            cycle[index] = charr[index]
        }

        val cy = CharArray(8)
        cy[0]='0'
        for (index in cycle.indices) {
            if (index == 6) {
                cy[7] = cycle[index]
            } else {
                cy[6 - index] = cycle[index]
            }
        }

        LogUtil.i("setRYAlarmCycleToDisplayCycle : ${cy.contentToString()}")
        return cy
    }

}