package com.ardiarahma.education.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Windows 10 on 3/19/2019.
 */

public class Siswa {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("user_id")
    @Expose
    private int user_id;

    @SerializedName("jenis_kelamin")
    @Expose
    private String jenis_kelamin;

    @SerializedName("class")
    @Expose
    private int classes;

    @SerializedName("school_name")
    @Expose
    private String school_name;

    @SerializedName("orangtua_id")
    @Expose
    private int orangtua_id;

    @SerializedName("province_id")
    @Expose
    private int province_id;

    @SerializedName("regency_id")
    @Expose
    private int regency_id;

    @SerializedName("district_id")
    @Expose
    private int district_id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("type")
    @Expose
    private String type;

    public Siswa(int id, int user_id, String jenis_kelamin, int classes, String school_name, int orangtua_id, int province_id, int regency_id, int district_id, String name, String email, String username, String password, String type) {
        this.id = id;
        this.user_id = user_id;
        this.jenis_kelamin = jenis_kelamin;
        this.classes = classes;
        this.school_name = school_name;
        this.orangtua_id = orangtua_id;
        this.province_id = province_id;
        this.regency_id = regency_id;
        this.district_id = district_id;
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public int getClasses() {
        return classes;
    }

    public void setClasses(int classes) {
        this.classes = classes;
    }

    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }

    public int getOrangtua_id() {
        return orangtua_id;
    }

    public void setOrangtua_id(int orangtua_id) {
        this.orangtua_id = orangtua_id;
    }

    public int getProvince_id() {
        return province_id;
    }

    public void setProvince_id(int province_id) {
        this.province_id = province_id;
    }

    public int getRegency_id() {
        return regency_id;
    }

    public void setRegency_id(int regency_id) {
        this.regency_id = regency_id;
    }

    public int getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(int district_id) {
        this.district_id = district_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
