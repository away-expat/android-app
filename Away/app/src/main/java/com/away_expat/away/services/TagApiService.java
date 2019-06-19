package com.away_expat.away.services;

import com.away_expat.away.classes.Tag;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface TagApiService {

    @GET("/tags/")
    Call<List<Tag>> getAllTags();

    @GET("/tags/ofUser")
    Call<List<Tag>> getUserTags(@Header("Authorization") String token);

}
