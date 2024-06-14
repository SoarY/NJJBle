package com.njj.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.njj.demo.databinding.ActivityDeviceBinding
import com.njj.njjsdk.manger.NjjPushDataHelper
import com.njj.njjsdk.utils.LogUtil
import kotlinx.android.synthetic.main.activity_ota.*
import java.io.File

class JieLiOTAActivity : AppCompatActivity() {
    private var selectPath: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityDeviceBinding>(
            this,
            R.layout.activity_ota
        )
    }

    fun select_file(view: android.view.View) {
        // 打开文件管理器选择文件

        // 打开文件管理器选择文件
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        //intent.setType(“image/*”);//选择图片
        //intent.setType(“audio/*”); //选择音频
        //intent.setType(“video/*”); //选择视频 （mp4 3gp 是android支持的视频格式）
        //intent.setType(“video/*;image/*”);//同时选择视频和图片
        //intent.setType(“image/*”);//选择图片
        //intent.setType(“audio/*”); //选择音频
        //intent.setType(“video/*”); //选择视频 （mp4 3gp 是android支持的视频格式）
        //intent.setType(“video/*;image/*”);//同时选择视频和图片
        intent.type = "*/*" //无类型限制

        intent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(intent, mTag)

    }


    fun push_ota(view: android.view.View) {
        if (selectPath==null){
            Toast.makeText(this,"请选择Ota升级文件", Toast.LENGTH_SHORT)
        }else{

            var njjPushDataHelper =NjjPushDataHelper()
            njjPushDataHelper.startPushJieLiOTA(selectPath,object :NjjPushDataHelper.NJjPushListener {
                override fun onPushSuccess() {
                    tv_info.text = "推送成功"
                }

                override fun onPushError(code: Int) {
                    tv_info.text= "推送失败 错误码==$code"
                }

                override fun onPushStart() {
                    tv_info.text="开始推送"
                }

                override fun onPushProgress(progress: Int) {
                    tv_info.text= "推送中....%$progress"
                }
            })
        }


    }

    private val mTag = 0x100


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (requestCode == mTag) {
                val uri = data.data
                if (uri != null) {
                    val file = CameraUtils.uriToFile(this, uri)
                    selectPath = file.path
                    LogUtil.d("--------------------------------$selectPath--------------$uri")
                    if (TextUtils.isEmpty(selectPath)) {
//                        showToastShort("文件地址未找到");
                        return
                    }
                    if (!File(selectPath).exists()) {
//                        showToastShort("文件地址未找到");
                        return
                    }
                    if (selectPath?.endsWith("bin") != true) {
                        return
                    }
                } else {
//                    showToastShort("文件不存在");
                }
            } else {
                LogUtil.e("数据为空")
                //                showToastShort("文件不存在");
            }
        }
    }
}