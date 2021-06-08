
package com.example.now.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Offers {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }


    public class Datum {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("product_name")
        @Expose
        private String productName;
        @SerializedName("model_number")
        @Expose
        private String modelNumber;
        @SerializedName("brand_name")
        @Expose
        private String brandName;
        @SerializedName("redeem_code")
        @Expose
        private Object redeemCode;
        @SerializedName("expiry_date")
        @Expose
        private Object expiryDate;
        @SerializedName("is_redeem")
        @Expose
        private Object isRedeem;
        @SerializedName("interest_id")
        @Expose
        private Integer interestId;
        @SerializedName("location")
        @Expose
        private Object location;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("latitude")
        @Expose
        private String latitude;
        @SerializedName("longitude")
        @Expose
        private String longitude;
        @SerializedName("schedule_date")
        @Expose
        private String scheduleDate;
        @SerializedName("schedule_time")
        @Expose
        private String scheduleTime;
        @SerializedName("marketer_id")
        @Expose
        private Integer marketerId;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("target_age")
        @Expose
        private String targetAge;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("is_now")
        @Expose
        private Integer isNow;
        @SerializedName("attachments")
        @Expose
        private List<Attachment> attachments = null;
        @SerializedName("label")
        @Expose
        private String label;
        @SerializedName("is_favorite")
        @Expose
        private Boolean isFavorite;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getModelNumber() {
            return modelNumber;
        }

        public void setModelNumber(String modelNumber) {
            this.modelNumber = modelNumber;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public Object getRedeemCode() {
            return redeemCode;
        }

        public void setRedeemCode(Object redeemCode) {
            this.redeemCode = redeemCode;
        }

        public Object getExpiryDate() {
            return expiryDate;
        }

        public void setExpiryDate(Object expiryDate) {
            this.expiryDate = expiryDate;
        }

        public Object getIsRedeem() {
            return isRedeem;
        }

        public void setIsRedeem(Object isRedeem) {
            this.isRedeem = isRedeem;
        }

        public Integer getInterestId() {
            return interestId;
        }

        public void setInterestId(Integer interestId) {
            this.interestId = interestId;
        }

        public Object getLocation() {
            return location;
        }

        public void setLocation(Object location) {
            this.location = location;
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

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getScheduleDate() {
            return scheduleDate;
        }

        public void setScheduleDate(String scheduleDate) {
            this.scheduleDate = scheduleDate;
        }

        public String getScheduleTime() {
            return scheduleTime;
        }

        public void setScheduleTime(String scheduleTime) {
            this.scheduleTime = scheduleTime;
        }

        public Integer getMarketerId() {
            return marketerId;
        }

        public void setMarketerId(Integer marketerId) {
            this.marketerId = marketerId;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getTargetAge() {
            return targetAge;
        }

        public void setTargetAge(String targetAge) {
            this.targetAge = targetAge;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public Integer getIsNow() {
            return isNow;
        }

        public void setIsNow(Integer isNow) {
            this.isNow = isNow;
        }

        public List<Attachment> getAttachments() {
            return attachments;
        }

        public void setAttachments(List<Attachment> attachments) {
            this.attachments = attachments;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public Boolean getIsFavorite() {
            return isFavorite;
        }

        public void setIsFavorite(Boolean isFavorite) {
            this.isFavorite = isFavorite;
        }

    }

    public class Attachment {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("link")
        @Expose
        private String link;
        @SerializedName("type")
        @Expose
        private Integer type;
        @SerializedName("offer_id")
        @Expose
        private Integer offerId;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public Integer getOfferId() {
            return offerId;
        }

        public void setOfferId(Integer offerId) {
            this.offerId = offerId;
        }

    }


}