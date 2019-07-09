package com.ardiarahma.education.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Windows 10 on 7/7/2019.
 */

public class Score {
    @SerializedName("student_id")
    @Expose
    private int student_id;

    @SerializedName("taskmaster_id")
    @Expose
    private int task_id;

    @SerializedName("score")
    @Expose
    private int score;

    public Score(int student_id, int task_id, int score) {
        this.student_id = student_id;
        this.task_id = task_id;
        this.score = score;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
