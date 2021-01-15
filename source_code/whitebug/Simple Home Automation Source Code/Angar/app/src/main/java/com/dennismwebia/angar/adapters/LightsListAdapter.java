package com.dennismwebia.angar.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.app.ProgressDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import com.dennismwebia.angar.R;
import com.dennismwebia.angar.activities.DashboardActivity;
import com.dennismwebia.angar.models.Light;
import com.dennismwebia.angar.utils.Common;
import com.dennismwebia.angar.utils.PreferenceHelper;
import com.dennismwebia.angar.views.TxtSemiBold;

/**
 * Created by dennis on 2/22/18.
 */

public class LightsListAdapter extends RecyclerView.Adapter<LightsListAdapter.ViewHolder> {
    private List<Light> lightList;
    private Activity context;
    private PreferenceHelper preferenceHelper;

    public static Boolean isTouched = false;


    public LightsListAdapter(Activity context, List<Light> lightList) {
        this.context = context;
        this.lightList = lightList;
        this.preferenceHelper = new PreferenceHelper(context);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TxtSemiBold txtDeviceName;
        private SwitchCompat switchDevice;
        private TextView dataid;




        @SuppressLint("ClickableViewAccessibility")
        public ViewHolder(View v) {
            super(v);

            txtDeviceName = (TxtSemiBold) v.findViewById(R.id.txtDeviceName);
            switchDevice = (SwitchCompat) v.findViewById(R.id.switchDevice);
            dataid = (TextView)v.findViewById(R.id.dataid);





        }

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.txtDeviceName.setText(lightList.get(position).getTitle());
        holder.dataid.setText(lightList.get(position).getCode());

        if (holder.switchDevice.isChecked()) {
            if (preferenceHelper.getBluetoothStatus() == 1) {
                try {
                    DashboardActivity.mmOutputStream.write('1');
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(context, "Connection not established with your home", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            } else
                Toast.makeText(context, "Connection not established with your home", Toast.LENGTH_LONG).show();
        } else {
            if (preferenceHelper.getBluetoothStatus() == 1) {
                try {
                    DashboardActivity.mmOutputStream.write('2');
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(context, "Connection not established with your home", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            } else
                Toast.makeText(context, "Connection not established with your home", Toast.LENGTH_LONG).show();
        }

        holder.switchDevice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if (isTouched) {
                    isTouched = false;

                    Common.progress = ProgressDialog.show(context, "White Bug", "Switching.. Please wait");
                    new APIAsyncTask().execute(lightList.get(holder.getAdapterPosition()).getCode() + (isChecked ? "1" : 0));
                }
            }
        });

        holder.switchDevice.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                isTouched = true;
                return false;
            }
        });

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


    private class APIAsyncTask extends AsyncTask<String,Void,String>
    {
        @Override
        protected String doInBackground(String... urls) {

            try
            {
                return Common.SendCommand(urls[0]);
            }
            catch (Exception ee)
            {
                Log.d("APIAsyncTask Error",ee.getMessage());
            }
            return "";
        }

        @Override
        protected void onPostExecute(String result) {

            //call printer code
            try
            {
                Common.progress.dismiss();
            }
            catch (Exception ee)
            {
                Log.d("Error Print call:", ee.getMessage());
                Common.progress.dismiss();
            }

        }
    }


}
