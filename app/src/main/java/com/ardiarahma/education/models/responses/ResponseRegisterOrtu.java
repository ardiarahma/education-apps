package com.ardiarahma.education.models.responses;

/**
 * Created by Windows 10 on 2/9/2019.
 */

public class ResponseRegisterOrtu {
    private String status;
    private boolean error;

    public String getStatus() {
        return status;
    }

    public ResponseRegisterOrtu(String status, boolean error) {

        this.status = status;
        this.error = error;
    }

    public boolean isError() {
        return error;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
