package com.example.personfinder.ui.person_listing.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PersonComplaintData {
    @SerializedName("ID")
    @Expose
    private Double id;
    @SerializedName("CODE")
    @Expose
    private String code;
    @SerializedName("DateOfBirth")
    @Expose
    private String dateOfBirth;
    @SerializedName("StringDateOfBirth")
    @Expose
    private Object stringDateOfBirth;
    @SerializedName("PersonName")
    @Expose
    private String personName;
    @SerializedName("FatherName")
    @Expose
    private String fatherName;
    @SerializedName("CNIC")
    @Expose
    private String cnic;
    @SerializedName("PersonAge")
    @Expose
    private Double personAge;
    @SerializedName("PersonHeight")
    @Expose
    private Double personHeight;
    @SerializedName("FK_Country_ID")
    @Expose
    private Double fKCountryID;
    @SerializedName("FK_States_ID")
    @Expose
    private Double fKStatesID;
    @SerializedName("FK_City_ID")
    @Expose
    private Double fKCityID;
    @SerializedName("FK_Gender_ID")
    @Expose
    private Double fKGenderID;
    @SerializedName("CountryName")
    @Expose
    private String countryName;
    @SerializedName("StatesName")
    @Expose
    private String statesName;
    @SerializedName("CityName")
    @Expose
    private String cityName;
    @SerializedName("GenderName")
    @Expose
    private String genderName;
    @SerializedName("MobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("LandLineNumber")
    @Expose
    private String landLineNumber;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("IsMissing")
    @Expose
    private Boolean isMissing;
    @SerializedName("IsFounded")
    @Expose
    private Boolean isFounded;
    @SerializedName("IsMentallyDisturb")
    @Expose
    private Boolean isMentallyDisturb;
    @SerializedName("PersonPicData")
    @Expose
    private Object personPicData;
    @SerializedName("ImagePath")
    @Expose
    private Object imagePath;
    @SerializedName("token")
    @Expose
    private Object token;
    @SerializedName("UserId")
    @Expose
    private Integer userId;

    public Double getId() {
        return id;
    }

    public void setId(Double id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Object getStringDateOfBirth() {
        return stringDateOfBirth;
    }

    public void setStringDateOfBirth(Object stringDateOfBirth) {
        this.stringDateOfBirth = stringDateOfBirth;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public Double getPersonAge() {
        return personAge;
    }

    public void setPersonAge(Double personAge) {
        this.personAge = personAge;
    }

    public Double getPersonHeight() {
        return personHeight;
    }

    public void setPersonHeight(Double personHeight) {
        this.personHeight = personHeight;
    }

    public Double getFKCountryID() {
        return fKCountryID;
    }

    public void setFKCountryID(Double fKCountryID) {
        this.fKCountryID = fKCountryID;
    }

    public Double getFKStatesID() {
        return fKStatesID;
    }

    public void setFKStatesID(Double fKStatesID) {
        this.fKStatesID = fKStatesID;
    }

    public Double getFKCityID() {
        return fKCityID;
    }

    public void setFKCityID(Double fKCityID) {
        this.fKCityID = fKCityID;
    }

    public Double getFKGenderID() {
        return fKGenderID;
    }

    public void setFKGenderID(Double fKGenderID) {
        this.fKGenderID = fKGenderID;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getStatesName() {
        return statesName;
    }

    public void setStatesName(String statesName) {
        this.statesName = statesName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getGenderName() {
        return genderName;
    }

    public void setGenderName(String genderName) {
        this.genderName = genderName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getLandLineNumber() {
        return landLineNumber;
    }

    public void setLandLineNumber(String landLineNumber) {
        this.landLineNumber = landLineNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getIsMissing() {
        return isMissing;
    }

    public void setIsMissing(Boolean isMissing) {
        this.isMissing = isMissing;
    }

    public Boolean getIsFounded() {
        return isFounded;
    }

    public void setIsFounded(Boolean isFounded) {
        this.isFounded = isFounded;
    }

    public Boolean getIsMentallyDisturb() {
        return isMentallyDisturb;
    }

    public void setIsMentallyDisturb(Boolean isMentallyDisturb) {
        this.isMentallyDisturb = isMentallyDisturb;
    }

    public Object getPersonPicData() {
        return personPicData;
    }

    public void setPersonPicData(Object personPicData) {
        this.personPicData = personPicData;
    }

    public Object getImagePath() {
        return imagePath;
    }

    public void setImagePath(Object imagePath) {
        this.imagePath = imagePath;
    }

    public Object getToken() {
        return token;
    }

    public void setToken(Object token) {
        this.token = token;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
