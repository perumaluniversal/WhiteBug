package com.dennismwebia.angar.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by dennis on 11/10/17.
 */

public class PreferenceHelper {
    private Context context = null;
    private SharedPreferences app_prefs;

    public PreferenceHelper(Context context) {
        this.context = context;
        this.app_prefs = context.getSharedPreferences("Angar", Context.MODE_PRIVATE);
    }

    public void putIsLoggedIn(boolean logged_in) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putBoolean("logged_in", logged_in);
        edit.apply();
    }

    public void putLoggedInName(String name) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString("name", name);
        edit.apply();
    }

    public String getLoggedInName() {
        return app_prefs.getString("name", "");

    }

    public void putLoggedInUsername(String username) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString("username", username);
        edit.apply();
    }

    public String getLoggedInUsername() {
        return app_prefs.getString("username", "");

    }

    public boolean getIsLoggedIn() {
        return app_prefs.getBoolean("logged_in", false);
    }

    public void putBluetoothStatus(int bluetooth_status) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putInt("bluetooth_status", bluetooth_status);
        edit.apply();
    }

    public int getBluetoothStatus() {
        return app_prefs.getInt("bluetooth_status", 0);
    }

}
