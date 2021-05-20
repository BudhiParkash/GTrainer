package com.example.gtrainer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PayPojo {

    @SerializedName("order")
    @Expose
    private Order order;
    @SerializedName("key_id")
    @Expose
    private String keyId;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }
}
