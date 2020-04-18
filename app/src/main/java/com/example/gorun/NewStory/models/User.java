package com.example.gorun.NewStory.models;

public class User {

    private String name;
    private String email;
    private String uid;
    private String picture;
    private String yearsOld;
    private String activty;


    public User(String name, String email, String uid, String picture, String yearsOld, String activty) {
        this.name = name;
        this.email = email;
        this.uid = uid;
        this.picture = picture;
        this.yearsOld = yearsOld;
        this.activty = activty;
    }

    public User(){}

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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getYearsOld() {
        return yearsOld;
    }

    public void setYearsOld(String yearsOld) {
        this.yearsOld = yearsOld;
    }

    public String getActivty() {
        return activty;
    }

    public void setActivty(String activty) {
        this.activty = activty;
    }
}
