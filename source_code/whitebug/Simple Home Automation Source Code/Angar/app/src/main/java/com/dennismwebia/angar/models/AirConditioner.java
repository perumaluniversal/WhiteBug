package com.dennismwebia.angar.models;

/**
 * Created by dennis on 2/22/18.
 */

public class AirConditioner {
    private int id;
    private String title;

    public AirConditioner(int id, String title){
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
