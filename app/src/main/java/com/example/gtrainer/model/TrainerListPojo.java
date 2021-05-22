package com.example.gtrainer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrainerListPojo {
    @SerializedName("searchResult")
    @Expose
    private List<User> searchResult = null;
    @SerializedName("previous")
    @Expose
    private PerviousPojo previous;

    @SerializedName("next")
    @Expose
    private NextPojo next;

    public List<User> getSearchResult() {
        return searchResult;
    }

    public void setSearchResult(List<User> searchResult) {
        this.searchResult = searchResult;
    }

    public PerviousPojo getPrevious() {
        return previous;
    }

    public void setPrevious(PerviousPojo previous) {
        this.previous = previous;
    }
}
