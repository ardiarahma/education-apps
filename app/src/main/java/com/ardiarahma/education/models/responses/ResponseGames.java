package com.ardiarahma.education.models.responses;

import com.ardiarahma.education.models.EbookShelves;
import com.ardiarahma.education.models.Games;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Windows 10 on 6/26/2019.
 */

public class ResponseGames {

    private boolean error;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("result")
    @Expose
    private ArrayList<Games> games = null;

    public ResponseGames(boolean error, String status, ArrayList<Games> games) {
        this.error = error;
        this.status = status;
        this.games = games;
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

    public ArrayList<Games> getGames() {
        return games;
    }

    public void setGames(ArrayList<Games> games) {
        this.games = games;
    }
}
