package com.example.bioticclasses.List;

public class CourseList {
    String img, name, disc;

    public CourseList(String img, String name, String disc) {
        this.img = img;
        this.name = name;
        this.disc = disc;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisc() {
        return disc;
    }

    public void setDisc(String disc) {
        this.disc = disc;
    }
}
