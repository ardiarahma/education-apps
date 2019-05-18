package com.ardiarahma.education.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Windows 10 on 2/19/2019.
 */

public class Regency {

    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    public Regency(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String toString(){
        return this.name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
