
package com.example.now.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("api_token")
    @Expose
    private String apiToken;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("user")
    @Expose
    private User user;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public class User {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("company_name")
        @Expose
        private String companyName;
        @SerializedName("person_name")
        @Expose
        private String personName;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("mobile_number")
        @Expose
        private Object mobileNumber;
        @SerializedName("latitude")
        @Expose
        private Object latitude;
        @SerializedName("longitude")
        @Expose
        private Object longitude;
        @SerializedName("country_code")
        @Expose
        private Object countryCode;
        @SerializedName("country")
        @Expose
        private Object country;
        @SerializedName("city")
        @Expose
        private Object city;
        @SerializedName("register_status")
        @Expose
        private Integer registerStatus;
        @SerializedName("api_token")
        @Expose
        private String apiToken;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("language")
        @Expose
        private Object language;
        @SerializedName("logo")
        @Expose
        private Object logo;
        @SerializedName("timezone")
        @Expose
        private String timezone;
        @SerializedName("fb_Userid")
        @Expose
        private Object fbUserId;
        @SerializedName("year_of_birth")
        @Expose
        private Object yearOfBirth;
        @SerializedName("gender")
        @Expose
        private String gender;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getPersonName() {
            return personName;
        }

        public void setPersonName(String personName) {
            this.personName = personName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Object getMobileNumber() {
            return mobileNumber;
        }

        public void setMobileNumber(Object mobileNumber) {
            this.mobileNumber = mobileNumber;
        }

        public Object getLatitude() {
            return latitude;
        }

        public void setLatitude(Object latitude) {
            this.latitude = latitude;
        }

        public Object getLongitude() {
            return longitude;
        }

        public void setLongitude(Object longitude) {
            this.longitude = longitude;
        }

        public Object getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(Object countryCode) {
            this.countryCode = countryCode;
        }

        public Object getCountry() {
            return country;
        }

        public void setCountry(Object country) {
            this.country = country;
        }

        public Object getCity() {
            return city;
        }

        public void setCity(Object city) {
            this.city = city;
        }

        public Integer getRegisterStatus() {
            return registerStatus;
        }

        public void setRegisterStatus(Integer registerStatus) {
            this.registerStatus = registerStatus;
        }

        public String getApiToken() {
            return apiToken;
        }

        public void setApiToken(String apiToken) {
            this.apiToken = apiToken;
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

        public Object getLanguage() {
            return language;
        }

        public void setLanguage(Object language) {
            this.language = language;
        }

        public Object getLogo() {
            return logo;
        }

        public void setLogo(Object logo) {
            this.logo = logo;
        }

        public String getTimezone() {
            return timezone;
        }

        public void setTimezone(String timezone) {
            this.timezone = timezone;
        }

        public Object getFbUserId() {
            return fbUserId;
        }

        public void setFbUserId(Object fbUserId) {
            this.fbUserId = fbUserId;
        }

        public Object getYearOfBirth() {
            return yearOfBirth;
        }

        public void setYearOfBirth(Object yearOfBirth) {
            this.yearOfBirth = yearOfBirth;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

    }
}
