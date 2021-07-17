package com.skywalkers.gtrainer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RatingPojo {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("users_id")
    @Expose
    private String usersId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getUsersId() {
        return usersId;
    }

    public void setUsersId(String usersId) {
        this.usersId = usersId;
    }
}
