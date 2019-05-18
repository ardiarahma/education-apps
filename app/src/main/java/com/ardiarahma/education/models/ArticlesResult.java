package com.ardiarahma.education.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Windows 10 on 2/28/2019.
 */

public class ArticlesResult {
    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("totalResults")
    @Expose
    private int totalNews;

    private ArrayList<Articles> articles = null;

    public ArticlesResult(String status, int totalNews, ArrayList<Articles> articles) {
        this.status = status;
        this.totalNews = totalNews;
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public int getTotalNews() {
        return totalNews;
    }

    public ArrayList<Articles> getArticles() {
        return articles;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTotalNews(int totalNews) {
        this.totalNews = totalNews;
    }

    public void setArticles(ArrayList<Articles> articles) {
        this.articles = articles;
    }
}
