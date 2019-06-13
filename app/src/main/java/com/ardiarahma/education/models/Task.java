package com.ardiarahma.education.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Windows 10 on 6/11/2019.
 */

public class Task {
    @SerializedName("id")
    @Expose
    private int id_soal;

    @SerializedName("description")
    @Expose
    private String soal;

    @SerializedName("A")
    @Expose
    private String option_A;

    @SerializedName("B")
    @Expose
    private String option_B;

    @SerializedName("C")
    @Expose
    private String option_C;

    @SerializedName("D")
    @Expose
    private String option_D;

    @SerializedName("Answer")
    @Expose
    private String jawaban;

    @SerializedName("discussion")
    @Expose
    private String pembahasan;

    public Task(int id_soal, String soal, String option_A, String option_B, String option_C, String option_D, String jawaban, String pembahasan) {
        this.id_soal = id_soal;
        this.soal = soal;
        this.option_A = option_A;
        this.option_B = option_B;
        this.option_C = option_C;
        this.option_D = option_D;
        this.jawaban = jawaban;
        this.pembahasan = pembahasan;
    }

    public int getId_soal() {
        return id_soal;
    }

    public void setId_soal(int id_soal) {
        this.id_soal = id_soal;
    }

    public String getSoal() {
        return soal;
    }

    public void setSoal(String soal) {
        this.soal = soal;
    }

    public String getOption_A() {
        return option_A;
    }

    public void setOption_A(String option_A) {
        this.option_A = option_A;
    }

    public String getOption_B() {
        return option_B;
    }

    public void setOption_B(String option_B) {
        this.option_B = option_B;
    }

    public String getOption_C() {
        return option_C;
    }

    public void setOption_C(String option_C) {
        this.option_C = option_C;
    }

    public String getOption_D() {
        return option_D;
    }

    public void setOption_D(String option_D) {
        this.option_D = option_D;
    }

    public String getJawaban() {
        return jawaban;
    }

    public void setJawaban(String jawaban) {
        this.jawaban = jawaban;
    }

    public String getPembahasan() {
        return pembahasan;
    }

    public void setPembahasan(String pembahasan) {
        this.pembahasan = pembahasan;
    }
}
