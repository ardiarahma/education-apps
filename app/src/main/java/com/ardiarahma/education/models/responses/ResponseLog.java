package com.ardiarahma.education.models.responses;

import com.ardiarahma.education.models.Log;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Windows 10 on 4/30/2019.
 */

public class ResponseLog {

    @SerializedName("error")
    @Expose
    private boolean error;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("result")
    @Expose
    private Log result;

    public ResponseLog(boolean error, String status, Log result) {
        this.error = error;
        this.status = status;
        this.result = result;
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

    public Log getResult() {
        return result;
    }

    public void setResult(Log result) {
        this.result = result;
    }
}
