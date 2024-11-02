package com.njj.demo

import android.app.ProgressDialog
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.njj.demo.utils.DpUtil
import com.njj.demo.utils.FileHelper
import com.njj.njjsdk.manger.NjjPushDataHelper
import com.njj.njjsdk.utils.LogUtil
import kotlinx.android.synthetic.main.activity_dial_custum.*
import java.io.File
import java.nio.ByteBuffer
import java.text.SimpleDateFormat
import java.util.*


class DialCustumActivity : AppCompatActivity(),
    RadioGroup.OnCheckedChangeListener {
    private var timePosition: Int = 1
    private var color: Int = 0
    val REQUEST_CODE_ALBUM = 1001
    val REQUEST_CODE_CAPTURE_CROP = 1002

    private var dialog: ProgressDialog? = null
    private var startTime = 0L

    private var imageCropFile: File? = null
    private var mCustomDialBgPath: String = ""
    private lateinit var mDate: String
    private lateinit var mTime: String

    private lateinit var rgpDialColor: FlowRadioGroup
    private val mColorIds = mutableListOf<Int>(
        R.color.color_btn1, R.color.color_btn2, R.color.color_btn3, R.color.color_btn4,
        R.color.color_btn5, R.color.color_btn6, R.color.color_btn7, R.color.color_btn8,
        R.color.color_btn9, R.color.color_btn10, R.color.color_btn11, R.color.color_btn12
    )



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dial_custum)
        dialog = ProgressDialog(this)
        dialog?.setCancelable(false)

        val dateTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
        mDate = dateTime.substring(0, 10)
        mTime = dateTime.substring(11, 16)

        rgpDialColor = findViewById(R.id.rgp_dial_color)

        rg_position.setOnCheckedChangeListener(this)


        //时间位置默认上方
        rg_position.check(R.id.rb_position_up)

        initRB()

    }

    fun initRB() {
        val layoutParams = RadioGroup.LayoutParams(
            RadioGroup.LayoutParams.WRAP_CONTENT,
            DpUtil.dp2px(this, 20)
        )
        rgpDialColor.removeAllViews()
        var i = 0
        while (i < mColorIds.size) {
            val radioButton = RadioButton(this)
            radioButton.id = i
            radioButton.buttonTintList = ColorStateList(
                arrayOf(
                    intArrayOf(-android.R.attr.state_enabled),
                    intArrayOf(android.R.attr.state_enabled)
                ), intArrayOf(
                    Color.GRAY, resources.getColor(
                        mColorIds[i]
                    )
                )
            )
            layoutParams.setMargins(0, 0, 0, 0)
            rgpDialColor.addView(radioButton, layoutParams)
            i++
        }

        //初始化字体颜色
        rgpDialColor.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            val colorId = mColorIds[checkedId]
            color = resources.getColor(colorId)
            Log.i("yy", "checkedId=$checkedId, color=$color")

//            mCustomDialConfig.textColor = color
            updateUI()
        })
        rgpDialColor.check(0)

    }

    fun pushCustomDialBg(view: View) {
        Log.i("yy", "path=$mCustomDialBgPath")
        if (!TextUtils.isEmpty(mCustomDialBgPath)) {
            val file = File(mCustomDialBgPath)
            if (file.exists()) {
                Log.i(
                    "yy",
                    "customBG size=${file.length() / 1024}KB,mCustomDialBgPath=$mCustomDialBgPath"
                )

            }

        } else {
            Toast.makeText(this@DialCustumActivity, "请先选择背景图片", Toast.LENGTH_LONG).show()
        }
    }

    fun toHexEncoding(color: Int): String? {
        val sb = StringBuffer()
        var R: String = Integer.toHexString(Color.red(color))
        var G: String = Integer.toHexString(Color.green(color))
        var B: String = Integer.toHexString(Color.blue(color))
        //判断获取到的R,G,B值的长度 如果长度等于1 给R,G,B值的前边添0
        R = if (R.length == 1) "0$R" else R
        G = if (G.length == 1) "0$G" else G
        B = if (B.length == 1) "0$B" else B
        sb.append(R)
        sb.append(G)
        sb.append(B)
        return sb.toString()
    }

    fun pushCustomDialConfig(view: View) {
//        Log.i("yy", "updateDialConfig-->mCustomDialConfig=${mCustomDialConfig.toString()}")
//        if (isBleConnected()) {
//            bleConnectHelper.pushCustomDialConfig(mCustomDialConfig)
//        }

        var njjPushDataHelper =NjjPushDataHelper();
        njjPushDataHelper.starPushCustomDial(
            imageCropFile!!.absolutePath.toString(),320,380,240,283,timePosition,toHexEncoding(color),
             object : NjjPushDataHelper.NJjPushListener {
                 override fun onPushSuccess() {
                     LogUtil.e("推送成功")

                     val costTime = (System.currentTimeMillis() - startTime) / 1000
                     Log.i("yy", "end costTime=${costTime}s")
                     dialog?.dismiss()
                     Toast.makeText(
                         this@DialCustumActivity,
                         "自定义表盘背景图片发送完成",
                         Toast.LENGTH_LONG
                     ).show()
                 }

                 override fun onPushError(code: Int) {
                     LogUtil.e("推送失败=$code")

                     dialog?.dismiss()
                     Log.i("yy", "onPushDataError-->code=$code")
                     Toast.makeText(
                         this@DialCustumActivity,
                         "" + code,
                         Toast.LENGTH_LONG
                     ).show()
                 }

                 override fun onPushStart() {
                     LogUtil.e("开始推送=")
                     startTime = System.currentTimeMillis()
                     if (dialog?.isShowing == false) {
                         dialog?.setCancelable(false)
                         dialog?.show()
                         dialog?.setMessage("0%")
                         dialog?.progress = 0
                     }
                 }

                 override fun onPushProgress(progress: Int) {
                     LogUtil.e("推送progress=$progress")
                     dialog?.setMessage("$progress%")
                     dialog?.progress = progress
                 }

             })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.i("yy", "onActivityResult-->requestCode=$requestCode,resultCode=$resultCode,data=$data")
        if (requestCode == REQUEST_CODE_ALBUM) {
            if (data?.data != null) {
                //打开系统裁剪
                data.data?.let { gotoCrop(it) };
            }
        } else if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_CAPTURE_CROP) {
            //显示页面上
            if (imageCropFile != null && imageCropFile!!.absolutePath != null) {
                if (Build.VERSION.SDK_INT >= 30) {
                    if (FileHelper.uri != null) {
                        // 通过存储的uri 查询File
                        imageCropFile = FileHelper.getCropFile(this, FileHelper.uri);
                    }
                } else {
                    Log.i("yy", "cropFile=${imageCropFile!!.absoluteFile}")
                }
                //转成RGB565的16位位图
                val smallBitmap = FileHelper.getSmallBitmap(
                    BitmapFactory.decodeFile(imageCropFile!!.absoluteFile.toString()),
320,380
                )
                //保存bitmap到file
                if (FileHelper.saveBitmap(smallBitmap, FileHelper.CUSTOM_DIAL_BG_PATH)) {
                    mCustomDialBgPath = FileHelper.CUSTOM_DIAL_BG_PATH
                }

                updateUI()
            }
        }
    }

    private fun updateUI() {
        var upData = ""


        var timeData = ""
        when (timePosition) {
            0,
            1 -> {
                timeData = mTime
            }
        }




        val sb = StringBuilder()
        sb.append(upData)
        sb.append("#")
        sb.append(timeData)
        drawBitmap()
    }

    private fun gotoCrop(sourceUri: Uri) {
        imageCropFile = FileHelper.createImageFile(this, true)
        if (imageCropFile != null) {
            val intent = Intent("com.android.camera.action.CROP")
            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            intent.putExtra("crop", "true")
            intent.putExtra("aspectX",320) //X方向上的比例
            intent.putExtra("aspectY", 380) //Y方向上的比例
            intent.putExtra("outputX", 320) //裁剪区的宽
            intent.putExtra("outputY",380) //裁剪区的高
            intent.putExtra("scale ", true) //是否保留比例
            intent.putExtra("return-data", false)
            intent.putExtra("outputFormat", "png")
            intent.setDataAndType(sourceUri, "image/*") //设置数据源
            if (Build.VERSION.SDK_INT >= 30) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, FileHelper.uri)
            } else {
                val imgCropUri: Uri = Uri.fromFile(imageCropFile)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imgCropUri)
            }
            startActivityForResult(intent, REQUEST_CODE_CAPTURE_CROP)
        }
    }

    fun selectPicture(view: View) {
        //打开系统相册
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_CODE_ALBUM)
    }


    override fun onCheckedChanged(radioGroup: RadioGroup?, i: Int) {
        if (radioGroup != null) {
            Log.i("yy", "onCheckedChanged id=$radioGroup.id")
            when (radioGroup.id) {
                R.id.rg_position -> {
                    when (i) {
                        R.id.rb_position_up -> {
                            timePosition =1
                        }
                        R.id.rb_position_down -> {
                           timePosition = 0
                        }
                    }
                }

            }
            updateUI()

        }
    }



    private fun drawBitmap() {
        var bitmap: Bitmap?
        if (imageCropFile != null) {
            val data = FileHelper.getByteStream(FileHelper.CUSTOM_DIAL_BG_PATH)
            bitmap = Bitmap.createBitmap(
                320,
                380,
                Bitmap.Config.RGB_565
            )
            val buffer = ByteBuffer.wrap(data)
            bitmap.copyPixelsFromBuffer(buffer)
        } else {
            bitmap = BitmapFactory.decodeResource(resources, com.chad.library.R.drawable.brvah_sample_footer_loading_progress)
        }
        customDialBg.setImageBitmap(bitmap)

    }

}

