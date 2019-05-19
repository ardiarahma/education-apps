package com.ardiarahma.education.models.responses;

import com.ardiarahma.education.models.EbookSubject;

/**
 * Created by Windows 10 on 3/9/2019.
 */

public class ResponseSubjects {

    private boolean error;
    private String status;
    private EbookSubject result;

    public ResponseSubjects(boolean error, String status, EbookSubject result) {
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

    public EbookSubject getResult() {
        return result;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setResult(EbookSubject result) {
        this.result = result;
    }
}
