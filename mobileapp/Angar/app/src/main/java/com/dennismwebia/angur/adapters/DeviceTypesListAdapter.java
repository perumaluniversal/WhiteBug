package com.dennismwebia.angar.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import com.dennismwebia.angar.R;
import com.dennismwebia.angar.activities.DeviceActionActivity;
import com.dennismwebia.angar.models.DeviceType;
import com.dennismwebia.angar.utils.CustomClickListener;
import com.dennismwebia.angar.views.Txt;
import com.dennismwebia.angar.views.TxtSemiBold;

/**
 * Created by dennis on 2/22/18.
 */

public class DeviceTypesListAdapter extends RecyclerView.Adapter<DeviceTypesListAdapter.ViewHolder> {
    private List<DeviceType> deviceTypeList;
    private Context context;
    private String LABEL_DEVICES = " devices";
    private CustomClickListener clickListener;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TxtSemiBold txtDeviceType;
        private Txt txtConnectedDevices;
        private ImageView iconDeviceType;
        private CardView cardView;

        public ViewHolder(View v) {
            super(v);
            txtDeviceType = (TxtSemiBold) v.findViewById(R.id.txtDeviceType);
            txtConnectedDevices = (Txt) v.findViewById(R.id.txtConnectedDevices);
            iconDeviceType = (ImageView) v.findViewById(R.id.iconDeviceType);
            cardView = (CardView) v.findViewById(R.id.layoutDeviceGroups);
        }
    }

    public DeviceTypesListAdapter(Context context, List<DeviceType> deviceTypeList, CustomClickListener clickListener) {
        this.context = context;
        this.deviceTypeList = deviceTypeList;
        this.clickListener = clickListener;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.txtDeviceType.setText(deviceTypeList.get(position).getType());
        holder.txtConnectedDevices.setText(deviceTypeList.get(position).getConnected_devices() + LABEL_DEVICES);
        Glide.with(context).load(deviceTypeList.get(position).getIcon()).into(holder.iconDeviceType);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(v, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return deviceTypeList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_connected_devices_list_item, parent, false);
        return new ViewHolder(view);
    }

}
