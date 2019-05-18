package com.ardiarahma.education.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Windows 10 on 1/29/2019.
 */

public class BanksoalSoal {

    @SerializedName("id")
    @Expose
    private int id_soal;

    @SerializedName("image")
    @Expose
    private int image;

    @SerializedName("description")
    @Expose
    private String soal;

    @SerializedName("discussion")
    @Expose
    private String pembahasan;

    @SerializedName("answers")
    @Expose
    private ArrayList<Answers> answers = null;

    public BanksoalSoal(int id_soal, int image, String soal, String pembahasan, ArrayList<Answers> answers) {
        this.id_soal = id_soal;
        this.image = image;
        this.soal = soal;
        this.pembahasan = pembahasan;
        this.answers = answers;
    }

    public int getId_soal() {
        return id_soal;
    }

    public void setId_soal(int id_soal) {
        this.id_soal = id_soal;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getSoal() {
        return soal;
    }

    public void setSoal(String soal) {
        this.soal = soal;
    }

    public String getPembahasan() {
        return pembahasan;
    }

    public void setPembahasan(String pembahasan) {
        this.pembahasan = pembahasan;
    }

    public ArrayList<Answers> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answers> answers) {
        this.answers = answers;
    }
}
