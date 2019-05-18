package com.ardiarahma.education.models.responses;

import com.ardiarahma.education.models.Province;
import com.ardiarahma.educationapplication.models.Province;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Windows 10 on 2/19/2019.
 */

public class ResponseProvince {

    @SerializedName("error")
    @Expose
    private boolean error;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("result")
    private ArrayList<Province> province = null;

    public ResponseProvince(boolean error, String status, ArrayList<Province> province) {
        this.error = error;
        this.status = status;
        this.province = province;
    }


    public boolean isError() {
        return error;
    }

    public String getStatus() {
        return status;
    }

    public ArrayList<Province> getProvince() {
        return province;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setProvince(ArrayList<Province> province) {
        this.province = province;
    }
}
