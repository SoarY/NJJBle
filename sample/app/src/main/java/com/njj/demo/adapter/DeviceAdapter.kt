package com.njj.demo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding


import androidx.recyclerview.widget.RecyclerView
import com.njj.demo.BR
import com.njj.demo.R
import com.njj.njjsdk.protocol.entity.BLEDevice



/**
 * 显示设备的适配器
 * @ClassName DeviceAdapter
 * @Description TODO
 * @Date 2022/7/22 11:26
 * @Version 1.0
 */
class DeviceAdapter(var data: MutableList<BLEDevice>?, var itemClickListener: (potion:Int) -> Unit) : RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder>() {

    private lateinit var context: AppCompatActivity


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        context = parent.context as AppCompatActivity
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            R.layout.layout_device_item, parent, false
        )

        return DeviceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        holder.binding.setVariable(BR.deviceAdapter, this)
        holder.binding.setVariable(BR.deviceInfo, data!![position])
        holder.binding.executePendingBindings()
        holder.binding.root.setOnClickListener {
            itemClickListener(position)
        }
    }

    override fun getItemCount(): Int = data!!.size

    class DeviceViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)
}
