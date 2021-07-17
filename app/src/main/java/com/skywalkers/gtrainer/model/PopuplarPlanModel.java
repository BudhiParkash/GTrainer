package com.skywalkers.gtrainer.model;

public class PopuplarPlanModel {

    String ImageLink;
    int ImageLinks;

    public PopuplarPlanModel(int imageLinks) {
        ImageLinks = imageLinks;
    }

    public int getImageLinks() {
        return ImageLinks;
    }

    public void setImageLinks(int imageLinks) {
        ImageLinks = imageLinks;
    }

    public PopuplarPlanModel(String imageLink) {
        ImageLink = imageLink;
    }

    public String getImageLink() {
        return ImageLink;
    }

    public void setImageLink(String imageLink) {
        ImageLink = imageLink;
    }
}
