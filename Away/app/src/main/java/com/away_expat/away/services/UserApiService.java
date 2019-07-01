package com.away_expat.away.services;

import android.database.Observable;

import com.away_expat.away.classes.City;
import com.away_expat.away.classes.Event;
import com.away_expat.away.classes.User;
import com.away_expat.away.dto.LoginDto;
import com.away_expat.away.dto.TokenDto;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UserApiService {

    @POST("/auth/login")
    Call<TokenDto> login(@Body LoginDto loginDto);

    @POST("users")
    Call<TokenDto> createUser(@Body User user);

    @GET("users/userInfo")
    Call<User> getUserInfo(@Header("Authorization") String token);

    @PUT("users/")
    Call<User> updateUser(@Header("Authorization") String token, @Body User user);

    @DELETE("users/")
    Call<User> deleteUser(@Header("Authorization") String token);

    @PUT("users/updateUserCity/{id}")
    Call<City> updateUserCity(@Header("Authorization") String token, @Path("id") int id);

    @GET("users/recherche/{search}")
    Call<List<User>> searchByText(@Header("Authorization") String token, @Path("search") String search);

    @GET("users/{id}")
    Call<User> getUserById(@Header("Authorization") String token, @Path("id") int id);

    @Multipart
    @PUT("users/upload")
    Call<ResponseBody> upload(@Header("Authorization") String token, @Part MultipartBody.Part file);

}
