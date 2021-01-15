package com.dennismwebia.angar.data;

import java.util.ArrayList;

import com.dennismwebia.angar.R;
import com.dennismwebia.angar.models.AirConditioner;
import com.dennismwebia.angar.models.DeviceType;
import com.dennismwebia.angar.models.Light;

/**
 * Created by dennis on 2/22/18.
 */

public class Data {

    public Data() {

    }

    public ArrayList<DeviceType> deviceTypes() {
        ArrayList<DeviceType> deviceTypes = new ArrayList<>();
        deviceTypes.add(new DeviceType(1, R.drawable.icon_light, "Lights", 8));
        //deviceTypes.add(new DeviceType(2, R.drawable.icon_airconditioner, "Air Conditioners", 1));
        //deviceTypes.add(new DeviceType(3, R.drawable.icon_door_lock, "Door Locks", 0));

        return deviceTypes;
    }

    public ArrayList<Light> lights(){
        ArrayList<Light> lights = new ArrayList<>();
        lights.add(new Light(6, "Light One"));
        lights.add(new Light(7, "Light Two"));
        lights.add(new Light(8, "Light Three"));
        lights.add(new Light(9, "Light Four"));
        lights.add(new Light(10, "Light Five"));
        lights.add(new Light(11, "Light Six"));
        lights.add(new Light(12, "Light Seven"));
        lights.add(new Light(13, "EB Main Switch"));

        return lights;
    }

    public ArrayList<AirConditioner> airConditioners(){
        ArrayList<AirConditioner> airConditioners = new ArrayList<>();
        airConditioners.add(new AirConditioner(1, "Master Bedroom"));

        return airConditioners;
    }

}
