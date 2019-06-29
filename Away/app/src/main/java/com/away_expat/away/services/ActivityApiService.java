package com.away_expat.away.services;

import com.away_expat.away.classes.Activity;
import com.away_expat.away.dto.ActivityByTagListDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ActivityApiService {

    @GET("/activities/googleByCity/{city}/{tag}")
    Call<ActivityByTagListDto> getActivitiesByTag(@Header("Authorization") String token, @Path("city") String city, @Path("tag") String tag);

    @GET("/activities/recherche/{search}")
    Call<List<Activity>> searchByText(@Header("Authorization")String token, @Path("search")String search);
}
