package com.ardiarahma.education.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Windows 10 on 5/12/2019.
 */

public class Log_detail {

    @SerializedName("id")
    @Expose
    private int id_log;

    @SerializedName("user_id")
    @Expose
    private int id_anak;

    @SerializedName("fitur")
    @Expose
    private String fitur;

    @SerializedName("created_at")
    @Expose
    private String created_at;

    @SerializedName("updated_at")
    @Expose
    private String updated_at;

    public Log_detail(int id_log, int id_anak, String fitur, String created_at, String updated_at) {
        this.id_log = id_log;
        this.id_anak = id_anak;
        this.fitur = fitur;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId_log() {
        return id_log;
    }

    public void setId_log(int id_log) {
        this.id_log = id_log;
    }

    public int getId_anak() {
        return id_anak;
    }

    public void setId_anak(int id_anak) {
        this.id_anak = id_anak;
    }

    public String getFitur() {
        return fitur;
    }

    public void setFitur(String fitur) {
        this.fitur = fitur;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
