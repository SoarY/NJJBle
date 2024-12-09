package com.njj.njjsdk.protocol.cmd.TypeConstant

/**
 * @ClassName CmdGPSConst
 * @Description TODO
 * @Author  LibinFan
 * @Date 2022/11/11 11:57
 * @Version 1.0
 */
const val GPS_CMD_GPS			= 0  //请求gps定位权限
const val GPS_CMD_COUNTDOWN	= 1  //请求开始倒计时
const val GPS_CMD_START 		= 2  //请求gps运动开始
const val GPS_CMD_SYN 		= 3  //同步gps运动数据
const val GPS_CMD_PAUSE 		= 4  //暂停
const val GPS_CMD_CONTINUE 	= 5  //继续
const val GPS_CMD_END 		= 6  //结束
const val GPS_CMD_APP_BUSY 	= 7  //APP 正忙，此时无法响应


/*typedef struct
{
	uint8_t sport_state;		//状态
    uint8_t sport_type;			//类型
	uint8_t sport_gps;			//gps定位权限
	uint8_t sport_hr;			//心率
    uint8_t sport_valid;        //数据是否有效
    uint8_t reserve1;
    uint8_t reserve2;
    uint8_t reserve3;
	uint32_t sport_time;		//时间	（秒）
	uint32_t sport_steps;		//步数
	uint32_t sport_kcal;		//卡路里（千卡）
	uint32_t sport_distance;	//距离	（米）
	uint32_t sport_speed;		//公里 	（小时）
} bcs_gps_sport_t, *bcs_gps_sport_p;



1.|0xBC|EVT_TYPE_GPS_SPORT|CTRL(0x03)|0x02|GPS_CMD_GPS|0|校验值|

GPS开启回复 ： |0xBC|EVT_TYPE_GPS_SPORT|CTRL(0x02)|0x02|GPS_CMD_GPS|1|校验值|
GPS关闭回复 ： |0xBC|EVT_TYPE_GPS_SPORT|CTRL(0x02)|0x02|GPS_CMD_GPS|0|校验值|
APP不响应回复：|0xBC|EVT_TYPE_GPS_SPORT|CTRL(0x02)|0x02|GPS_CMD_APP_BUSY|0|校验值|

2.|0xBC|EVT_TYPE_GPS_SPORT|CTRL(0x03)|0x02|GPS_CMD_COUNTDOWN|sport_id|校验值|

3.|0xBC|EVT_TYPE_GPS_SPORT|CTRL(0x03)|0x02|GPS_CMD_START|sport_id|校验值|

APP同步数据回复：|0xBC|EVT_TYPE_GPS_SPORT|CTRL(0x02)|0x02|GPS_CMD_SYN|bcs_gps_sport_t|校验值| （每秒检测数据有效填写）

固件同步心率：|0xBC|EVT_TYPE_GPS_SPORT|CTRL(0x03)|0x02|GPS_CMD_SYN|bcs_gps_sport_t|校验值|

4.|0xBC|EVT_TYPE_GPS_SPORT|CTRL(0x03/0x02)|0x02|GPS_CMD_PAUSE|sport_id|校验值|

5.|0xBC|EVT_TYPE_GPS_SPORT|CTRL(0x03/0x02)|0x02|GPS_CMD_CONTINUE|sport_id|校验值|

6.|0xBC|EVT_TYPE_GPS_SPORT|CTRL(0x03/0x02)|0x02|GPS_CMD_END|sport_id|校验值| (数据无效，app发送该指令，数据有效，直接ota)


bin包规则：填心率 + 填类型
【bcs_gps_sport_t + 全屏图】*/