package com.ardiarahma.education.models;

/**
 * Created by Windows 10 on 1/13/2019.
 */

public class EbookSubject {

    private int id;
    private String Name;
    private int Thumbnail;


    public EbookSubject(int id, String name, int thumbnail) {
        this.id = id;
        Name = name;
        Thumbnail = thumbnail;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return Name;
    }

    public int getThumbnail() {
        return Thumbnail;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setThumbnail(int thumbnail) {
        Thumbnail = thumbnail;
    }
}
