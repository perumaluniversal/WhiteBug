package com.dennismwebia.angar.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.dennismwebia.angar.R;
import com.dennismwebia.angar.adapters.AirConditionerListAdapter;
import com.dennismwebia.angar.adapters.LightsListAdapter;
import com.dennismwebia.angar.data.Data;
import com.dennismwebia.angar.utils.Common;
import com.dennismwebia.angar.utils.Utils;
import com.dennismwebia.angar.views.TxtSemiBold;

import org.json.JSONObject;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class DeviceActionActivity extends AppCompatActivity {

    public static StatusAsyncTask statusthread;
    public TxtSemiBold txtbatteryvoltage;
    public TxtSemiBold txttemperature;
    public TxtSemiBold txtrunning;
    public com.dennismwebia.angar.views.Btn btnrefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_action);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDeviceAction);

        Utils utils = new Utils(this, this);
        getExtraData(utils, toolbar);

        txtbatteryvoltage = findViewById(R.id.txtbatteryvoltage);
        txttemperature = findViewById(R.id.txttemperature);
        txtrunning = findViewById(R.id.txtrunning);
        btnrefresh = findViewById(R.id.btnrefresh);

        btnrefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Refresh();
            }
        });


    }



    private void getExtraData(Utils utils, Toolbar toolbar) {
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            Bundle bundle = intent.getExtras();
            if (bundle.containsKey("device_group")) {
                String device_group = bundle.getString("device_group");
                utils.initToolbar(toolbar, device_group, DashboardActivity.class);
                initDeviceList(device_group);
            }
        }
    }

    private void initDeviceList(String deviceGroup) {
        Data data = new Data();
        RecyclerView listDevices = (RecyclerView) findViewById(R.id.listDevices);
        listDevices.setHasFixedSize(true);
        listDevices.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        if (deviceGroup.contains("Lights")) {
            LightsListAdapter lightsListAdapter = new LightsListAdapter(this, data.lights());
            listDevices.setAdapter(lightsListAdapter);
        } else if (deviceGroup.contains("Air Condition")) {
            AirConditionerListAdapter airConditionerListAdapter = new AirConditionerListAdapter(this, data.airConditioners());
            listDevices.setAdapter(airConditionerListAdapter);
        }

        Refresh();
    }

    private void Refresh()
    {
        Common.progress = ProgressDialog.show(this, "White Bug", "Getting Status.. Please wait");
        DeviceActionActivity.statusthread = new StatusAsyncTask();
        DeviceActionActivity.statusthread.execute("");
    }


    private class StatusAsyncTask extends AsyncTask<String,Void,String>
    {
        @Override
        protected String doInBackground(String... urls) {

            try
            {
                return Common.GetControlStatus();
            }
            catch (Exception ee)
            {
                Log.d("StatusAsyncTask" , ee.getMessage());
            }
            return "";
        }

        @Override
        protected void onPostExecute(String result) {

            //call printer code
            try
            {
                //14.176923076923078||39.7'C(thersold)||SOLAR
                Common.progress.dismiss();
                String[] resarray = result.split("\\|\\|");
                DecimalFormat df = new DecimalFormat("#.##");
                df.setRoundingMode(RoundingMode.CEILING);
                txtbatteryvoltage.setText(df.format(Double.parseDouble(resarray[0])) + " Volt");
                txttemperature.setText(resarray[1]);
                txtrunning.setText(resarray[2]);

                //switch state
                RecyclerView listDevices = (RecyclerView) findViewById(R.id.listDevices);
                int counter = listDevices.getChildCount();
                JSONObject jdata = new JSONObject(resarray[3]);
                int i;
                for(i = 0; i<counter; i++)
                {
                    String code = ((TextView)listDevices.getChildAt(i).findViewById(R.id.dataid)).getText().toString();
                    String sdata = jdata.getString(code);

                    if(sdata!=null)
                    {
                        if(sdata.equals("1"))
                            ((SwitchCompat)listDevices.getChildAt(i).findViewById(R.id.switchDevice)).setChecked(true);
                        else
                            ((SwitchCompat)listDevices.getChildAt(i).findViewById(R.id.switchDevice)).setChecked(false);
                    }
                }
            }
            catch (Exception ee)
            {
                Log.d("Error Print call:", ee.getMessage());
            }
        }
    }



}
