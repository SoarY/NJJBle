package com.njj.demo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.njj.demo.adapter.MacAdapter;
import com.njj.demo.utils.FileHelper;
import com.njj.njjsdk.callback.CallBackManager;
import com.njj.njjsdk.callback.ConnectStatuesCallBack;
import com.njj.njjsdk.callback.NjjSearchBack;
import com.njj.njjsdk.library.Code;
import com.njj.njjsdk.manger.NjjBleManger;
import com.njj.njjsdk.manger.NjjProtocolHelper;
import com.njj.njjsdk.protocol.entity.BLEDevice;
import com.njj.njjsdk.utils.LogUtil;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecoveryTestActivity extends AppCompatActivity {
    private  boolean isConnect=false;
    private BLEDevice mBleDevice;
    private RecyclerView recyclerView;
    private List<BLEDevice> macSuccess = new ArrayList<>();
    private MacAdapter macSuccessAdapter;
    private Context context;

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};
    //请求状态码
    private static int REQUEST_PERMISSION_CODE = 1;
    private EditText et_fil;
    private TextView tvSian;
    private int type=0;
    private boolean isStart;

    //检测是否有写的权限
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_test);
        context=this;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_PERMISSION_CODE);
            }
        }



        initVIew();
        CallBackManager.getInstance().registerConnectStatuesCallBack("",connectCallBack);
    }

    private void initVIew() {
        recyclerView=findViewById(R.id.rv_connect);
        et_fil = findViewById(R.id.et_fil);
        tvSian = findViewById(R.id.tv_sian);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        macSuccessAdapter = new MacAdapter(macSuccess);
        // 设置Adapter
        recyclerView.setAdapter(macSuccessAdapter);
        // 设置分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CallBackManager.getInstance().unregisterConnectStatuesCallBack("",connectCallBack);
    }

    private final ConnectStatuesCallBack.ICallBack connectCallBack=new ConnectStatuesCallBack.ICallBack() {

        private String content;

        @Override
        public void onConnected(String mac) {

        }

        @Override
        public void onConnecting(String mac) {
            isConnect=true;
        }

        @Override
        public void onDisConnected(String mac) {
            LogUtil.e("连接失败"+isConnect);
            if (isConnect) {
                tvSian.setText("连接失败,重新搜索");
                LogUtil.e("连接失败,重新搜索");
                if (type == 0) {
                    scan();
                } else {
                    scan(mac);
                }
            }
        }

        @Override
        public void onConnectFail(int code) {
            LogUtil.e("连接失败"+isConnect);
            if (isConnect) {
                tvSian.setText("连接失败,重新搜索");
                LogUtil.e("连接失败,重新搜索");
                if (type == 0) {
                    scan();
                } else {
                    scan(mBleDevice.getDevice().getAddress());
                }
            }
        }

        @Override
        public void onDiscoveredServices(int code) {
            if (code== Code.REQUEST_SUCCESS){
                isConnect=false;
                try {
                    if (mBleDevice.getDevice()!=null){
                        content=mBleDevice.getDevice().getAddress();
                        String mac = mBleDevice.getDevice().getAddress()+ "\r\n";
                        FileHelper.writeFileO(mac,FileHelper.getAppRootDirPath().getAbsolutePath(),"name.txt",true);
                    }
                }catch (Exception e){

                }
                if (type==0){
                    tvSian.setText("重启设备中...等待设备连接..");
                    NjjProtocolHelper.getInstance().reset();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            NjjBleManger.getInstance().disConnection();
                            NjjBleManger.getInstance().clearRequest(content);
                            scan(content);
                        }
                    },1000);
                }else {
                    tvSian.setText("设备关机..");
                    NjjProtocolHelper.getInstance().shutdown();
                    //连接成功 ，加入进去，2 ，关机
                    macSuccess.add(mBleDevice);
                    macSuccessAdapter.notifyDataSetChanged();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            NjjBleManger.getInstance().disConnection();
                            scan();
                        }
                    },2*1000);
                }


            }


        }
    };

    private void scan(String mac) {
        type=1;
        NjjBleManger.getInstance().scanStop();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mBleDevice=null;
                NjjBleManger.getInstance().scanBluetooth(new NjjSearchBack() {
                    @Override
                    public void onSearchStarted() {

                    }

                    @Override
                    public void onDeviceFounded(@Nullable BLEDevice device) {
                        if (device.getDevice().getAddress().equals(mac)){
                            NjjBleManger.getInstance().scanStop();
                            mBleDevice=device;
                            connectDevice(device);
                        }
                    }

                    @Override
                    public void onSearchStopped() {
                        LogUtil.e("stop");
                        scan();
                    }

                    @Override
                    public void onSearchCanceled() {
                        LogUtil.e("cancel");
                    }
                });
            }
        },15*1000);
    }

    public void start(View view) {
        if (!isStart){
            isStart = true;
            scan();
        }


    }

    private void scan() {
        NjjBleManger.getInstance().scanStop();
        type=0;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mBleDevice=null;
                NjjBleManger.getInstance().scanBluetooth(new NjjSearchBack() {
                    @Override
                    public void onSearchStarted() {

                    }

                    @Override
                    public void onDeviceFounded(@Nullable BLEDevice device) {
                        if (!TextUtils.isEmpty(device.getProjectNo())&&device.getProjectNo().startsWith(et_fil.getText().toString())){
                            NjjBleManger.getInstance().scanStop();
                            mBleDevice=device;
                            connectDevice(device);
                        }
                    }

                    @Override
                    public void onSearchStopped() {
                        LogUtil.e("stop");
                        scan();
                    }

                    @Override
                    public void onSearchCanceled() {
                        LogUtil.e("cancel");
                    }
                });
            }
        },5*1000);

    }

    private void connectDevice(BLEDevice device) {
        tvSian.setText("正在连接...mac=="+device.getDevice().getAddress()+" name=="+device.getDevice().getName());
        NjjBleManger.getInstance().connectionRequest(device);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                Log.i("MainActivity", "申请的权限为：" + permissions[i] + ",申请结果：" + grantResults[i]);
            }
        }
    }

}