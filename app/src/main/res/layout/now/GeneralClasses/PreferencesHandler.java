package com.example.now.GeneralClasses;


import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;


public class PreferencesHandler {

    private static SharedPreferences pref;
    private static SharedPreferences.Editor editor;

    private static final String UEMAIL = "uemail";
    private static final String UPWD = "upwd";
    private static final String CURRENT_DATE = "current_date";
    public static final String LOGINSTATUS = "loginstatus";
    public static final String USERNAME = "username";
    public static final String PHONENUMBER = "phonenumber";
    public static final String USERID = "userid";
    public static final String API_TOKEN = "api_token";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String IS_INTRO_ENABLE = "IsIntroEnable";
    private static final String IS_ROUTE_DRAW = "Is_Route_Draw";
    private static final String OPID = "opid";
    private static final String CRID = "crid";
    private static final String IS_LOGIN = "Is_Login";
    private static final String STATUS = "status";
    private static final String ORDER_DATA = "orderData";
    private static final String ORDER_ARRIVE = "orderArrive";
    private static final String HAS_ORDER = "hasOrder";
    private static final String JOB_STATUS = "JobStatus";
    private static final String OFFER_ID = "offer_id";
    public PreferencesHandler() {

    }

    public PreferencesHandler(Context context) {
        pref = context.getSharedPreferences("Now", Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public boolean getIsFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }


    public int getOperatorId() {
        return pref.getInt(OPID, 0);
    }

    public void setOperatorId(int operid) {
        editor.putInt(OPID, operid);
        editor.apply();
        editor.commit();
    }

    public int getCarrierId() {
        return pref.getInt(CRID, 0);
    }

    public void setCarrierId(int carrierId) {
        editor.putInt(CRID, carrierId);
        editor.apply();
        editor.commit();
    }

    public void setCarrierIndividualId(int carrierId) {
        editor.putInt(CRID, carrierId);
        editor.apply();
        editor.commit();
    }


    public void setIsFirstTimeLaunch(boolean isFirstTimeLaunch) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTimeLaunch);
        editor.apply();
        editor.commit();
    }

    public boolean getIsIntroEnable() {
        return pref.getBoolean(IS_INTRO_ENABLE, true);
    }

    public void setIsIntroEnableh(boolean isIntroEnableh) {
        editor.putBoolean(IS_INTRO_ENABLE, isIntroEnableh);
        editor.apply();
        editor.commit();
    }

    public boolean getIsRouteDraw() {
        return pref.getBoolean(IS_ROUTE_DRAW, false);
    }

    public void setIisRouteDraw(boolean isRouteDraw) {
        editor.putBoolean(IS_ROUTE_DRAW, isRouteDraw);
        editor.apply();
        editor.commit();
    }

    public String getUemail() {
        return pref.getString(UEMAIL, "");
    }

    public void setUemail(String uemail) {
        editor.putString(UEMAIL, uemail);
        editor.apply();
        editor.commit();
    }

    public String getUpwd() {
        return pref.getString(UPWD, "");
    }

    public void setUpwd(String upwd) {
        editor.putString(UPWD, upwd);
        editor.apply();
        editor.commit();
    }

    public String getCurrentDate() {
        return pref.getString(CURRENT_DATE, "");
    }

    public void setCurrentDate(String currentDate) {
        editor.putString(CURRENT_DATE, currentDate);
        editor.apply();
        editor.commit();
    }

    public boolean getLoginstatus() {
        return pref.getBoolean(LOGINSTATUS, false);
    }

    public void setLoginstatus(Boolean loginstatus) {
        editor.putBoolean(LOGINSTATUS, loginstatus);
        editor.apply();
        editor.commit();
    }

    public String getOfferId() {
        return pref.getString(OFFER_ID, "");
    }

    public void setOfferId(String offerId) {
        editor.putString(OFFER_ID, offerId);
        editor.apply();
        editor.commit();
    }


    public String getIsLogin() {
        return pref.getString(IS_LOGIN, "");
    }

    public void setIsLogin(String isLogin) {
        editor.putString(IS_LOGIN, isLogin);
        editor.apply();
        editor.commit();
    }


    public String getUsername() {
        return pref.getString(USERNAME, "");
    }

    public void setUsername(String username) {
        editor.putString(USERNAME, username);
        editor.apply();
        editor.commit();
    }

    public String getPhonenumber() {
        return pref.getString(PHONENUMBER, "");
    }

    public void setPhonenumber(String phonenumber) {
        editor.putString(PHONENUMBER, phonenumber);
        editor.apply();
        editor.commit();
    }

    public String getUserid() {
        return pref.getString(USERID, "");
    }

    public void setUserid(String userid) {
        editor.putString(USERID, userid);
        editor.apply();
        editor.commit();
    }

    public String getApiToken() {
        return pref.getString(API_TOKEN, "");
    }

    public void setApiToken(String bearerToken) {
        editor.putString(API_TOKEN, bearerToken);
        editor.apply();
        editor.commit();
    }

    public void setStatus(boolean isActive){
        editor.putBoolean(STATUS, isActive);
        editor.apply();
        editor.commit();
    }

    public void emptyPrefs() {
        editor.remove(JOB_STATUS);
        editor.remove(ORDER_ARRIVE);
        editor.apply();
        editor.commit();
    }



}
