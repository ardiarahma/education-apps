package com.ardiarahma.education.models.responses;

import com.ardiarahma.educationapplication.models.Regency;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Windows 10 on 2/19/2019.
 */

public class ResponseRegency {

    @SerializedName("error")
    @Expose
    private boolean error;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("result")
    private ArrayList<Regency> regency = null;

    public ResponseRegency(boolean error, String status, ArrayList<Regency> regency) {
        this.error = error;
        this.status = status;
        this.regency = regency;
    }

    public boolean isError() {
        return error;
    }

    public String getStatus() {
        return status;
    }

    public ArrayList<Regency> getRegency() {
        return regency;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRegency(ArrayList<Regency> regency) {
        this.regency = regency;
    }
}
