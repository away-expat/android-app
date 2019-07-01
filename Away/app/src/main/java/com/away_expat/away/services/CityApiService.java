package com.away_expat.away.services;

import com.away_expat.away.classes.City;
import com.away_expat.away.classes.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CityApiService {

    @GET("/cities/")
    Call<List<City>> getCities(@Header("Authorization") String token);

    @GET("/cities/autoCompleteCityName/{name}")
    Call<List<City>> searchCities(@Path("name") String search);

    @GET("cities/{id}")
    Call<City> getCityById(@Header("Authorization") String token, @Path("id") String idCity);

    @GET("/cities/suggestion")
    Call<List<City>> getCitiesSuggestion(@Header("Authorization") String token);
}
