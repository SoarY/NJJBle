package com.njj.njjsdk.entity;

/**
 * 实时录音数据
 */
public class RecordingDataEntity {
    /**
     * TYPE: 0:手表获取 APP 是否支持文心一言
     * TYPE: 5:手表获取 APP 是否支持翻译
     * TYPE: 1:手表端语音传输控制
     * TYPE: 2:语音传输数据
     */
    private int type;

    /**
     * 语音的语言
     */
    private int lang1;

    /**
     * 需要翻译为的文本语言
     */
    private int lang2;

    /**
     * 0:开始 1: 结束 当type==1时有效
     *
     */
    private int status;

    /**
     * 会话
     *  0:开启新会话 1:当前会话
     */
    private int session;

    /**
     *  ID:数据包是否拆分-0:单一数据包·不为 0:拆分的 D号，需要将数据包合并, 当type==2时有效
     */
    private int id;

    /**
     * 录音数据
     */
    private byte[] voiceData;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setLang1(int lang1) {
        this.lang1 = lang1;
    }

    public void setLang2(int lang2) {
        this.lang2 = lang2;
    }

    public int getLang1() {
        return lang1;
    }

    public int getLang2() {
        return lang2;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSession() {
        return session;
    }

    public void setSession(int session) {
        this.session = session;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getVoiceData() {
        return voiceData;
    }

    public void setVoiceData(byte[] voiceData) {
        this.voiceData = voiceData;
    }
}
