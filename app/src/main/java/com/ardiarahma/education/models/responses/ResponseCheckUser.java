package com.ardiarahma.education.models.responses;

import com.ardiarahma.education.models.Siswa;
import com.ardiarahma.education.models.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Windows 10 on 5/18/2019.
 */

public class ResponseCheckUser {

    private boolean error;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("result")
    @Expose
    private User user;

    @SerializedName("anak")
    @Expose
    private ArrayList<Siswa> anak = null;

    public ResponseCheckUser(boolean error, String status, User user, ArrayList<Siswa> anak) {
        this.error = error;
        this.status = status;
        this.user = user;
        this.anak = anak;
    }

    public boolean isError() {
        return error;
    }


    public String getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }

    public ArrayList<Siswa> getAnak() {
        return anak;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAnak(ArrayList<Siswa> anak) {
        this.anak = anak;
    }
}
