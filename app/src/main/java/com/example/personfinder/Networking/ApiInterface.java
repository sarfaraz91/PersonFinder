package com.example.personfinder.Networking;


import com.example.personfinder.Model.LoginResponse;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {


    @FormUrlEncoded
    @POST(NetworkConstants.LOGIN)
    Call<LoginResponse> login(@Field("UserName") String email, @Field("Password") String password,@Field("token") String token);

    @FormUrlEncoded
    @POST(NetworkConstants.REGISTER)
    Call<LoginResponse> register(@Field("UserName") String UserName,
                                 @Field("UserEmail") String UserEmail,
                                 @Field("password") String password,
                                 @Field("DateOfBirth") String DateOfBirth,
                                 @Field("MobileNo") String MobileNo,
                                 @Field("UserAddress") String UserAddress,
                                 @Field("Gender") String Gender);



//    @GET(NetworkConstants.GETOFFERS)
//    Call<Offers> getOffers(@Query("api_token") String api_token,
//                           @Query("type") String type);
//
//
//    @GET(NetworkConstants.HOME_API)
//    Call<Offers> getAllOffers(@Query("api_token") String api_token,
//                              @Query("ids") String ids,
//                              @Query("lat") String lat,
//                              @Query("long") String lng);
//
//
//    @GET(NetworkConstants.INTERESTS)
//    Call<Interests.MyResponse> getAllInterests();


//    @FormUrlEncoded
//    @POST(NetworkConstants.CREATEOFFER)
//    Call<ResponseBody> createOffer(@Query("api_token") String api_token,
//                                   @Field("type") String type,
//                                   @Field("description") String description,
//                                   @Field("brand_name") String brand_name,
//                                   @Field("product_name") String product_name,
//                                   @Field("model_number") String model_number,
//                                   @Field("latitude") double latitude,
//                                   @Field("longitude") double longitude,
//                                   @Field("video_link") String video_link,
//                                   @Field("store_location") String store_location,
//                                   @Field("web_link") String web_link,
//                                   @Field("images") List<RequestBody> images);
//
//    @POST(NetworkConstants.CREATEOFFER)
//    Call<ResponseBody> createOffer2(
//            @Query("api_token") String token, @Body RequestBody params);
//
//
//    @GET(NetworkConstants.GET_USERS_BY_INTEREST_AND_RADIUS)
//    Call<TotalUsers> getInterestedUsers(@Query("api_token") String api_token,
//                                        @Query("ids") String ids,
//                                        @Query("latitude") String latitude,
//                                        @Query("longitude") String longitude,
//                                        @Query("km") String km);
//
//    @FormUrlEncoded
//    @POST(NetworkConstants.PUBLISH_OFFER)
//    Call<ResponseBody> publishOffer(@Query("api_token") String api_token,
//                                    @Query("offer_id") String offer_id,
//                                    @Query("ids") String ids,
//                                    @Field("gender") String gender,
//                                    @Field("target_age") String target_age,
//                                    @Field("latitude") String latitude,
//                                    @Field("longitude") String longitude,
//                                    @Field("schedule_date") String schedule_date,
//                                    @Field("schedule_time") String schedule_time,
//                                    @Field("time") String time);

}
