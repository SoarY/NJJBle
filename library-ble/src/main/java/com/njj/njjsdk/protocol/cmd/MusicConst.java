
package com.njj.njjsdk.protocol.cmd;


public interface MusicConst {

    interface MusicType{
        int BLE_MEDIA_REC_CMD_STATE = 1;//播放状态
        int BLE_MEDIA_REC_CMD_VOLUME = 2;//音量
        int BLE_MEDIA_REC_CMD_LYRICS = 3;//歌词
        int BLE_MEDIA_REC_CMD_SONG_NAME = 4;//歌名
    }

    interface MediaCtrlType{
        int BLE_MEDIA_CTRL_PAUSE = 1;//暂停
        int BLE_MEDIA_CTRL_RESUME = 2;//播放
        int BLE_MEDIA_CTRL_NEXT = 3;//下⼀曲
        int BLE_MEDIA_CTRL_PREVIOUS = 4;//上⼀曲
        int BLE_MEDIA_CTRL_VOLUME = 5;//⾳量控制
    }
}
