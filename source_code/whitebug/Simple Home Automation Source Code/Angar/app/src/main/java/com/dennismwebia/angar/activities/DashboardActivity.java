package com.dennismwebia.angar.activities;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import com.dennismwebia.angar.R;
import com.dennismwebia.angar.adapters.DeviceTypesListAdapter;
import com.dennismwebia.angar.data.Data;
import com.dennismwebia.angar.models.DeviceType;
import com.dennismwebia.angar.utils.CustomClickListener;
import com.dennismwebia.angar.utils.PreferenceHelper;
import com.dennismwebia.angar.utils.Utils;
import com.dennismwebia.angar.views.TxtLight;
import com.dennismwebia.angar.views.TxtSemiBold;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothSocket mmSocket;
    private BluetoothDevice mmDevice;
    public static OutputStream mmOutputStream;
    private InputStream mmInputStream;
    private TxtSemiBold txtTotalConnectedDevice, txtUsername, txtConnectionStatus;
    private TxtLight txtConnectBluetooth;
    private AVLoadingIndicatorView avLoadingIndicatorView;
    private PreferenceHelper preferenceHelper;
    private int bluetooth_state;
    private final static int REQUEST_ENABLE_BT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDashboard);
        txtTotalConnectedDevice = (TxtSemiBold) findViewById(R.id.txtTotalConnectedDevices);

        preferenceHelper = new PreferenceHelper(this);
        Utils utils = new Utils(this, this);
        utils.hideKeyPad();

        initToolbar(toolbar, "WHITEBUG");

        initDeviceTypeList();

        initUI();

        getExtraData();
    }

    private void initUI() {
        txtUsername = (TxtSemiBold) findViewById(R.id.txtUsername);
        txtConnectionStatus = (TxtSemiBold) findViewById(R.id.txtConnectionStatus);
        txtConnectBluetooth = (TxtLight) findViewById(R.id.txtConnectBluetooth);
        avLoadingIndicatorView = (AVLoadingIndicatorView) findViewById(R.id.avLoadingIndicator);

        txtConnectBluetooth.setOnClickListener(this);
    }

    private void centerToolbarTitle(@NonNull final Toolbar toolbar) {
        final CharSequence title = toolbar.getTitle();
        final ArrayList<View> outViews = new ArrayList<>(1);
        toolbar.findViewsWithText(outViews, title, View.FIND_VIEWS_WITH_TEXT);
        if (!outViews.isEmpty()) {
            final TextView titleView = (TextView) outViews.get(0);
            titleView.setGravity(Gravity.CENTER);
            titleView.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf"));
            final Toolbar.LayoutParams layoutParams = (Toolbar.LayoutParams) titleView.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            toolbar.requestLayout();
        }
    }

    private void initToolbar(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(Color.WHITE);
        centerToolbarTitle(toolbar);
        setSupportActionBar(toolbar);
    }

    private void initDeviceTypeList() {
        final Data data = new Data();
        txtTotalConnectedDevice.setText(String.valueOf(connectedDevicesTotal(data.deviceTypes())));
        DeviceTypesListAdapter deviceTypesListAdapter = new DeviceTypesListAdapter(this, data.deviceTypes(), new CustomClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (data.deviceTypes().get(position).getConnected_devices() != 0) {
                    Intent intentAction = new Intent(DashboardActivity.this, DeviceActionActivity.class);
                    intentAction.putExtra("device_group", data.deviceTypes().get(position).getType());
                    intentAction.putExtra("connected_devices", data.deviceTypes().get(position).getConnected_devices());
                    startActivity(intentAction);
                } else {
                    showSnackBar(data.deviceTypes().get(position).getType() + " have not been connected.");
                }
            }
        });
        RecyclerView listDeviceTypes = (RecyclerView) findViewById(R.id.listDeviceTypes);
        listDeviceTypes.setHasFixedSize(true);
        listDeviceTypes.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        listDeviceTypes.setAdapter(deviceTypesListAdapter);
    }

    public int connectedDevicesTotal(ArrayList<DeviceType> deviceTypes) {
        int sum = 0;
        for (DeviceType deviceType : deviceTypes) {
            sum += deviceType.getConnected_devices();
        }

        return sum;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
                preferenceHelper.putIsLoggedIn(false);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (preferenceHelper.getIsLoggedIn()) {
            return;
        } else {
            super.onBackPressed();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        txtUsername.setText(preferenceHelper.getLoggedInName());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtConnectBluetooth:
                //connectToBluetooth();
                break;
            default:
                break;
        }
    }

    /**
     * Show SnackBar
     */
    public void showSnackBar(final String text) {
        View container = findViewById(R.id.layoutMainView);
        Snackbar snackbar = Snackbar.make(container, text, Snackbar.LENGTH_LONG);
        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
        textView.setTextColor(getResources().getColor(R.color.colorWhite));
        textView.setTypeface(typeface);
        snackbar.show();
    }

    private void getExtraData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            if (bundle.containsKey("username")) {
                String username = bundle.getString("username");
                txtUsername.setText(username);
            } else {
                txtUsername.setText(preferenceHelper.getLoggedInName());
            }
        }

    }

    private void connectToBluetooth() {
        try {
            if (avLoadingIndicatorView.getVisibility() == View.GONE) {
                avLoadingIndicatorView.setVisibility(View.VISIBLE);
                txtConnectBluetooth.setVisibility(View.GONE);
                avLoadingIndicatorView.show();
            }

            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBluetooth, REQUEST_ENABLE_BT);
            }

            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
            if (pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {
                    if (device.getName().equals(preferenceHelper.getLoggedInUsername())) {
                        mmDevice = device;
                        break;
                    }
                }
            }

            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
            mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
            mmSocket.connect();
            mmOutputStream = mmSocket.getOutputStream();
            mmInputStream = mmSocket.getInputStream();

            bluetooth_state = 1;
            preferenceHelper.putBluetoothStatus(bluetooth_state);

            if (bluetooth_state == 1) {
                if (avLoadingIndicatorView.getVisibility() == View.VISIBLE) {
                    avLoadingIndicatorView.setVisibility(View.GONE);
                    txtConnectBluetooth.setVisibility(View.VISIBLE);
                    txtConnectionStatus.setText(getString(R.string.string_bluetooth_connected));
                    avLoadingIndicatorView.hide();
                }
            }

        } catch (Exception ex) {
            if (avLoadingIndicatorView.getVisibility() == View.VISIBLE) {
                avLoadingIndicatorView.setVisibility(View.GONE);
                txtConnectBluetooth.setVisibility(View.VISIBLE);
                txtConnectionStatus.setText(getString(R.string.string_bluetooth_disconnected));
                avLoadingIndicatorView.hide();
            }
            bluetooth_state = 0;
            preferenceHelper.putBluetoothStatus(bluetooth_state);
            ex.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
    }
}
