package com.ardiarahma.education.models.responses;

import com.ardiarahma.education.models.EbookShelves;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Windows 10 on 3/10/2019.
 */

public class ResponseEbook {
    private boolean error;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("result")
    @Expose
    private ArrayList<EbookShelves> ebookShelves = null;

    public ResponseEbook(boolean error, String status, ArrayList<EbookShelves> ebookShelves) {
        this.error = error;
        this.status = status;
        this.ebookShelves = ebookShelves;
    }

    public boolean isError() {
        return error;
    }

    public String getStatus() {
        return status;
    }

    public ArrayList<EbookShelves> getEbookShelves() {
        return ebookShelves;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setEbookShelves(ArrayList<EbookShelves> ebookShelves) {
        this.ebookShelves = ebookShelves;
    }
}
