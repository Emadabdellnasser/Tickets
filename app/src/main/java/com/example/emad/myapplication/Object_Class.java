package com.example.emad.myapplication;

/**
 * Created by EMAD on 3/6/2018.
 */

public class Object_Class {

    private String nameCh;
    private String priceCh;
    private String image;

    private String rat;

    public Object_Class(String nameCh, String priceCh, String image) {
        this.nameCh = nameCh;
        this.priceCh = priceCh;
        this.image = image;
    }

    public Object_Class() {
    }
    public String getRat() {return rat;}

    public void setRat(String rat) {this.rat = rat;}

    public String getNameCh() {
        return nameCh;
    }

    public void setNameCh(String nameCh) {
        this.nameCh = nameCh;
    }

    public String getPriceCh() {
        return priceCh;
    }

    public void setPriceCh(String priceCh) {
        this.priceCh = priceCh;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
