package com.dennismwebia.angar.models;

/**
 * Created by dennis on 2/22/18.
 */

public class Light {
    private int id;
    private String title;
    private String code;

    public Light(int id, String title,String code){
        this.id = id;
        this.title = title;
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCode() { return code; }
}
