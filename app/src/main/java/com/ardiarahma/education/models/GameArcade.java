package com.ardiarahma.education.models;

/**
 * Created by Windows 10 on 1/3/2019.
 */

public class GameArcade {

    private String Name;
    private String Link;
    private int Thumbnail;

    public GameArcade(){

    }

    public GameArcade(String name, String link, int thumbnail) {
        Name = name;
        Link = link;
        Thumbnail = thumbnail;
    }

    //getter
    public String getName() {
        return Name;
    }

    public String getLink() {
        return Link;
    }

    public int getThumbnail() {
        return Thumbnail;
    }

    //setter
    public void setName(String name) {
        Name = name;
    }

    public void setLink(String link) {
        Link = link;
    }

    public void setThumbnail(int thumbnail) {
        Thumbnail = thumbnail;
    }
}
