package com.njj.demo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.njj.demo.adapter.ControlAdapter
import com.njj.demo.adapter.DeviceLogAdapter
import com.njj.demo.adapter.GridItemDecoration
import com.njj.demo.databinding.ActivityDeviceBinding
import com.njj.demo.entity.LogInfo
import com.njj.demo.utils.ControlDataImpl
import com.njj.demo.utils.DeviceControlHelper
import com.njj.njjsdk.callback.CallBackManager
import com.njj.njjsdk.callback.NjjNotifyCallback
import com.njj.njjsdk.callback.SomatosensoryGameCallback
import com.njj.njjsdk.manger.NjjProtocolHelper
import com.njj.njjsdk.protocol.entity.NjjStepData
import com.njj.njjsdk.protocol.entity.SomatosensoryGame
import com.njj.njjsdk.utils.LogUtil
import kotlinx.android.synthetic.main.activity_device.*
import kotlinx.android.synthetic.main.activity_device.view.*

class DeviceActivity : AppCompatActivity() {
    lateinit var adapter: ControlAdapter
    lateinit var logAdapter: DeviceLogAdapter
    lateinit var logData: MutableList<LogInfo>
    lateinit var helper: DeviceControlHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        helper = DeviceControlHelper()
        DataBindingUtil.setContentView<ActivityDeviceBinding>(
            this,
            R.layout.activity_device
        )

        rv_control.layoutManager = GridLayoutManager(this, 5)
        adapter = ControlAdapter(null, listener)
        rv_control.adapter = adapter
        rv_control.addItemDecoration(GridItemDecoration(1))
        CallBackManager.getInstance().registerSomatosensoryGameCallback(object :SomatosensoryGameCallback.ICallBack{
            override fun onReceiveData(somatosensoryGame: SomatosensoryGame?) {
                LogUtil.e(somatosensoryGame.toString())
            }

            override fun onReceiveStatus(status: Int) {
                TODO("Not yet implemented")
            }

        })

        var somatosensoryGameCallback = CallBackManager.getInstance().somatosensoryGameCallback
        tv_dial.setOnClickListener {
            var intent=Intent(this@DeviceActivity,TestDialActivity::class.java)
            startActivity(intent)
        }

        tv_ota.setOnClickListener {
            var intent=Intent(this@DeviceActivity,OtaActivity::class.java)
            startActivity(intent)
//            NjjProtocolHelper.getInstance().setMotionGameStatus(0)

        }

        tv_cum_dial.setOnClickListener {
            var intent=Intent(this@DeviceActivity,DialCustumActivity::class.java)
            startActivity(intent)
        }

        tv_jieli_ota.setOnClickListener {
            var intent=Intent(this@DeviceActivity,JieLiOTAActivity::class.java)
            startActivity(intent)
        }


        adapter.setNewData(ControlDataImpl.getControlData())
        adapter.onItemClickListener =
            BaseQuickAdapter.OnItemClickListener { _, _, p2 ->

                helper.userClickItem(ControlDataImpl.getControlData()[p2], p2,text)
            }

        logData = ArrayList()
        logAdapter = DeviceLogAdapter(logData)


        NjjProtocolHelper.getInstance().registerSingleHeartOxBloodCallback(
            object : NjjNotifyCallback {
                override fun onBloodPressureData(systolicPressure: Int, diastolicPressure: Int) {
                    LogUtil.e("单次血压$systolicPressure=$diastolicPressure")
                    text.text="单次血压$systolicPressure=$diastolicPressure"
                }

                override fun onHeartRateData(rate: Int) {
                    LogUtil.e("单次心率$rate")
                    text.text="单次心率$rate"
                }

                override fun onOxyData(rate: Int) {
                    LogUtil.e("单次血氧$rate")

                    text.text="单次心率$rate"
                }

                override fun takePhone(value: Int) {
                    LogUtil.e("拍照$value")

                    text.text="拍照$value"
                }

                override fun onStepData(njjStepData: NjjStepData?) {
                    text.text="卡路里${njjStepData?.calData} 步数${njjStepData?.stepNum}  距离${njjStepData?.distance}"
                }

                override fun findPhone(value: Int) {
                    LogUtil.e("查找手机$value")
                    text.text="查找手机$value"
                }

                override fun endCallPhone(value: Int) {
                    if(value==0){
                        text.text="挂断电话"
                    }
                }
            })

    }

    private val listener: ControlAdapter.OnSpinnerSelectListener =
        object : ControlAdapter.OnSpinnerSelectListener {
            override fun onSpinnerItemSelect(pos: Int, cmdType: Int) {
                LogUtil.e("!!!!!!!!!!!!!!!!!!!!")
//                NjjProtocolHelper.getInstance().setDeviceLanguage(pos)


            }

        }
}