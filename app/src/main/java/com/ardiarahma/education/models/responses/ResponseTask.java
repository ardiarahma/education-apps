package com.ardiarahma.education.models.responses;

import com.ardiarahma.education.models.BanksoalSoal;
import com.ardiarahma.education.models.BanksoalShelves;
import com.ardiarahma.education.models.Task;
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
    private ArrayList<Task> tasks = null;

    public ResponseTask(boolean error, String status, ArrayList<Task> tasks) {
        this.error = error;
        this.status = status;
        this.tasks = tasks;
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

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }
}
