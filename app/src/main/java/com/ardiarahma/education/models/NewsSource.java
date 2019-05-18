package com.ardiarahma.education.models;

/**
 * Created by Windows 10 on 3/12/2019.
 */

public class NewsSource {
    private int id;
    private String name;

    public NewsSource(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
