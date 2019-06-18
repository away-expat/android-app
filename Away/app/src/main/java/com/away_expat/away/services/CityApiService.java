package com.away_expat.away.services;

import com.away_expat.away.classes.City;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface CityApiService {

    @GET("/cities/")
    Call<List<City>> getCities(@Header("Authorization") String token);
}
