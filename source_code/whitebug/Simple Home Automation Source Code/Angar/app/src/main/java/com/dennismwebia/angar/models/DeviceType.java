package com.dennismwebia.angar.models;

/**
 * Created by dennis on 2/22/18.
 */

public class DeviceType {
    private int id;
    private int icon;
    private String type;
    private int connected_devices;

    public DeviceType(int id, int icon, String type, int connected_devices){
        this.id = id;
        this.icon = icon;
        this.type = type;
        this.connected_devices = connected_devices;
    }

    public int getId() {
        return id;
    }

    public int getIcon() {
        return icon;
    }

    public String getType() {
        return type;
    }

    public int getConnected_devices() {
        return connected_devices;
    }
}
