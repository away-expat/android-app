package com.away_expat.away.services;

import com.away_expat.away.classes.Info;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface InfoApiService {

    @GET("/infos")
    Call<List<Info>> getCountryInfo(@Header("Authorization") String token);
}
