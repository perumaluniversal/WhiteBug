package com.dennismwebia.angar.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import com.dennismwebia.angar.R;
import com.dennismwebia.angar.activities.DashboardActivity;
import com.dennismwebia.angar.models.Light;
import com.dennismwebia.angar.utils.PreferenceHelper;
import com.dennismwebia.angar.views.TxtSemiBold;

/**
 * Created by dennis on 2/22/18.
 */

public class LightsListAdapter extends RecyclerView.Adapter<LightsListAdapter.ViewHolder> {
    private List<Light> lightList;
    private Activity context;
    private PreferenceHelper preferenceHelper;

    public LightsListAdapter(Activity context, List<Light> lightList) {
        this.context = context;
        this.lightList = lightList;
        this.preferenceHelper = new PreferenceHelper(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TxtSemiBold txtDeviceName;
        private SwitchCompat switchDevice;

        public ViewHolder(View v) {
            super(v);
            txtDeviceName = (TxtSemiBold) v.findViewById(R.id.txtDeviceName);
            switchDevice = (SwitchCompat) v.findViewById(R.id.switchDevice);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtDeviceName.setText(lightList.get(position).getTitle());

        //Check Here Light One and Off
        //holder.switchDevice.isChecked()
    }

    @Override
    public int getItemCount() {
        return lightList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_device_list_item, parent, false);
        return new ViewHolder(view);
    }

}
