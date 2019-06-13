package com.away_expat.away.services;

import com.away_expat.away.classes.Tag;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TagApiService {

    @GET("/tags/")
    Call<List<Tag>> getAllTags();
}
