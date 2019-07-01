package com.away_expat.away.services;

import com.away_expat.away.classes.Event;
import com.away_expat.away.dto.ActivityListDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface ActivityApiService {

    @GET("/activities/googleByCity/{city}/{tag}")
    Call<ActivityListDto> getActivitiesByTag(@Header("Authorization") String token, @Path("city") String city, @Path("tag") String tag);

    @GET("/activities/recherche/{search}")
    Call<ActivityListDto> searchByText(@Header("Authorization")String token, @Path("search")String search);

    @GET("/activities/suggestion")
    Call<List<Event>> getHome(@Header("Authorization")String token);

    @GET("/activities/googleGetNextPage/{token}")
    Call<ActivityListDto> loadNextResults(@Header("Authorization")String token, @Path("token")String nextToken);

}
