package com.ardiarahma.education.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Windows 10 on 4/30/2019.
 */

public class Log_menu {

    @SerializedName("user_id")
    @Expose
    private int user_id;

    @SerializedName("fitur")
    @Expose
    private String fitur;

    @SerializedName("id")
    @Expose
    private int id;

    public Log_menu(int user_id, String fitur, int id) {
        this.user_id = user_id;
        this.fitur = fitur;
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getFitur() {
        return fitur;
    }

    public void setFitur(String fitur) {
        this.fitur = fitur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
