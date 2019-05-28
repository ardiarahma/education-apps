package com.ardiarahma.education.models.responses;

import com.ardiarahma.education.models.Answers;
import com.ardiarahma.education.models.BanksoalSoal;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Windows 10 on 5/23/2019.
 */

public class ResponseChoice {
    @SerializedName("error")
    @Expose
    private boolean error;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("result")
    @Expose
    private ArrayList<Answers> answers = null;

    public ResponseChoice(boolean error, String status, ArrayList<Answers> answers) {
        this.error = error;
        this.status = status;
        this.answers = answers;
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

    public ArrayList<Answers> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answers> answers) {
        this.answers = answers;
    }
}
