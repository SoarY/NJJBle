package com.njj.njjsdk.protocol.entity

/**
 * 跑步详情实体类
 *
 * @ClassName RunDetailsInfoData
 * @Description TODO
 * @Author Darcy
 * @Date 2021/4/23 15:37
 * @Version 1.0
 */
class NjjRunDetailsInfoData : Any(){
     var timeMill = 0

     var date //日期
            : String? = null

     var distance //实际里程（米）
            = 0

     var duration //实际耗时（秒）
            = 0

     var kcal //卡路里（卡）
            = 0

     var model
            = 0

     var heartRate //平均心率
            = 0

     var stepNum //步数
            = 0

     var heartRateArr:String?=null//心率数组
}