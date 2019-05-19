package com.ardiarahma.education.models.responses;

import com.ardiarahma.education.models.LogStudy;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Windows 10 on 5/12/2019.
 */

public class ResponseLogStudyReport {

    @SerializedName("error")
    @Expose
    private boolean error;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("result")
    @Expose
    private ArrayList<LogStudy> logStudy = null;

    public ResponseLogStudyReport(boolean error, String status, ArrayList<LogStudy> logStudy) {
        this.error = error;
        this.status = status;
        this.logStudy = logStudy;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<LogStudy> getLogStudy() {
        return logStudy;
    }

    public void setLogStudy(ArrayList<LogStudy> logStudy) {
        this.logStudy = logStudy;
    }
}
