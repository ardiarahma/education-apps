package com.ardiarahma.education.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Windows 10 on 6/26/2019.
 */

public class Games {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("gamecategories_id")
    @Expose
    private int gamecategories_id;

    @SerializedName("image")
    @Expose
    private String img;

    @SerializedName("url")
    @Expose
    private String url;

    public Games(String id, String name, int gamecategories_id, String img, String url) {
        this.id = id;
        this.name = name;
        this.gamecategories_id = gamecategories_id;
        this.img = img;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGamecategories_id() {
        return gamecategories_id;
    }

    public void setGamecategories_id(int gamecategories_id) {
        this.gamecategories_id = gamecategories_id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
