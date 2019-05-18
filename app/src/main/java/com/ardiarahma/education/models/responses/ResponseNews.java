package com.ardiarahma.education.models.responses;

import com.ardiarahma.education.models.ArticlesResult;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Windows 10 on 2/21/2019.
 */

public class ResponseNews {

    private boolean error;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("result")
    @Expose
    private ArticlesResult result;

    public ResponseNews(boolean error, String status, ArticlesResult result) {
        this.error = error;
        this.status = status;
        this.result = result;
    }

    public boolean isError() {
        return error;
    }

    public String getStatus() {
        return status;
    }

    public ArticlesResult getResult() {
        return result;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setResult(ArticlesResult result) {
        this.result = result;
    }
}
