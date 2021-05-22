package com.example.gtrainer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingPojo {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("buyerId")
    @Expose
    private String buyerId;
    @SerializedName("trainerID")
    @Expose
    private String trainerID;
    @SerializedName("payment")
    @Expose
    private int payment;
    @SerializedName("periodOfBooking")
    @Expose
    private String periodOfBooking;
    @SerializedName("lastDateOfBook")
    @Expose
    private String lastDateOfBook;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("__v")
    @Expose
    private Integer v;


    public BookingPojo(String buyerId, String trainerID, int payment, String periodOfBooking, String lastDateOfBook) {
        this.buyerId = buyerId;
        this.trainerID = trainerID;
        this.payment = payment;
        this.periodOfBooking = periodOfBooking;
        this.lastDateOfBook = lastDateOfBook;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getTrainerID() {
        return trainerID;
    }

    public void setTrainerID(String trainerID) {
        this.trainerID = trainerID;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public String getPeriodOfBooking() {
        return periodOfBooking;
    }

    public void setPeriodOfBooking(String periodOfBooking) {
        this.periodOfBooking = periodOfBooking;
    }

    public String getLastDateOfBook() {
        return lastDateOfBook;
    }

    public void setLastDateOfBook(String lastDateOfBook) {
        this.lastDateOfBook = lastDateOfBook;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }
}
