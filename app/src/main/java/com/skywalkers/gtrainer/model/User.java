package com.skywalkers.gtrainer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {
    @SerializedName("profileVerified")
    @Expose
    private Boolean profileVerified;
    @SerializedName("trainerAvailable")
    @Expose
    private Boolean trainerAvailable;
    @SerializedName("trainerSlotFull")
    @Expose
    private Boolean trainerSlotFull;
    @SerializedName("typeOfUser")
    @Expose
    private Integer typeOfUser;
    @SerializedName("ranking")
    @Expose
    private Integer ranking;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("phoneNum")
    @Expose
    private Long phoneNum;
    @SerializedName("certificates")
    @Expose
    private List<CertificatePhotoPojo> certificates = null;
    @SerializedName("trainerPic")
    @Expose
    private List<TrainerPicPojo> trainerPic = null;
    @SerializedName("ratings")
    @Expose
    private List<RatingPojo> ratings = null;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("__v")
    @Expose
    private Integer v;

    @SerializedName("user_name")
    @Expose
    private String user_name;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("city")
    @Expose
    private String city;

    @SerializedName("aboutUser")
    @Expose
    private String aboutUser;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("experiance")
    @Expose
    private String experiance;


    @SerializedName("price")
    @Expose
    private Integer price;


    public User(Boolean trainerAvailable, String user_name, String email, String city, String aboutUser, String address, String gender, String language, String experiance) {
        this.trainerAvailable = trainerAvailable;
        this.user_name = user_name;
        this.email = email;
        this.city = city;
        this.aboutUser = aboutUser;
        this.address = address;
        this.gender = gender;
        this.language = language;
        this.experiance = experiance;
    }

    public User(Integer ranking, List<CertificatePhotoPojo> certificates, List<TrainerPicPojo> trainerPic, String user_name, String city, String aboutUser, String address, String gender, String language, String experiance) {
        this.ranking = ranking;
        this.certificates = certificates;
        this.trainerPic = trainerPic;
        this.user_name = user_name;
        this.city = city;
        this.aboutUser = aboutUser;
        this.address = address;
        this.gender = gender;
        this.language = language;
        this.experiance = experiance;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getAboutUser() {
        return aboutUser;
    }

    public void setAboutUser(String aboutUser) {
        this.aboutUser = aboutUser;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getExperiance() {
        return experiance;
    }

    public void setExperiance(String experiance) {
        this.experiance = experiance;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public User(Long phoneNum) {
        this.phoneNum = phoneNum;
    }

    public User(String user_name) {
        this.user_name = user_name;

    }



    public Boolean getProfileVerified() {
        return profileVerified;
    }

    public void setProfileVerified(Boolean profileVerified) {
        this.profileVerified = profileVerified;
    }

    public Boolean getTrainerAvailable() {
        return trainerAvailable;
    }

    public void setTrainerAvailable(Boolean trainerAvailable) {
        this.trainerAvailable = trainerAvailable;
    }

    public Boolean getTrainerSlotFull() {
        return trainerSlotFull;
    }

    public void setTrainerSlotFull(Boolean trainerSlotFull) {
        this.trainerSlotFull = trainerSlotFull;
    }

    public Integer getTypeOfUser() {
        return typeOfUser;
    }

    public void setTypeOfUser(Integer typeOfUser) {
        this.typeOfUser = typeOfUser;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(Long phoneNum) {
        this.phoneNum = phoneNum;
    }

    public List<CertificatePhotoPojo> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<CertificatePhotoPojo> certificates) {
        this.certificates = certificates;
    }

    public List<TrainerPicPojo> getTrainerPic() {
        return trainerPic;
    }

    public void setTrainerPic(List<TrainerPicPojo> trainerPic) {
        this.trainerPic = trainerPic;
    }

    public List<RatingPojo> getRatings() {
        return ratings;
    }

    public void setRatings(List<RatingPojo> ratings) {
        this.ratings = ratings;
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
