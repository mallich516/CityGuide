package com.example.loginpage.components;

public class ComponentModel {

    String title;
    String desc;
    String rating;
    String image;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ComponentModel() {
        this.title = title;
        this.desc = desc;
        this.rating = rating;
        this.image = image;
    }
}
