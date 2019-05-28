package com.ardiarahma.education.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Windows 10 on 5/15/2019.
 */

public class Answers {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("choice")
    @Expose
    private char choice;

    @SerializedName("choice_answer")
    @Expose
    private String answer;

    @SerializedName("is_answer")
    @Expose
    private int right_choice;

    public Answers (int id, char choice, String answer, int right_choice) {
        this.id = id;
        this.choice = choice;
        this.answer = answer;
        this.right_choice = right_choice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public char getChoice() {
        return choice;
    }

    public void setChoice(char choice) {
        this.choice = choice;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getRight_choice() {
        return right_choice;
    }

    public void setRight_choice(int right_choice) {
        this.right_choice = right_choice;
    }

}
