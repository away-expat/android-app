package com.away_expat.away.services;

import com.away_expat.away.classes.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApiService {

    @POST("users")
    Call<User> createUser(@Body User user);

    @PATCH("users/{id}")
    Call<User> updateUser(@Header("Authorization") String token, @Path("id") Long userId, @Body User user);

    @DELETE("users/{id}")
    Call<User> deleteUser(@Header("Authorization") String token, @Path("id") Long id);

}
