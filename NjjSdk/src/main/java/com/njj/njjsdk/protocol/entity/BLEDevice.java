package com.njj.njjsdk.protocol.entity;

import android.bluetooth.BluetoothDevice;

import java.io.Serializable;

/**
 * @ClassName BLEDevice
 * @Description TODO
 * @Author Darcy
 * @Date 2021/4/16 15:49
 * @Version 1.0
 */
public class BLEDevice implements Serializable, Comparable<BLEDevice> {

    private BluetoothDevice device; //蓝牙
    private String projectNo; //适配号
    private int rssi;        //信号值
    public byte[] scanRecord; //广播数据

    public BLEDevice() {

    }

    public BluetoothDevice getDevice() {
        return device;
    }

    public void setDevice(BluetoothDevice device) {
        this.device = device;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public byte[] getScanRecord() {
        return scanRecord;
    }

    public void setScanRecord(byte[] scanRecord) {
        this.scanRecord = scanRecord;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    @Override
    public int compareTo(BLEDevice o) {
        return Integer.compare(o.rssi, rssi);
    }

    @Override
    public String toString() {
        return "BLEDevice{" +
                ", deviceRssi=" + rssi + '}';
    }



}
