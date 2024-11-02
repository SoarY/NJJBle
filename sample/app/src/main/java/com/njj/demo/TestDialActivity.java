package com.njj.demo;


import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;


import com.njj.njjsdk.manger.NJJOtaManage;
import com.njj.njjsdk.manger.NjjPushDataHelper;
import com.njj.njjsdk.protocol.entity.EmergencyContact;
import com.njj.njjsdk.utils.LogUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


/**
 * 用于测试瑞昱表盘的页面
 *
 * @ClassName TestDialActviity
 * @Description TODO
 * @Author Darcy
 * @Date 2022/4/14 16:51
 * @Version 1.0
 */

public class TestDialActivity extends AppCompatActivity {
    private String selectPath;
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_ry_dial);
        initDatas();
    }


    protected void initDatas() {
        isPrepared = false;
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        ProtocolUtil.getInstance().unBindDevice();
//        BLESDKLocalData.getInstance().setMacAddress("");
    }

    boolean isPrepared;


    public void select_file(View view) {
        // 打开文件管理器选择文件
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //intent.setType(“image/*”);//选择图片
        //intent.setType(“audio/*”); //选择音频
        //intent.setType(“video/*”); //选择视频 （mp4 3gp 是android支持的视频格式）
        //intent.setType(“video/*;image/*”);//同时选择视频和图片
        intent.setType("*/*");//无类型限制
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, mTag);
    }

    private final int mTag = 0x100;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == mTag) {
                Uri uri = data.getData();
                if (uri != null) {
                    File file = CameraUtils.uriToFile(this, uri);
                    selectPath = file.getPath();
                    LogUtil.d("--------------------------------"+selectPath+"--------------"+uri);
                    if (TextUtils.isEmpty(selectPath)) {
//                        showToastShort("文件地址未找到");
                        return;
                    }
                    if (!new File(selectPath).exists()) {
//                        showToastShort("文件地址未找到");
                        return;
                    }
                    if (!selectPath.endsWith("bin")) {

                        return;
                    }

                } else {
//                    showToastShort("文件不存在");
                }

            } else {
                LogUtil.e("数据为空");
//                showToastShort("文件不存在");
            }
        }
    }


    public void pushContact(View view) {
        List<EmergencyContact> emergencyContacts=new ArrayList<>();
        for (int i=0;i<6;i++) {
            EmergencyContact emergencyContact = new EmergencyContact();
            emergencyContact.setContactName("乏力和"+i);
            emergencyContact.setPhoneNumber("1329857747"+i);
            emergencyContact.setContactId(i);
            emergencyContacts.add(emergencyContact);
        }

        NjjPushDataHelper njjPushDataHelper=new NjjPushDataHelper();
        njjPushDataHelper.startPushContactDial(emergencyContacts, new NjjPushDataHelper.NJjPushListener() {
            @Override
            public void onPushSuccess() {
                LogUtil.e("推送成功");
            }

            @Override
            public void onPushError(int code) {
                LogUtil.e("推送失败="+code);
            }

            @Override
            public void onPushStart() {
                LogUtil.e("开始推送=");
            }

            @Override
            public void onPushProgress(int progress) {
                LogUtil.e("推送progress="+progress);
            }
        });

    }

    public void pushDialReal(View view) {
        NjjPushDataHelper njjPushDataHelper=new NjjPushDataHelper();
        njjPushDataHelper.startPushDial(selectPath, new NjjPushDataHelper.NJjPushListener() {
            @Override
            public void onPushSuccess() {
                LogUtil.e("推送成功");
            }

            @Override
            public void onPushError(int code) {
                LogUtil.e("推送失败="+code);
            }

            @Override
            public void onPushStart() {
                LogUtil.e("开始推送=");
            }

            @Override
            public void onPushProgress(int progress) {
                LogUtil.e("推送progress="+progress);
            }
        });

    }

    public void pushUIOTA(View view) {
        NjjPushDataHelper njjPushDataHelper=new NjjPushDataHelper();
        njjPushDataHelper.startUIOTA(selectPath, new NjjPushDataHelper.NJjPushListener() {
            @Override
            public void onPushSuccess() {
                LogUtil.e("推送成功");
            }

            @Override
            public void onPushError(int code) {
                LogUtil.e("推送失败="+code);
            }

            @Override
            public void onPushStart() {
                LogUtil.e("开始推送=");
            }

            @Override
            public void onPushProgress(int progress) {
                LogUtil.e("推送progress="+progress);
            }
        });
    }
}
