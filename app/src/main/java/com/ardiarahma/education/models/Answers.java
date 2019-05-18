package com.ardiarahma.education.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Windows 10 on 5/15/2019.
 */

public class Answers {

    @SerializedName("choice")
    @Expose
    private char choice;

    @SerializedName("choice_answer")
    @Expose
    private String answer;

    @SerializedName("is_answer")
    @Expose
    private boolean right_choice;

    public Answers (char choice, String answer, boolean right_choice) {
        this.choice = choice;
        this.answer = answer;
        this.right_choice = right_choice;
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

    public boolean isRight_choice() {
        return right_choice;
    }

    public void setRight_choice(boolean right_choice) {
        this.right_choice = right_choice;
    }
}
