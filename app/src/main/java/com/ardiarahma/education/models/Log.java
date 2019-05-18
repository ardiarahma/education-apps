package com.ardiarahma.education.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Windows 10 on 4/30/2019.
 */

public class Log {

    @SerializedName("menu")
    @Expose
    private Log_menu menu;

    @SerializedName("user")
    @Expose
    private User_siswa user;

    public Log(Log_menu menu, User_siswa user) {
        this.menu = menu;
        this.user = user;
    }

    public Log_menu getMenu() {
        return menu;
    }

    public void setMenu(Log_menu menu) {
        this.menu = menu;
    }

    public User_siswa getUser() {
        return user;
    }

    public void setUser(User_siswa user) {
        this.user = user;
    }
}
