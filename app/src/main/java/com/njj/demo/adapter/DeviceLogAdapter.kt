package com.njj.demo.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.njj.demo.BR
import com.njj.demo.R

import com.njj.demo.entity.LogInfo
import kotlinx.android.synthetic.main.layout_control_item.view.*
import kotlinx.android.synthetic.main.layout_log_item.view.*


/**
 * @ClassName DeviceLogAdapter
 * @Description TODO
 * @Author  Darcy
 * @Date 2022/3/2 17:10
 * @Version 1.0
 */
class DeviceLogAdapter(var data: MutableList<LogInfo>) : RecyclerView.Adapter<DeviceLogAdapter.DeviceLogViewHolder>(){

    lateinit var context: AppCompatActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceLogViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            R.layout.layout_log_item, parent, false
        )
        return DeviceLogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DeviceLogViewHolder, position: Int) {

        if (position == data.size-1){
            holder.binding.root.tv_content.setTextColor(Color.GREEN)
            holder.binding.root.tv_label.setTextColor(Color.GREEN)
        }else{
            holder.binding.root.tv_content.setTextColor(Color.WHITE)
            holder.binding.root.tv_label.setTextColor(Color.GRAY)
        }
        holder.binding.setVariable(BR.deviceAdapter,this)

        holder.binding.setVariable(BR.deviceInfo,data[position])
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int =data.size

    class DeviceLogViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)
}
