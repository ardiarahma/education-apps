package com.ardiarahma.education.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Windows 10 on 1/16/2019.
 */

public class BanksoalShelves {

    @SerializedName("id")
    @Expose
    private int task_id;

    @SerializedName("title")
    @Expose
    private String name;

    @SerializedName("class")
    @Expose
    private int classes;

    public BanksoalShelves(int task_id, String name, int classes) {
        this.task_id = task_id;
        this.name = name;
        this.classes = classes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public int getClasses() {
        return classes;
    }

    public void setClasses(int classes) {
        this.classes = classes;
    }
}
