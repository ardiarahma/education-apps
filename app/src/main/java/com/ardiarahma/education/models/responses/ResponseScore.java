package com.ardiarahma.education.models.responses;

import com.ardiarahma.education.models.Score;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Windows 10 on 7/7/2019.
 */

public class ResponseScore {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("result")
    @Expose
    private Score result;

    public ResponseScore(String status, Score result) {
        this.status = status;
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Score getResult() {
        return result;
    }

    public void setResult(Score result) {
        this.result = result;
    }
}
