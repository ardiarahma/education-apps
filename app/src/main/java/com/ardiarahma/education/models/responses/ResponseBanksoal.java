package com.ardiarahma.education.models.responses;

import com.ardiarahma.education.models.BanksoalShelves;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Windows 10 on 5/18/2019.
 */

public class ResponseBanksoal {
    private boolean error;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("result")
    @Expose
    private ArrayList<BanksoalShelves> banksoalShelves = null;

    public ResponseBanksoal(boolean error, String status, ArrayList<BanksoalShelves> banksoalShelves) {
        this.error = error;
        this.status = status;
        this.banksoalShelves = banksoalShelves;
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

    public ArrayList<BanksoalShelves> getBanksoalShelves() {
        return banksoalShelves;
    }

    public void setBanksoalShelves(ArrayList<BanksoalShelves> banksoalShelves) {
        this.banksoalShelves = banksoalShelves;
    }
}
