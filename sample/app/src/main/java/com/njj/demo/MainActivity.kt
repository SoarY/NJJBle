package com.njj.demo


import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.njj.demo.adapter.DeviceAdapter
import com.njj.njjsdk.callback.CallBackManager
import com.njj.njjsdk.callback.ConnectStatuesCallBack
import com.njj.njjsdk.callback.NjjSearchBack
import com.njj.njjsdk.library.Code
import com.njj.njjsdk.manger.NjjBleManger
import com.njj.njjsdk.protocol.entity.BLEDevice
import com.njj.njjsdk.utils.LogUtil
import com.wega.library.loadingDialog.LoadingDialog
import kotlinx.android.synthetic.main.activity_main.refresh_layout
import kotlinx.android.synthetic.main.activity_main.rv_device


class MainActivity : AppCompatActivity() {
    private lateinit var adapter: DeviceAdapter
    private lateinit var data: ArrayList<BLEDevice>
    private lateinit var mPermissionList: ArrayList<String>
    private var callBack: ConnectStatuesCallBack.ICallBack =
        object : ConnectStatuesCallBack.ICallBack {
            override fun onConnected(mac: String?) {
                LogUtil.e("连接成功")
                loadingDialog.loadComplete("连接成功")

            }

            override fun onConnecting(mac: String?) {
                LogUtil.e("连接中")
            }

            override fun onDisConnected(mac: String?) {
                LogUtil.e("断开连接")
                loadingDialog.loadComplete("断开连接")
            }

            override fun onDiscoveredServices(code: Int, mac: String?) {
                if (code == Code.REQUEST_SUCCESS) {
                    val intent = Intent(this@MainActivity, DeviceActivity::class.java)
                    startActivity(intent)
                }
            }

            override fun onConnectFail(mac: String?) {
                LogUtil.e("连接失败")
                loadingDialog.loadComplete("连接失败")
            }





        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView();
        initPermission();

    }


    // todo 蓝牙动态申请权限
    private fun initPermission() {
        mPermissionList = ArrayList();

    /*    if (Build.VERSION.SDK_INT >= 31) {
            // Android 版本大于等于 Android12 时
            // 只包括蓝牙这部分的权限，其余的需要什么权限自己添加
            mPermissionList.add("android.permission.BLUETOOTH_SCAN");
            mPermissionList.add("android.permission.BLUETOOTH_ADVERTISE");
            mPermissionList.add("android.permission.BLUETOOTH_CONNECT");

        } else {
            // Android 版本小于 Android12 及以下版本
            mPermissionList.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
            mPermissionList.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }
        val mPermissionList: MutableList<String> = java.util.ArrayList()*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            mPermissionList.add(Manifest.permission.BLUETOOTH_SCAN)
            mPermissionList.add(Manifest.permission.BLUETOOTH_ADVERTISE)
            mPermissionList.add(Manifest.permission.BLUETOOTH_CONNECT)
        } else {
            mPermissionList.add(Manifest.permission.BLUETOOTH)
            mPermissionList.add(Manifest.permission.BLUETOOTH_ADMIN)
        }
        mPermissionList.add(Manifest.permission.ACCESS_FINE_LOCATION)
        mPermissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        if (mPermissionList.size > 0) {
            ActivityCompat.requestPermissions(
                this, mPermissionList.toTypedArray(), 1001
            );
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        CallBackManager.getInstance().unregisterConnectStatuesCallBack("", callBack)
    }

    private val loadingDialog: LoadingDialog
        get() {
            return LoadingDialog.Builder(this).create()
        }

    private fun initView() {
        NjjBleManger.getInstance().init(this.application)
        CallBackManager.getInstance().registerConnectStatuesCallBack("", callBack)
        data = ArrayList()
        rv_device.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)



        adapter = DeviceAdapter(data) {
            loadingDialog.loading("连接中")
            refresh_layout.isRefreshing = false
            NjjBleManger.getInstance().scanStop()
            NjjBleManger.getInstance().clearRequest(data[it].device.address)
            NjjBleManger.getInstance().connectionRequest(data[it])
        }
        rv_device.adapter = adapter
//        refresh_layout.setOnRefreshListener(this)

    }


    fun onRefresh() {

        NjjBleManger.getInstance().scanBluetooth(object : NjjSearchBack {
            override fun onSearchStarted() {

            }

            override fun onDeviceFounded(device: BLEDevice?) {
                LogUtil.e(device?.device?.address)
                if (data.size > 0) {
                    var isHave = true;
                    for (i in data.indices) {
                        if (data[i].device.address.equals(device?.device?.address)) {
                            isHave = false
                            break
                        }
                    }
                    if (isHave)
                        data.add(device!!)
                } else {
                    device?.let { data.add(it) }
                }
                //排序
                data.sort()
                adapter.notifyDataSetChanged()
            }

            override fun onSearchStopped() {

            }

            override fun onSearchCanceled() {

            }

        }, "")
    }

    fun jili_test(view: android.view.View) {
        onRefresh()
    }

//    fun connect_test(view: android.view.View) {
//        val intent = Intent(this@MainActivity, ConnectTestActivity::class.java)
//        startActivity(intent)
//    }

    fun ota_test(view: android.view.View) {

    }

//    fun recovery_test(view: android.view.View) {
//        val intent = Intent(this@MainActivity, RecoveryTestActivity::class.java)
//        startActivity(intent)
//    }
}