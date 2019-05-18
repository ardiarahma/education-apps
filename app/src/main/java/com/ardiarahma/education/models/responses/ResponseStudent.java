package com.ardiarahma.education.models.responses;

import com.ardiarahma.education.models.Students;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Windows 10 on 4/14/2019.
 */

public class ResponseStudent {
    private boolean error;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("result")
    @Expose
    public Students result;

    public ResponseStudent(boolean error, String status, Students result) {
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

    public Students getResult() {
        return result;
    }

    public void setResult(Students result) {
        this.result = result;
    }
}
