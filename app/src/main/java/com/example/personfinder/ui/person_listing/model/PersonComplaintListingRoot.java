package com.example.personfinder.ui.person_listing.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PersonComplaintListingRoot {
    @SerializedName("ComplaintList")
    @Expose
    private ArrayList<PersonComplaintData> complaintList = null;
    @SerializedName("Status")
    @Expose
    private Boolean status;
    @SerializedName("StatusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("Message")
    @Expose
    private String message;

    public ArrayList<PersonComplaintData> getComplaintList() {
        return complaintList;
    }

    public void setComplaintList(ArrayList<PersonComplaintData> complaintList) {
        this.complaintList = complaintList;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
