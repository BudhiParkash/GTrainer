package com.example.gtrainer.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CertificatePhotoPojo implements Parcelable {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("pic")
    @Expose
    private String pic;

    protected CertificatePhotoPojo(Parcel in) {
        id = in.readString();
        pic = in.readString();
    }

    public static final Creator<CertificatePhotoPojo> CREATOR = new Creator<CertificatePhotoPojo>() {
        @Override
        public CertificatePhotoPojo createFromParcel(Parcel in) {
            return new CertificatePhotoPojo(in);
        }

        @Override
        public CertificatePhotoPojo[] newArray(int size) {
            return new CertificatePhotoPojo[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(pic);
    }
}
