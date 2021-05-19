package com.example.gtrainer.model;

public class Ads_Pojo {


    int ad_number;
    String ad_Name;
    String ad_Photo;
    String ad_Description;
    String ad_Link;


    public Ads_Pojo(){

    }

    public Ads_Pojo(String ad_Name, String ad_Photo, String ad_Description, String ad_Link) {
        this.ad_Name = ad_Name;
        this.ad_Photo = ad_Photo;
        this.ad_Description = ad_Description;
        this.ad_Link = ad_Link;
    }

    public int getAd_number() {
        return ad_number;
    }

    public void setAd_number(int ad_number) {
        this.ad_number = ad_number;
    }

    public String getAd_Name() {
        return ad_Name;
    }

    public void setAd_Name(String ad_Name) {
        this.ad_Name = ad_Name;
    }

    public String getAd_Photo() {
        return ad_Photo;
    }

    public void setAd_Photo(String ad_Photo) {
        this.ad_Photo = ad_Photo;
    }

    public String getAd_Description() {
        return ad_Description;
    }

    public void setAd_Description(String ad_Description) {
        this.ad_Description = ad_Description;
    }

    public String getAd_Link() {
        return ad_Link;
    }

    public void setAd_Link(String ad_Link) {
        this.ad_Link = ad_Link;
    }
}
