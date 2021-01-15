package com.dennismwebia.angar.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import com.dennismwebia.angar.R;
import com.dennismwebia.angar.models.AirConditioner;
import com.dennismwebia.angar.views.TxtSemiBold;

/**
 * Created by dennis on 2/22/18.
 */

public class AirConditionerListAdapter  extends RecyclerView.Adapter<AirConditionerListAdapter.ViewHolder> {
    private List<AirConditioner> airConditioners;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TxtSemiBold txtDeviceName;

        public ViewHolder(View v) {
            super(v);
            txtDeviceName = (TxtSemiBold) v.findViewById(R.id.txtDeviceName);
        }
    }

    public AirConditionerListAdapter(Context context, List<AirConditioner> airConditioners) {
        this.context = context;
        this.airConditioners = airConditioners;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtDeviceName.setText(airConditioners.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return airConditioners.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_device_list_item, parent, false);
        return new ViewHolder(view);
    }

}
