package com.ardiarahma.education.models.responses;

import com.ardiarahma.education.models.BanksoalSoal;
import com.ardiarahma.education.models.BanksoalShelves;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Windows 10 on 5/11/2019.
 */

public class ResponseTask {

    @SerializedName("error")
    @Expose
    private boolean error;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("result")
    @Expose
    private BanksoalShelves banksoalShelves;

    @SerializedName("soal")
    @Expose
    private ArrayList<BanksoalSoal> banksoalSoals = null;

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

    public BanksoalShelves getBanksoalShelves() {
        return banksoalShelves;
    }

    public void setBanksoalShelves(BanksoalShelves banksoalShelves) {
        this.banksoalShelves = banksoalShelves;
    }

    public ArrayList<BanksoalSoal> getBanksoalSoals() {
        return banksoalSoals;
    }

    public void setBanksoalSoals(ArrayList<BanksoalSoal> banksoalSoals) {
        this.banksoalSoals = banksoalSoals;
    }

    public ResponseTask(String status) {

        this.status = status;
    }
}
