package com.example.personfinder.Networking;

public class NetworkConstants {

    public static final String BASE_URL = "http://demo.theembroider.com/FindService/";
    public static final String INTERESTS = "/api/interests";
    public static final String OFFERS = "/api/user/offers?ids=3,2,&lat=26.282958453807595&long=50.21213367109322&api_token=BH0Mu8xgXTp5GS7QpEgpu1CiF5BrRCTiRzBwOB3VTNbgg8zquQ6aedaMzjgX";
    public static final String LOGIN = "PostLogin";
    public static final String REGISTER = "PostRegister";
    public static final String CREATEOFFER = "/api/marketer/offer";
    public static final String GETOFFERS = "/api/marketer/offer";
    public static final String PUBLISH_OFFER = "/api/marketer/offer/market";
    public static final String GET_COMPLAINTS = "/FindService/ComplaintListByFilter";
    public static final String POST_PROFILE = "/FindService/UserupdateData";
    public static final String CREATE_POST = "/FindService/ComplaintsData";


    public static final String HOME_API = "/api/user/offers";


    public static final String GET_USERS_BY_INTEREST_AND_RADIUS = "/api/marketer/interestedusers";
}
