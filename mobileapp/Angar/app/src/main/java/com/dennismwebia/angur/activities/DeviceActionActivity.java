package com.dennismwebia.angar.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.dennismwebia.angar.R;
import com.dennismwebia.angar.adapters.AirConditionerListAdapter;
import com.dennismwebia.angar.adapters.LightsListAdapter;
import com.dennismwebia.angar.data.Data;
import com.dennismwebia.angar.utils.Utils;

public class DeviceActionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_action);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDeviceAction);

        Utils utils = new Utils(this, this);
        getExtraData(utils, toolbar);
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

    }
}
