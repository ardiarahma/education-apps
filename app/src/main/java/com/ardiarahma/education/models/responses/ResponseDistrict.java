package com.ardiarahma.education.models.responses;

import com.ardiarahma.education.models.District;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Windows 10 on 2/19/2019.
 */

public class ResponseDistrict {

    @SerializedName("error")
    @Expose
    private boolean error;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("result")
    private ArrayList<District> districts = null;

    public ResponseDistrict(boolean error, String status, ArrayList<District> districts) {
        this.error = error;
        this.status = status;
        this.districts = districts;
    }

    public boolean isError() {
        return error;
    }

    public String getStatus() {
        return status;
    }

    public ArrayList<District> getDistricts() {
        return districts;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDistricts(ArrayList<District> districts) {
        this.districts = districts;
    }
}
