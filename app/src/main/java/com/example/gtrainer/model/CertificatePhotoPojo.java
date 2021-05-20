package com.example.gtrainer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CertificatePhotoPojo {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("pic")
    @Expose
    private String pic;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
