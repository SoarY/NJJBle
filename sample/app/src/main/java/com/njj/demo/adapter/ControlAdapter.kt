package com.njj.demo.adapter

import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.njj.demo.R

import com.njj.demo.entity.ControlInfo

/**
 * 控制的适配器
 * @ClassName ControlAdapter
 * @Description TODO
 * @Author  Darcy
 * @Date 2022/3/1 18:02
 * @Version 1.0
 */
class ControlAdapter(data:MutableList<ControlInfo>?, var listener: OnSpinnerSelectListener) : BaseQuickAdapter<ControlInfo, BaseViewHolder>(
    R.layout.layout_control_item,
    data
) {


    override fun convert(helper: BaseViewHolder, controlInfo: ControlInfo?) {

        if (helper.adapterPosition == 3){
            helper.setVisible(R.id.spinner,true)
            helper.setVisible(R.id.tv_label,false)
        }else{
            helper.setVisible(R.id.spinner,false)
            helper.setVisible(R.id.tv_label,true)
        }
        helper.setText(R.id.tv_label,controlInfo!!.lable)
        val spinner = helper.getView<Spinner>(R.id.spinner)

        spinner.setSelection(1)
        spinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                listener.onSpinnerItemSelect(position,controlInfo.type)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

    }

    interface OnSpinnerSelectListener{

        fun onSpinnerItemSelect(pos:Int,cmdType:Int)
    }

}