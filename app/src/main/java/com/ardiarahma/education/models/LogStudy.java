package com.ardiarahma.education.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Windows 10 on 5/15/2019.
 */

public class LogStudy {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("score")
    @Expose
    private String score;

    @SerializedName("title")
    @Expose
    private String title;

    public LogStudy(int id, String score, String title) {
        this.id = id;
        this.score = score;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
