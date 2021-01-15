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
        deviceTypes.add(new DeviceType(1, R.drawable.icon_light, "Lights", 6));
        //deviceTypes.add(new DeviceType(2, R.drawable.icon_airconditioner, "Air Conditioners", 1));
        //deviceTypes.add(new DeviceType(3, R.drawable.icon_door_lock, "Door Locks", 0));

        return deviceTypes;
    }

    public ArrayList<Light> lights(){
        //Working Switch code = 13,11,09,08,06,05,04,03
        ArrayList<Light> lights = new ArrayList<>();
        lights.add(new Light(1, "Home1 Fordigo Light","D03"));
        lights.add(new Light(2, "Home2 Fordigo Light","D04"));
        lights.add(new Light(3, "Home1 Fordigo Back Light","D05"));
        lights.add(new Light(3, "Home1 Light 1","D06"));
        lights.add(new Light(3, "Home1 Light 2","D08"));
        lights.add(new Light(3, "Home1 Light 3","D09"));

        lights.add(new Light(3, "EB Line","D11"));//working line
        lights.add(new Light(3, "Cooler Fan","D13"));

        // 11 and 13 reserved for main supply

        return lights;
    }

    public ArrayList<AirConditioner> airConditioners(){
        ArrayList<AirConditioner> airConditioners = new ArrayList<>();
        airConditioners.add(new AirConditioner(1, "Master Bedroom"));

        return airConditioners;
    }

}
