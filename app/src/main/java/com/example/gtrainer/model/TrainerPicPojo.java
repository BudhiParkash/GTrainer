package com.example.gtrainer.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrainerPicPojo implements Parcelable {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("pic")
    @Expose
    private String pic;

    protected TrainerPicPojo(Parcel in) {
        id = in.readString();
        pic = in.readString();
    }

    public static final Creator<TrainerPicPojo> CREATOR = new Creator<TrainerPicPojo>() {
        @Override
        public TrainerPicPojo createFromParcel(Parcel in) {
            return new TrainerPicPojo(in);
        }

        @Override
        public TrainerPicPojo[] newArray(int size) {
            return new TrainerPicPojo[size];
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
