package com.away_expat.away.services;

import com.away_expat.away.classes.Tag;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TagApiService {

    @GET("/tags/")
    Call<List<Tag>> getAllTags();

    @GET("/tags/ofUser")
    Call<List<Tag>> getUserTags(@Header("Authorization") String token);

    @GET("/tags/recherche/{search}")
    Call<List<Tag>> searchByText(@Header("Authorization") String token, @Path("search") String searchedText);

    @POST("/tags/like/{id}")
    Call<Tag> addToUserTags(@Header("Authorization") String token, @Path("id") int id);

    @DELETE("/tags/dislike/{id}")
    Call<Void> removeFromUserTags(@Header("Authorization") String token, @Path("id") int id);
}
