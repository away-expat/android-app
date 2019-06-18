package com.away_expat.away.services;

import com.away_expat.away.classes.User;
import com.away_expat.away.dto.LoginDto;
import com.away_expat.away.dto.TokenDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApiService {

    @POST("/auth/login")
    Call<TokenDto> login(@Body LoginDto loginDto);

    @POST("users")
    Call<TokenDto> createUser(@Body User user);

    @GET("users/userInfo")
    Call<User> getUserInfo(@Header("Authorization") String token);

    @PATCH("users/{id}")
    Call<User> updateUser(@Header("Authorization") String token, @Path("id") Long userId, @Body User user);

    @DELETE("users/{id}")
    Call<User> deleteUser(@Header("Authorization") String token, @Path("id") Long id);

}
