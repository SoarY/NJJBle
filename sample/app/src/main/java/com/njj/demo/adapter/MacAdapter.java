package com.njj.demo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.njj.demo.R;
import com.njj.njjsdk.protocol.entity.BLEDevice;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @ClassName MacAdapter
 * @Description TODO
 * @Author LibinFan
 * @Date 2022/10/9 17:31
 * @Version 1.0
 */
public class MacAdapter  extends RecyclerView.Adapter<MacAdapter.Holder>{


    public MacAdapter(List<BLEDevice> data) {
        this.data = data;
    }

    private List<BLEDevice> data;
    @NonNull
    @NotNull
    @Override
    public Holder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mac, parent, false);
        // 这里将view和holder的绑定方式与ListViewAdapter不同，不再是使用Tag的方式了，而是将view作为holder的成员变量
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Holder holder, int position) {
        BLEDevice bleDevice = data.get(position);
        holder.tv.setText(bleDevice.getDevice().getAddress());
    }



    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class Holder extends RecyclerView.ViewHolder {


        TextView tv;
        public Holder(@NonNull View itemView) {
            super(itemView);
            tv=itemView.findViewById(R.id.tv_name);
        }
    }
}
