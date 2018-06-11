package com.karman.bluetoothcrane.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.karman.bluetoothcrane.R;
import com.karman.bluetoothcrane.model.Device;
import com.karman.bluetoothcrane.utils.Utils;

import java.util.ArrayList;
import java.util.List;


public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder> {
    private OnItemClickListener mItemClickListener;
    private Activity activity;
    private List<Device> deviceList = new ArrayList<> ();

    public DeviceAdapter(Activity activity, List<Device> deviceList) {
        this.activity = activity;
        this.deviceList = deviceList;
    }

    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        final LayoutInflater mInflater = LayoutInflater.from (parent.getContext ());
        final View sView = mInflater.inflate (R.layout.list_item_device, parent, false);
        return new ViewHolder (sView);
    }

    @Override
    public void onBindViewHolder (final ViewHolder holder, int position) {//        runEnterAnimation (holder.itemView);
        final Device device= deviceList.get (position);
        Utils.setTypefaceToAllViews (activity, holder.tvName);
        holder.tvName.setText (device.getName ());
        holder.tvAddress.setText (device.getAddress());
        holder.tvType.setText (device.getType());
    }

    @Override
    public int getItemCount () {
        return deviceList.size ();
    }

    public void SetOnItemClickListener (final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }
    
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName;
        TextView tvAddress;
        TextView tvType;

        public ViewHolder (View view) {
            super (view);
            tvName = (TextView) view.findViewById (R.id.tvName);
            tvAddress = (TextView) view.findViewById (R.id.tvAddress);
            tvType = (TextView) view.findViewById (R.id.tvType);
            view.setOnClickListener (this);
        }
        
        @Override
        public void onClick (View v) {
            mItemClickListener.onItemClick (v, getLayoutPosition ());
        }
    }
}