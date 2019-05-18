package com.ardiarahma.education.models.responses;

import com.ardiarahma.education.models.Log_detail;
import com.ardiarahma.education.models.Siswa;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Windows 10 on 5/12/2019.
 */

public class ResponseLogActivityReport {

    @SerializedName("error")
    @Expose
    private boolean error;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("anak")
    @Expose
    private Siswa anak;

    @SerializedName("log")
    @Expose
    private ArrayList <Log_detail> log_detail = null;

    public ResponseLogActivityReport(boolean error, String status, Siswa anak, ArrayList<Log_detail> log_detail) {
        this.error = error;
        this.status = status;
        this.anak = anak;
        this.log_detail = log_detail;
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

    public Siswa getAnak() {
        return anak;
    }

    public void setAnak(Siswa anak) {
        this.anak = anak;
    }

    public ArrayList<Log_detail> getLog_detail() {
        return log_detail;
    }

    public void setLog_detail(ArrayList<Log_detail> log_detail) {
        this.log_detail = log_detail;
    }
}
