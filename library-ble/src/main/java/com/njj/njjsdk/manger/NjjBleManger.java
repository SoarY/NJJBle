package com.njj.njjsdk.manger;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.text.TextUtils;

import com.njj.njjsdk.callback.CallBackManager;
import com.njj.njjsdk.callback.ConnectStatuesCallBack;
import com.njj.njjsdk.library.BluetoothClient;
import com.njj.njjsdk.library.Code;
import com.njj.njjsdk.library.Constants;
import com.njj.njjsdk.library.connect.listener.BleConnectStatusListener;
import com.njj.njjsdk.library.connect.options.BleConnectOptions;
import com.njj.njjsdk.library.connect.response.BleConnectResponse;

import com.njj.njjsdk.library.connect.response.BleMtuResponse;
import com.njj.njjsdk.library.connect.response.BleNotifyResponse;
import com.njj.njjsdk.library.connect.response.BleReadResponse;
import com.njj.njjsdk.library.connect.response.BleReadRssiResponse;
import com.njj.njjsdk.library.connect.response.BleUnnotifyResponse;
import com.njj.njjsdk.library.connect.response.BleWriteResponse;
import com.njj.njjsdk.library.model.BleGattProfile;
import com.njj.njjsdk.library.search.SearchRequest;
import com.njj.njjsdk.library.search.SearchResult;
import com.njj.njjsdk.library.search.response.SearchResponse;

import com.njj.njjsdk.base.UUIDConfig;
import com.njj.njjsdk.callback.BleConnectionListener;
import com.njj.njjsdk.callback.NjjSearchBack;

import com.njj.njjsdk.protocol.entity.BLEDevice;
import com.njj.njjsdk.utils.BleBeaconUtil;
import com.njj.njjsdk.utils.ByteAndStringUtil;
import com.njj.njjsdk.utils.LogUtil;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.Observer;

import static com.njj.njjsdk.library.Constants.STATUS_CONNECTED;
import static com.njj.njjsdk.library.Constants.STATUS_DISCONNECTED;

/**
 * 蓝牙操作管理类
 */
public class NjjBleManger {

    //作为一个全局单例，管理所有BLE设备的连接。
    private BluetoothClient bluetoothClient;
    private BleConnectOptions options;
    private SearchRequest request;
    //瑞昱厂商识别码
    private final String NJYID = "BCBC";
    private BLEDevice mBleDevice;
    private boolean needPair=true;
    public BLEDevice getmBleDevice() {
        return mBleDevice;
    }


    public BluetoothClient getBluetoothClient() {
        return bluetoothClient;
    }



    public void init(Context context) {
        NjjProtocolHelper.getInstance().init();
        bluetoothClient = new BluetoothClient(context);
        options = new BleConnectOptions.Builder()
                .setConnectRetry(1)   // 连接如果失败重试1次
                .setConnectTimeout(30000)   // 连接超时30s
                .setServiceDiscoverRetry(3)  // 发现服务如果失败重试3次
                .setServiceDiscoverTimeout(20000)  // 发现服务超时20s
                .build();


        request = new SearchRequest.Builder()
                .searchBluetoothLeDevice(1000 * 10, 1)   // 先扫BLE设备1次，每次20s
                .build();
    }


    public void init(Context context,Builder builder) {
        NjjProtocolHelper.getInstance().init();
        bluetoothClient = new BluetoothClient(context);
        needPair=builder.needPair;
        options = new BleConnectOptions.Builder()
                .setConnectRetry(builder.connectRetry)   // 连接如果失败重试1次
                .setConnectTimeout(builder.connectTimeout)   // 连接超时30s
                .setServiceDiscoverRetry(builder.serviceDiscoverRetry)  // 发现服务如果失败重试3次
                .setServiceDiscoverTimeout(builder.serviceDiscoverTimeout)  // 发现服务超时20s
                .build();

        request = new SearchRequest.Builder()
                .searchBluetoothLeDevice(builder.searchDuration, 1)   // 先扫BLE设备1次，每次20s
                .build();
    }

    public void creteBond(String mac) {
        if (needPair){
            SearchRequest build = new SearchRequest.Builder()
//                .searchBluetoothLeDevice(1000 * 20, 1)   // 先扫BLE设备1次，每次20s
                    .searchBluetoothClassicDevice(2000 * 10, 1) // 再扫经典蓝牙5s
                    //.searchBluetoothLeDevice(2000)      // 再扫BLE设备2s
                    .build();

            bluetoothClient.search(build, new SearchResponse() {
                @Override
                public void onSearchStarted() {

                }

                @Override
                public void onDeviceFounded(SearchResult searchResult) {
                    if (searchResult.getName().contains("Watch Call")) {
                        LogUtil.e(searchResult.getName() + "mac=" + searchResult.getAddress());
                    }
                    if (mac.equals(searchResult.device.getAddress())) {
                        if (searchResult.device.getBondState() != BluetoothDevice.BOND_BONDED) {
                            boolean bond = searchResult.device.createBond();
                            if (bond) {
                                bluetoothClient.stopSearch();
                            }
                        } else {
                            bluetoothClient.stopSearch();
                        }

                    }
                }

                @Override
                public void onSearchStopped() {

                }

                @Override
                public void onSearchCanceled() {

                }
            });
        }
    }

    private static NjjBleManger instance;


    /**
     * 写入数据
     * <p>
     * 此命令是用于写入公共数据的，像同步时间，运动数据等等
     * </p>
     *
     * @param cmd 命令
     * @date 2021-4-15 13:39:57
     */
    public void writeData(byte[] cmd) {
        if (!isConnect()) {
            LogUtil.e("设备断开不能写入命令");
            return;
        }
        writeData(cmd, i -> {
            if (i == Constants.REQUEST_SUCCESS) {
                LogUtil.e("发送成功");
            } else {
                LogUtil.e("发送失败");
            }
        });
    }

    public void writeData(byte[] cmd, BleWriteResponse response) {
        if (!isConnect()) {
            LogUtil.e("设备断开不能写入命令");
            return;
        }
        writeCommand(mBleDevice.getDevice().getAddress(), cmd, response);
    }


    /**
     * 判断设备是否已经连接
     * @param mac 蓝牙地址
     * @return boolean true:已经连接  false 断开连接
     * @date 2019-02-13
     */
    public Boolean isConnect(String mac) {
        int status = bluetoothClient.getConnectStatus(mac);
        if (Constants.STATUS_DEVICE_CONNECTED == status) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isConnect() {
        if (mBleDevice == null) {
            LogUtil.e("要判断连接的蓝牙地址为空....");
            return false;
        }
        return isConnect(mBleDevice.getDevice().getAddress());
    }

    /**
     * 是否支持ble 蓝牙
     *
     * @return boolean true :支持 false:不支持
     * @Date 2018-11-07
     */
    public boolean isSupport() {
        boolean bleSupported = bluetoothClient.isBleSupported();
        if (bleSupported) {
            return true;
        } else {
            return false;
        }
    }


    public void registerConnectStatue(String mac) {
        registerConnection(mac,statusChangeListener);
    }

    public void unregisterConnectStatue() {
        unRegisterConnection(mBleDevice.getDevice().getAddress(),statusChangeListener);
    }

    private final BleConnectStatusListener statusChangeListener = new BleConnectStatusListener() {
        @Override
        public void onConnectStatusChanged(String mac, int status) {
            if (status == STATUS_CONNECTED) {
                ConnectStatuesCallBack.onConnected(mac);
            } else if (status == STATUS_DISCONNECTED) {
                ConnectStatuesCallBack.onDisConnected(mac);
            }
        }
    };
    /**
     * 注册连接监听
     *
     * @param mac                       蓝牙mac地址
     * @param mBleConnectStatusListener 蓝牙状态连接监听
     * @return void
     * @date 2019-01-23
     */
    public void registerConnection(String mac, BleConnectStatusListener mBleConnectStatusListener) {
        bluetoothClient.registerConnectStatusListener(mac, mBleConnectStatusListener);
    }

    /**
     * @param mac                       蓝牙mac地址
     * @param mBleConnectStatusListener 蓝牙连接监听
     * @return void
     * @date 2019-01-23
     */
    public void unRegisterConnection(String mac, BleConnectStatusListener mBleConnectStatusListener) {
        bluetoothClient.unregisterConnectStatusListener(mac, mBleConnectStatusListener);
    }


    /**
     * 设备蓝牙是否打开
     *
     * @return boolean
     * @Date 2018-11-07
     */
    public boolean isOpen() {
        boolean bluetoothOpened = bluetoothClient.isBluetoothOpened();
        if (bluetoothOpened) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 读取数据
     *
     * @param mac           mac地址
     * @param serviceUuid   服务
     * @param characterUuid 特征
     * @return void
     * @date 2019-02-15
     */
    public void readData(String mac, UUID serviceUuid, UUID characterUuid, BleReadResponse readResponse) {
        bluetoothClient.read(mac, serviceUuid, characterUuid, readResponse);
    }


    public void openNotify(String mac, BleNotifyResponse bleNotifyResponse) {
        bluetoothClient.notify(mac, UUIDConfig.SERVICE_UUID_NJY, UUIDConfig.NOTIFY_UUID_NJY, bleNotifyResponse);
    }


    /**
     * 关闭通知
     *
     * @param mac 蓝牙mac地址
     * @return void
     * @date 2012-07-23
     */
    public void closeNotify(String mac, UUID serviceUuid, UUID characterUuid) {
        bluetoothClient.unnotify(mac, serviceUuid, characterUuid, new BleUnnotifyResponse() {
            @Override
            public void onResponse(int code) {

            }
        });
    }


    /**
     * @param mtu      更改的大小
     * @param response 回调
     */
    public void requestMtu(String mac, int mtu, BleMtuResponse response) {
        bluetoothClient.requestMtu(mac, mtu, response);
    }


    /**
     * 写入命令
     *
     * @param mac      m
     * @param cmd      指令
     * @param response 回调
     * @return
     * @date 2021-4-14 13:54:27
     */
    public void writeCommand(String mac, byte[] cmd, BleWriteResponse response) {

        UUID serviceUUid;
        UUID writeUUid;
        serviceUUid = UUIDConfig.SERVICE_UUID_NJY;
        writeUUid = UUIDConfig.WRITE_UUID_NJY;
        bluetoothClient.writeNoRsp(mac, serviceUUid, writeUUid,
                cmd, response);
    }


    public void writeDailRuiYu(byte[] command, BleWriteResponse response) {
        bluetoothClient.writeNoRsp(mBleDevice.getDevice().getAddress(), UUIDConfig.SERVICE_UUID_NJY, UUIDConfig.WRITE_UUID_NJY,
                command, response);
    }


    /**
     * 连接蓝牙设备
     *
     * @param
     * @param bleDevice
     * @return void
     * @date 2019-01-23
     */
    public void connectionRequest(BLEDevice bleDevice) {
        Map<String, String> beaconMap = BleBeaconUtil.parseData(bleDevice.getScanRecord());
        String id = beaconMap.get("ID");
        if (!id.startsWith(NJYID)){
            ConnectStatuesCallBack.onConnectFail(bleDevice.getDevice().getAddress());
        }else {
            ConnectStatuesCallBack.onConnecting(bleDevice.getDevice().getAddress());
            bluetoothClient.connect(bleDevice.getDevice().getAddress(), options, new BleConnectResponse() {
                @Override
                public void onResponse(int i, BleGattProfile bleGattProfile) {
                    if (i == Code.REQUEST_SUCCESS) {
                        LogUtil.e("连接成功");
                        NjjProtocolHelper.getInstance().openNotify(bleDevice.getDevice().getAddress());
                        mBleDevice = bleDevice;
                        ConnectStatuesCallBack.onConnected(bleDevice.getDevice().getAddress());
                        registerConnectStatue(bleDevice.getDevice().getAddress());
                    }else {
                        ConnectStatuesCallBack.onConnectFail(bleDevice.getDevice().getAddress());
//                        LogUtil.e("连接失败");
                    }
                }
            });
        }
    }

    /**
     * 连接蓝牙设备
     *
     * @param
     * @param bluetoothDevice
     * @return void
     * @date 2019-01-23
     */
    public void connectionNormalRequest(BluetoothDevice bluetoothDevice, byte[] adv_data) {
        Map<String, String> beaconMap = BleBeaconUtil.parseData(adv_data);
        String id = beaconMap.get("ID");
        if (!id.startsWith(NJYID)){
            ConnectStatuesCallBack.onConnectFail(bluetoothDevice.getAddress());
        }else {
            ConnectStatuesCallBack.onConnecting(bluetoothDevice.getAddress());
            bluetoothClient.connect(bluetoothDevice.getAddress(), options, new BleConnectResponse() {
                @Override
                public void onResponse(int i, BleGattProfile bleGattProfile) {
                    if (i == Code.REQUEST_SUCCESS) {
//                        LogUtil.e("连接成功");
                        NjjProtocolHelper.getInstance().openNotify(bluetoothDevice.getAddress());
                        BLEDevice bleDevice = new BLEDevice();
                        bleDevice.setRssi(0);
                        bleDevice.setScanRecord(adv_data);
                        bleDevice.setDevice(bluetoothDevice);
                        if (beaconMap.get("company") != null && beaconMap.get("company").length() > 11 && Objects.requireNonNull(beaconMap.get("company")).substring(0, 6).equals("FF0101")) {
                            String company = beaconMap.get("company").substring(6, 12);
                            bleDevice.setProjectNo(company);
//                            LogUtil.e(company);
                        }
                        mBleDevice = bleDevice;
                        ConnectStatuesCallBack.onConnected(bluetoothDevice.getAddress());
                    }else {
                        ConnectStatuesCallBack.onConnectFail(bluetoothDevice.getAddress());
                    }
                }
            });
        }
    }

//    ConnectStatusListener connectStatusListener;

    public void discoveredServices(int code, String macAddress) {
        ConnectStatuesCallBack.onDiscoveredServices(code,macAddress);
    }


    /**
     * 断开连接
     *
     * @param
     * @return void
     * @date 2019-01-23
     */
    public void disConnection() {
        bluetoothClient.disconnect(mBleDevice.getDevice().getAddress());
    }

    /**
     * 关闭连接
     *
     * @return void
     * @date 2019-01-23
     */
    public void closeBluetooth() {
        bluetoothClient.closeBluetooth();
    }

    /**
     * 打开连接
     *
     * @return void
     * @date 2019-01-23
     */
    public void openBluetooth() {
        bluetoothClient.openBluetooth();
    }

    /**
     * 搜索蓝牙设备
     * <br>
     * 每次搜索的时候都要重新生成对应的request
     * 不可复用
     * </br>
     */
    public void scanBluetooth(NjjSearchBack njjSearchBack, String... flitName) {
        bluetoothClient.search(request, new SearchResponse() {
            @Override
            public void onSearchStarted() {
                njjSearchBack.onSearchStarted();
            }

            @Override
            public void onDeviceFounded(SearchResult device) {
                String[] flitNames = flitName.clone();
                if (TextUtils.isEmpty(device.getName()) || "NULL".equals(device.getName()))
                    return;
                if (flitNames.length > 0) {
                    for (int i = 0; i < flitNames.length; i++) {
                        if (device.getName().startsWith(flitNames[i])) {
                            Map<String, String> map = BleBeaconUtil.parseData(device.scanRecord);
//                            LogUtil.e(map.toString());
                            BLEDevice bleDevice = new BLEDevice();
                            bleDevice.setRssi(device.rssi);
                            bleDevice.setScanRecord(device.scanRecord);
                            bleDevice.setDevice(device.device);
                            if (map.get("company") != null && map.get("company").length() > 11 && Objects.requireNonNull(map.get("company")).substring(0, 6).equals("FF0101")) {
                                String company = map.get("company").substring(6, 12);
                                bleDevice.setProjectNo(company);
//                                LogUtil.e(company);
                            }

                            if (map.get("company") != null && map.get("company").length() > 11 && Objects.requireNonNull(map.get("company")).substring(0, 6).equals("FF0102")) {
                                String company = map.get("company").substring(6, 12);
                                bleDevice.setProjectNo(company);
//                                LogUtil.e(company);
                            }
                            njjSearchBack.onDeviceFounded(bleDevice);
                            break;
                        }
                    }
                }
                else {
                    Map<String, String> map = BleBeaconUtil.parseData(device.scanRecord);
//                    LogUtil.e(map.toString());
                    BLEDevice bleDevice = new BLEDevice();
                    bleDevice.setRssi(device.rssi);
                    bleDevice.setScanRecord(device.scanRecord);
                    bleDevice.setDevice(device.device);
                    if (map.get("company") != null && map.get("company").length() > 11 && Objects.requireNonNull(map.get("company")).substring(0, 6).equals("FF0101")) {
                        String company = map.get("company").substring(6, 12);
                        bleDevice.setProjectNo(company);
                    }
                    if (map.get("company") != null && map.get("company").length() > 11 && Objects.requireNonNull(map.get("company")).substring(0, 6).equals("FF0102")) {
                        String company = map.get("company").substring(6, 12);
                        bleDevice.setProjectNo(company);
//                        LogUtil.e(company);
                    }
                    njjSearchBack.onDeviceFounded(bleDevice);
                }

            }

            @Override
            public void onSearchStopped() {
                njjSearchBack.onSearchStopped();
            }

            @Override
            public void onSearchCanceled() {
                njjSearchBack.onSearchCanceled();
            }
        });
    }



    /**
     * 停止扫描
     */
    public void scanStop() {
        if (bluetoothClient == null)
            return;
        bluetoothClient.stopSearch();
    }


    /**
     * 读取信号值
     *
     * @param mac              蓝牙的Mac地址
     * @param readRssiResponse 回调
     * @return void
     * @date 2019-08-27
     */
    public void readRssi(String mac, BleReadRssiResponse readRssiResponse) {
        bluetoothClient.readRssi(mac, readRssiResponse);
    }


    /**
     * 清除相关请求
     * // Constants.REQUEST_READ，所有读请求
     * // Constants.REQUEST_WRITE，所有写请求
     * // Constants.REQUEST_NOTIFY，所有通知相关的请求
     * // Constants.REQUEST_RSSI，所有读信号强度的请求
     *
     * @param
     * @return
     * @date 2021-4-15 17:48:36
     */
    public void clearRequest(String mac) {
        bluetoothClient.clearRequest(mac, Constants.REQUEST_WRITE);
        bluetoothClient.clearRequest(mac, Constants.REQUEST_READ);
        bluetoothClient.clearRequest(mac, Constants.REQUEST_NOTIFY);
        bluetoothClient.clearRequest(mac, Constants.CODE_CONNECT);
    }

    public void clearWrite(){
        if (mBleDevice!=null)
            bluetoothClient.clearRequest(mBleDevice.getDevice().getAddress(), Constants.REQUEST_WRITE);
    }


    public static NjjBleManger getInstance() {
        if (null == instance) {
            synchronized (NjjBleManger.class) {
                if (null == instance) {
                    instance = new NjjBleManger();
                }
            }
        }
        return instance;
    }

    public static class Builder {
        private static final int DEFAULT_CONNECT_RETRY = 0;
        private static final int DEFAULT_SERVICE_DISCOVER_RETRY = 0;
        private static final int DEFAULT_CONNECT_TIMEOUT=  30000;
        private static final int DEFAULT_SERVICE_DISCOVER_TIMEOUT = 30000;
        private static final int DEFAULT_searchDuration = 5*1000;
        private static final boolean DEFAULT_needPair=true;

        private int connectRetry = DEFAULT_CONNECT_RETRY;
        private int serviceDiscoverRetry = DEFAULT_SERVICE_DISCOVER_RETRY;
        private int connectTimeout = DEFAULT_CONNECT_TIMEOUT;
        private int serviceDiscoverTimeout = DEFAULT_SERVICE_DISCOVER_TIMEOUT;
        private int searchDuration = DEFAULT_searchDuration;
        private boolean needPair=DEFAULT_needPair;

        public NjjBleManger.Builder setConnectRetry(int retry) {
            connectRetry = retry;
            return this;
        }

        public NjjBleManger.Builder setServiceDiscoverRetry(int retry) {
            serviceDiscoverRetry = retry;
            return this;
        }

        public NjjBleManger.Builder setConnectTimeout(int timeout) {
            connectTimeout = timeout;
            return this;
        }

        public NjjBleManger.Builder setServiceDiscoverTimeout(int timeout) {
            serviceDiscoverTimeout = timeout;
            return this;
        }

        public NjjBleManger.Builder setSearchDuration(int duration) {
            this.searchDuration = duration;
            return this;
        }

        public NjjBleManger.Builder setNeedPair(boolean needPair) {
            this.needPair = needPair;
            return this;
        }

        public NjjBleManger build() {
            return new NjjBleManger();
        }
    }

}
